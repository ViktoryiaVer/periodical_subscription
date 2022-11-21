package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.util.TestObjectUtil;
import com.periodicalsubscription.service.api.CartService;
import com.periodicalsubscription.service.api.PeriodicalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {
    @Mock
    private PeriodicalService periodicalService;
    private CartService cartService;

    @BeforeEach
    void setup() {
        cartService = new CartServiceImpl(periodicalService);
    }

    @Test
    void whenAddFirstItemToCart_thenReturnMapWithOneItem() {
        Long periodicalId = 1L;
        Integer subscriptionDurationInYears = 1;
        Map<Long, Integer> cartWithOneItem = TestObjectUtil.getCartWithOneItem();

        doNothing().when(periodicalService).checkIfPeriodicalIsUnavailable(periodicalId);

        Map<Long, Integer> createdCart = cartService.add(periodicalId, subscriptionDurationInYears, null);

        assertEquals(cartWithOneItem, createdCart);
        assertEquals(1, createdCart.size());
        verify(periodicalService, times(1)).checkIfPeriodicalIsUnavailable(periodicalId);
    }

    @Test
    void whenAddNotFirstItemToCart_thenReturnMapWithOneAdditionalItem() {
        Long secondPeriodicalId = 2L;
        Integer subscriptionDurationInYears = 1;
        Map<Long, Integer> cartWithOneItem = TestObjectUtil.getCartWithOneItem();
        Map<Long, Integer> cartWithTwoItems = TestObjectUtil.getCartWithTwoItems();

        doNothing().when(periodicalService).checkIfPeriodicalIsUnavailable(secondPeriodicalId);

        Map<Long, Integer> updated = cartService.add(secondPeriodicalId, subscriptionDurationInYears, cartWithOneItem);

        assertEquals(cartWithTwoItems, updated);
        assertEquals(2, updated.size());
        verify(periodicalService, times(1)).checkIfPeriodicalIsUnavailable(secondPeriodicalId);
    }
}
