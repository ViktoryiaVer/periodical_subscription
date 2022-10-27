package com.periodicalsubscription.controller;

import com.periodicalsubscription.dto.PaymentDto;
import com.periodicalsubscription.manager.PageManager;
import com.periodicalsubscription.service.api.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping(value = "/all")
    public String payments(Model model) {
        List<PaymentDto> payments = paymentService.findAll();

        if(payments.isEmpty()) {
            model.addAttribute("message", "No payments could be found");
            return PageManager.PAYMENTS;
        }
        model.addAttribute("payments", payments);

        return PageManager.PAYMENTS;
    }
}
