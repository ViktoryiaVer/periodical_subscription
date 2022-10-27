package com.periodicalsubscription.controller;

import com.periodicalsubscription.dto.PaymentDto;
import com.periodicalsubscription.dto.SubscriptionDto;
import com.periodicalsubscription.manager.PageManager;
import com.periodicalsubscription.service.api.PaymentService;
import com.periodicalsubscription.service.api.SubscriptionDetailService;
import com.periodicalsubscription.service.api.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final SubscriptionService subscriptionService;
    private final SubscriptionDetailService subscriptionDetailService;

    @GetMapping("/all")
    public String getAllPayments(Model model) {
        List<PaymentDto> payments = paymentService.findAll();

        if(payments.isEmpty()) {
            model.addAttribute("message", "No payments could be found");
            return PageManager.PAYMENTS;
        }
        model.addAttribute("payments", payments);

        return PageManager.PAYMENTS;
    }

    @GetMapping("/{id}")
    public String getPayment(@PathVariable Long id, Model model) {
        PaymentDto payment = paymentService.findById(id);

        model.addAttribute("payment", payment);

        return PageManager.PAYMENT;
    }

    @GetMapping("/register/{id}")
    public String createPaymentForm(@PathVariable("id") Long subscriptionId, Model model) {
        model.addAttribute("subscriptionId", subscriptionId);
        return PageManager.CREATE_PAYMENT;
    }

    @PostMapping("/register")
    public String createPayment(@RequestParam Long subscriptionId, @RequestParam String paymentTime, @RequestParam String paymentMethodDto, Model model) {
        PaymentDto paymentDto = paymentService.processPaymentRegistration(subscriptionId, paymentTime, paymentMethodDto);
        PaymentDto savedPayment = paymentService.save(paymentDto);
        SubscriptionDto subscriptionDto = subscriptionService.updateSubscriptionStatus(SubscriptionDto.StatusDto.PAYED, paymentDto.getSubscriptionDto().getId());
        subscriptionDto.getSubscriptionDetailDtos().forEach((detail -> subscriptionDetailService.updateSubscriptionPeriod(savedPayment.getPaymentTime().toLocalDate(), detail.getSubscriptionDurationInYears(), detail.getId())));

        model.addAttribute("message", "Payment was registered successfully");
        model.addAttribute("payment", savedPayment);
        return PageManager.PAYMENT;
    }

    @GetMapping("/update/{id}")
    public String updatePaymentForm(@PathVariable Long id, Model model) {
        PaymentDto paymentDto = paymentService.findById(id);
        model.addAttribute("payment", paymentDto);
        return PageManager.UPDATE_PAYMENT;
    }

    @PostMapping("/update")
    public String updatePayment(@RequestParam Long paymentId, String paymentTime, String paymentMethodDto, Model model) {
        PaymentDto foundPayment = paymentService.findById(paymentId);
        foundPayment.setPaymentTime(LocalDateTime.parse(paymentTime));
        foundPayment.setPaymentMethodDto(PaymentDto.PaymentMethodDto.valueOf(paymentMethodDto));

        PaymentDto updatedPayment = paymentService.update(foundPayment);
        model.addAttribute("message", "Payment was updated successfully");
        model.addAttribute("payment", updatedPayment);
        return PageManager.PAYMENT;
    }
}
