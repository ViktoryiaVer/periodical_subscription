package com.periodicalsubscription.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionDetailServiceImpl implements SubscriptionDetailService {
    private final SubscriptionDetailRepository subscriptionDetailRepository;
    private final SubscriptionDetailMapper mapper;
    private final PeriodicalMapper periodicalMapper;

    @Override
    public List<SubscriptionDetailDto> findAll() {
        return subscriptionDetailRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionDetailDto findById(Long id) {
        SubscriptionDetail detail = subscriptionDetailRepository.findById(id).orElseThrow(() -> {
            throw new SubscriptionDetailNotFoundException("Subscription detail with id " + id + " could not be found");
        });

        return mapper.toDto(detail);
    }

    @Override
    public SubscriptionDetailDto save(SubscriptionDetailDto dto) {
        SubscriptionDetailDto savedDetail = mapper.toDto(subscriptionDetailRepository.save(mapper.toEntity(dto)));
        if(savedDetail == null) {
            throw new SubscriptionDetailServiceException("Error while saving subscription detail.");
        }
        return savedDetail;
    }

    @Override
    public SubscriptionDetailDto update(SubscriptionDetailDto dto) {
        SubscriptionDetailDto updatedDetail = mapper.toDto(subscriptionDetailRepository.save(mapper.toEntity(dto)));
        if(updatedDetail == null) {
            throw new SubscriptionDetailServiceException("Error while updating subscription detail with id " + dto.getId() + ".");
        }
        return updatedDetail;
    }

    @Override
    public void deleteById(Long id) {
        subscriptionDetailRepository.deleteById(id);
        if (subscriptionDetailRepository.existsById(id)) {
            throw new SubscriptionDetailServiceException("Error while deleting subscription detail with id " + id + ".");
        }
    }

    @Transactional
    @Override
    public void updateSubscriptionPeriod(LocalDate startDate, Integer subscriptionDuration, Long id) {
        LocalDate endDate = startDate.plusYears(subscriptionDuration).minusDays(1);

        subscriptionDetailRepository.updateSubscriptionStartDate(startDate, id);
        subscriptionDetailRepository.updateSubscriptionEndDate(endDate, id);
    }

    @Override
    public boolean checkIfSubscriptionExistsByPeriodical(PeriodicalDto periodicalDto) {
        return subscriptionDetailRepository.existsSubscriptionDetailByPeriodical(periodicalMapper.toEntity(periodicalDto));
    }
}
