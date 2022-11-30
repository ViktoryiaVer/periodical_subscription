package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.util.TestObjectUtil;
import com.periodicalsubscription.model.entity.Payment;
import com.periodicalsubscription.model.repository.SubscriptionDetailRepository;
import com.periodicalsubscription.service.api.SubscriptionDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionDetailServiceImplTest {
    @Mock
    private SubscriptionDetailRepository subscriptionDetailRepository;
    private SubscriptionDetailService subscriptionDetailService;

    @BeforeEach
    public void setup() {
        subscriptionDetailService = new SubscriptionDetailServiceImpl(subscriptionDetailRepository);
    }

    @Test
    void whenUpdateSubscriptionPeriod_thenSubscriptionDetailIsUpdated() {
        Payment payment = TestObjectUtil.getPaymentWithOutId();
        LocalDate startDate = payment.getPaymentTime().toLocalDate();
        int subscriptionDuration = 1;
        Long detailId = 1L;
        LocalDate endDate = startDate.plusYears(subscriptionDuration).minusDays(1);

        subscriptionDetailService.updateSubscriptionPeriod(startDate, subscriptionDuration, detailId);

        verify(subscriptionDetailRepository, times(1)).updateSubscriptionStartDate(startDate, detailId);
        verify(subscriptionDetailRepository, times(1)).updateSubscriptionEndDate(endDate, detailId);
    }

    @Test
    void whenCheckIfSubscriptionDetailExistsByPeriodicalId_thenReturnTrue() {
        Long detailId = 1L;
        when(subscriptionDetailRepository.existsSubscriptionDetailByPeriodicalId(detailId)).thenReturn(true);
        assertTrue(subscriptionDetailService.checkIfSubscriptionDetailExistsByPeriodicalId(detailId));
    }

    @Test
    void whenCheckIfSubscriptionDetailNotExistsByPeriodicalId_thenReturnFalse() {
        Long detailId = 1L;
        when(subscriptionDetailRepository.existsSubscriptionDetailByPeriodicalId(detailId)).thenReturn(false);
        assertFalse(subscriptionDetailService.checkIfSubscriptionDetailExistsByPeriodicalId(detailId));
    }
}
