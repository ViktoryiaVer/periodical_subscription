package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.SubscriptionDto;
import com.periodicalsubscription.service.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface SubscriptionService {
    Page<SubscriptionDto> findAll(Pageable pageable);

    SubscriptionDto findById(Long id);

    SubscriptionDto save(SubscriptionDto dto);

    SubscriptionDto update(SubscriptionDto dto);

    void deleteById(Long id);

    SubscriptionDto createSubscriptionFromCart(UserDto userDto, Map<Long, Integer> cart);

    SubscriptionDto processSubscriptionInCart(UserDto userDto, Map<Long, Integer> cart);

    SubscriptionDto updateSubscriptionStatus(SubscriptionDto.StatusDto status, Long id);

    boolean checkIfSubscriptionExistsByUSer(Long id);

    Page<SubscriptionDto> findAllSubscriptionsByUserId(Long id, Pageable pageable);

    Page<SubscriptionDto> filterSubscription(String type, Pageable pageable);

    Page<SubscriptionDto> searchForSubscriptionByKeyword(String keyword, Pageable pageable);
}
