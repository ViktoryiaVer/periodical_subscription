package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocationService;
import com.periodicalsubscription.aspect.logging.annotation.ServiceEx;
import com.periodicalsubscription.exceptions.subscription.SubscriptionCompletedStatusException;
import com.periodicalsubscription.exceptions.subscription.SubscriptionNotFoundException;
import com.periodicalsubscription.exceptions.user.UserNotFoundException;
import com.periodicalsubscription.model.repository.UserRepository;
import com.periodicalsubscription.model.specification.SubscriptionSpecifications;
import com.periodicalsubscription.service.dto.PeriodicalDto;
import com.periodicalsubscription.service.dto.SubscriptionDetailDto;
import com.periodicalsubscription.service.dto.UserDto;
import com.periodicalsubscription.exceptions.subscription.SubscriptionServiceException;
import com.periodicalsubscription.mapper.SubscriptionMapper;
import com.periodicalsubscription.model.repository.SubscriptionRepository;
import com.periodicalsubscription.model.entity.Subscription;
import com.periodicalsubscription.service.api.PeriodicalService;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.service.dto.SubscriptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final PeriodicalService periodicalService;
    private final SubscriptionMapper mapper;
    private final MessageSource messageSource;
    private final UserRepository userRepository;

    @Override
    @LogInvocationService
    public Page<SubscriptionDto> findAll(Pageable pageable) {
        return subscriptionRepository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public SubscriptionDto findById(Long id) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> {
            throw new SubscriptionNotFoundException(messageSource.getMessage("msg.error.subscription.find.by.id", null,
                    LocaleContextHolder.getLocale()));
        });
        return mapper.toDto(subscription);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    @Transactional
    public SubscriptionDto save(SubscriptionDto dto) {
        SubscriptionDto savedSubscription = mapper.toDto(subscriptionRepository.save(mapper.toEntity(dto)));
        if (savedSubscription == null) {
            throw new SubscriptionServiceException(messageSource.getMessage("msg.error.subscription.service.save", null,
                    LocaleContextHolder.getLocale()));
        }
        return savedSubscription;
    }

    @Override
    @LogInvocationService
    @Transactional
    public SubscriptionDto createSubscriptionFromCart(UserDto userDto, Map<Long, Integer> cart) {
        SubscriptionDto subscription = processSubscriptionInCart(userDto, cart);
        return save(subscription);
    }

    @Override
    @LogInvocationService
    public SubscriptionDto processSubscriptionInCart(UserDto userDto, Map<Long, Integer> cart) {
        SubscriptionDto subscription = new SubscriptionDto();
        subscription.setUserDto(userDto);
        subscription.setStatusDto(SubscriptionDto.StatusDto.PENDING);
        List<SubscriptionDetailDto> subscriptionDetails = new ArrayList<>();

        cart.forEach((periodicalId, durationInYears) -> {
            SubscriptionDetailDto detail = new SubscriptionDetailDto();
            PeriodicalDto periodical = periodicalService.findById(periodicalId);
            detail.setPeriodicalDto(periodical);
            detail.setSubscriptionDurationInYears(durationInYears);
            detail.setPeriodicalCurrentPrice(periodical.getPrice());
            subscriptionDetails.add(detail);
        });
        subscription.setSubscriptionDetailDtos(subscriptionDetails);
        BigDecimal totalCost = calculateTotalCost(subscriptionDetails);
        subscription.setTotalCost(totalCost);
        return subscription;
    }

    /**
     * calculates total cost of the subscription
     * @param details List of SubscriptionDetailDto objects in the subscription
     * @return total price of the subscription
     */
    @LogInvocationService
    private BigDecimal calculateTotalCost(List<SubscriptionDetailDto> details) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (SubscriptionDetailDto detail : details) {
            BigDecimal periodicalPrice = detail.getPeriodicalCurrentPrice();
            totalCost = totalCost.add(periodicalPrice.multiply(BigDecimal.valueOf(detail.getSubscriptionDurationInYears())));
        }
        return totalCost;
    }

    @Override
    @LogInvocationService
    @ServiceEx
    @Transactional
    public SubscriptionDto updateSubscriptionStatus(SubscriptionDto.StatusDto status, Long id) {
        if (!status.equals(SubscriptionDto.StatusDto.CANCELED) && !SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new SubscriptionServiceException(messageSource.getMessage("msg.error.subscription.service.update", null, LocaleContextHolder.getLocale()));
        }

        if (status.equals(SubscriptionDto.StatusDto.COMPLETED)) {
            checkIfSubscriptionCanBeCompleted(id);
        }
        subscriptionRepository.updateSubscriptionStatus(Subscription.Status.valueOf(status.toString()), id);
        return findById(id);
    }

    /**
     * checks if the subscription can be completed
     * if not, an SubscriptionCompletedStatusException exception is thrown
     * @param id id of the subscription
     */
    private void checkIfSubscriptionCanBeCompleted(Long id) {
        SubscriptionDto subscriptionDto = findById(id);
        subscriptionDto.getSubscriptionDetailDtos().forEach(detail -> {
            if (detail.getSubscriptionEndDate().compareTo(LocalDate.now()) > 0) {
                throw new SubscriptionCompletedStatusException(messageSource.getMessage("msg.error.subscription.completed.status", null, LocaleContextHolder.getLocale()));
            }
        });
    }

    @Override
    @LogInvocationService
    public boolean checkIfSubscriptionExistsByUSer(Long id) {
        return subscriptionRepository.existsSubscriptionByUserId(id);
    }

    @Override
    @LogInvocationService
    public Page<SubscriptionDto> findAllSubscriptionsByUserId(Long id, Pageable pageable) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(messageSource.getMessage("msg.error.user.find.by.id", null,
                    LocaleContextHolder.getLocale()));
        }
        return subscriptionRepository.findAllByUserId(id, pageable).map(mapper::toDto);
    }

    @Override
    @LogInvocationService
    public Page<SubscriptionDto> filterSubscription(String type, Pageable pageable) {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setStatusDto(SubscriptionDto.StatusDto.valueOf(type));
        return subscriptionRepository.findAll(Example.of(mapper.toEntity(subscriptionDto), ExampleMatcher.matchingAny()), pageable)
                .map(mapper::toDto);
    }

    @Override
    @LogInvocationService
    public Page<SubscriptionDto> searchForSubscriptionByKeyword(String keyword, Pageable pageable) {
        Specification<Subscription> specification = SubscriptionSpecifications.hasIdLike(keyword)
                .or(SubscriptionSpecifications.hasTotalCostLike(keyword))
                .or(SubscriptionSpecifications.hasPeriodicalIdLike(keyword))
                .or(SubscriptionSpecifications.hasPeriodicalTitleLike(keyword))
                .or(SubscriptionSpecifications.hasUserIdLike(keyword))
                .or(SubscriptionSpecifications.hasUserEmailLike(keyword));
        return subscriptionRepository.findAll(specification, pageable).map(mapper::toDto);
    }
}
