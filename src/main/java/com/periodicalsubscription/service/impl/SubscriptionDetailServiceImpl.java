package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.aspect.logging.annotation.LogInvocationService;
import com.periodicalsubscription.aspect.logging.annotation.ServiceEx;
import com.periodicalsubscription.dto.PeriodicalDto;
import com.periodicalsubscription.exceptions.subscriptiondetail.SubscriptionDetailNotFoundException;
import com.periodicalsubscription.exceptions.subscriptiondetail.SubscriptionDetailServiceException;
import com.periodicalsubscription.mapper.PeriodicalMapper;
import com.periodicalsubscription.mapper.SubscriptionDetailMapper;
import com.periodicalsubscription.model.repository.SubscriptionDetailRepository;
import com.periodicalsubscription.model.entity.SubscriptionDetail;
import com.periodicalsubscription.service.api.SubscriptionDetailService;
import com.periodicalsubscription.dto.SubscriptionDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionDetailServiceImpl implements SubscriptionDetailService {
    private final SubscriptionDetailRepository subscriptionDetailRepository;
    private final SubscriptionDetailMapper mapper;
    private final PeriodicalMapper periodicalMapper;
    private final MessageSource messageSource;

    @Override
    @LogInvocationService
    public Page<SubscriptionDetailDto> findAll(Pageable pageable) {
        return subscriptionDetailRepository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public SubscriptionDetailDto findById(Long id) {
        SubscriptionDetail detail = subscriptionDetailRepository.findById(id).orElseThrow(() -> {
            throw new SubscriptionDetailNotFoundException(messageSource.getMessage("msg.error.subscription.detail.find.by.id", null,
                    LocaleContextHolder.getLocale()));
        });

        return mapper.toDto(detail);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public SubscriptionDetailDto save(SubscriptionDetailDto dto) {
        SubscriptionDetailDto savedDetail = mapper.toDto(subscriptionDetailRepository.save(mapper.toEntity(dto)));
        if (savedDetail == null) {
            throw new SubscriptionDetailServiceException(messageSource.getMessage("msg.error.subscription.detail.service.save", null,
                    LocaleContextHolder.getLocale()));
        }
        return savedDetail;
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public SubscriptionDetailDto update(SubscriptionDetailDto dto) {
        SubscriptionDetailDto updatedDetail = mapper.toDto(subscriptionDetailRepository.save(mapper.toEntity(dto)));
        if (updatedDetail == null) {
            throw new SubscriptionDetailServiceException(messageSource.getMessage("msg.error.subscription.detail.service.update", null,
                    LocaleContextHolder.getLocale()));
        }
        return updatedDetail;
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public void deleteById(Long id) {
        subscriptionDetailRepository.deleteById(id);
        if (subscriptionDetailRepository.existsById(id)) {
            throw new SubscriptionDetailServiceException(messageSource.getMessage("msg.error.subscription.detail.service.delete", null,
                    LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @LogInvocationService
    @Transactional
    public void updateSubscriptionPeriod(LocalDate startDate, Integer subscriptionDuration, Long id) {
        LocalDate endDate = startDate.plusYears(subscriptionDuration).minusDays(1);

        subscriptionDetailRepository.updateSubscriptionStartDate(startDate, id);
        subscriptionDetailRepository.updateSubscriptionEndDate(endDate, id);
    }

    @Override
    @LogInvocationService
    public boolean checkIfSubscriptionExistsByPeriodical(PeriodicalDto periodicalDto) {
        return subscriptionDetailRepository.existsSubscriptionDetailByPeriodical(periodicalMapper.toEntity(periodicalDto));
    }
}
