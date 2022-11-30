package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.PaymentDto;
import com.periodicalsubscription.service.dto.filter.PaymentFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * interface with methods for payment business logic
 */
public interface PaymentService {

    /**
     * finds all payments
     * @param pageable Pageable object for result pagination
     * @return page of PaymentDto object
     */
    Page<PaymentDto> findAll(Pageable pageable);

    /**
     * finds a payment by id
     * @param id id of the payment
     * @return found payment
     */
    PaymentDto findById(Long id);

    /**
     * saves payment
     * @param dto PaymentDto object to be saved
     * @return saved PaymentDto object
     */
    PaymentDto save(PaymentDto dto);

    /**
     * updates payment
     * @param dto PaymentDto object to be updated
     * @return updated PaymentDto object
     */
    PaymentDto update(PaymentDto dto);

    /**
     * processes payment registration
     * @param subscriptionId id of the subscription that the payment should be registered for
     * @param paymentTime time of the payment as LocalDateTime object
     * @param paymentMethod method of the payment
     * @return saved PaymentDto object
     */
    PaymentDto processPaymentRegistration(Long subscriptionId, String paymentTime, String paymentMethod);

    /**
     * processes payment update
     * @param paymentId id of the payment to be updated
     * @param paymentTime time of the payment to be set
     * @param paymentMethodDto method of the payment to be set
     * @return updated PaymentDto object
     */
    PaymentDto processPaymentUpdate(Long paymentId, String paymentTime, String paymentMethodDto);

    /**
     * filters Payments
     * @param filterDto filter object for payment filtering
     * @param pageable Pageable object for result pagination
     * @return page of PaymentDto object
     */
    Page<PaymentDto> filterPayment(PaymentFilterDto filterDto, Pageable pageable);

    /**
     * searchs for payments by a keyword
     * @param keyword word to be searched by
     * @param pageable Pageable object for result pagination
     * @return page of PaymentDto object
     */
    Page<PaymentDto> searchForPaymentByKeyword(String keyword, Pageable pageable);
}
