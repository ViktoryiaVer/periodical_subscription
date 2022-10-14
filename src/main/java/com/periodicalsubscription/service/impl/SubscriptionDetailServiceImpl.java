package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.model.dao.SubscriptionDetailRepository;
import com.periodicalsubscription.model.entity.SubscriptionDetail;
import com.periodicalsubscription.service.api.SubscriptionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionDetailServiceImpl implements SubscriptionDetailService {
    private final SubscriptionDetailRepository subscriptionDetailRepository;
    @Override
    public List<SubscriptionDetail> findAll() {
        return subscriptionDetailRepository.findAll();
    }

    @Override
    public SubscriptionDetail findById(Long id) {
        Optional<SubscriptionDetail> subscriptionDetail = subscriptionDetailRepository.findById(id);

        return subscriptionDetail.orElseThrow(RuntimeException::new);
    }

    @Override
    public SubscriptionDetail save(SubscriptionDetail subscriptionDetail) {
        //TODO some validation
        return subscriptionDetailRepository.save(subscriptionDetail);
    }

    @Override
    public SubscriptionDetail update(SubscriptionDetail subscriptionDetail) {
        //TODO some validation
        return subscriptionDetailRepository.save(subscriptionDetail);
    }

    @Override
    public void delete(SubscriptionDetail subscriptionDetail) {
        //TODO some validation?
        subscriptionDetailRepository.delete(subscriptionDetail);
    }
}
