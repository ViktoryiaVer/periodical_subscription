package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.SubscriptionDto;
import com.periodicalsubscription.service.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * interface with methods for subscription business logic
 */
public interface SubscriptionService {

    /**
     * finds all subscriptions
     * @param pageable Pageable object for result pagination
     * @return page of SubscriptionDto object
     */
    Page<SubscriptionDto> findAll(Pageable pageable);

    /**
     * finds a subscription by id
     * @param id id of the subscription
     * @return found subscription
     */
    SubscriptionDto findById(Long id);

    /**
     * saves the subscription
     * @param dto SubscriptionDto object to be saved
     * @return saved SubscriptionDto object
     */
    SubscriptionDto save(SubscriptionDto dto);

    /**
     * creates subscription from cart
     * @param userDto user which the subscription is created for
     * @param cart Map object with periodicals for subscription
     * @return created SubscriptionDto object
     */
    SubscriptionDto createSubscriptionFromCart(UserDto userDto, Map<Long, Integer> cart);

    /**
     * processes subscription in cart
     * @param userDto user which the subscription can be created for
     * @param cart Map object with periodicals for subscription
     * @return SubscriptionDto to be displayed as cart
     */
    SubscriptionDto processSubscriptionInCart(UserDto userDto, Map<Long, Integer> cart);

    /**
     * updates subscription status
     * @param status status to be set
     * @param id id of the subscription
     * @return updated subscription
     */
    SubscriptionDto updateSubscriptionStatus(SubscriptionDto.StatusDto status, Long id);

    /**
     * checks if subscription exists by user
     * @param id id of the user
     * @return true if exists, false otherwise
     */
    boolean checkIfSubscriptionExistsByUSer(Long id);

    /**
     * finds all subscriptions by user id
     * @param id id of the user
     * @param pageable Pageable object for result pagination
     * @return page of SubscriptionDto object
     */
    Page<SubscriptionDto> findAllSubscriptionsByUserId(Long id, Pageable pageable);

    /**
     * filters subscriptions
     * @param type type of the subscriptions to be filtered by
     * @param pageable Pageable object for result pagination
     * @return page of SubscriptionDto object
     */
    Page<SubscriptionDto> filterSubscription(String type, Pageable pageable);

    /**
     * searches for a subscription by a keyword
     * @param keyword word to be searched by
     * @param pageable Pageable object for result pagination
     * @return page of SubscriptionDto object
     */
    Page<SubscriptionDto> searchForSubscriptionByKeyword(String keyword, Pageable pageable);
}
