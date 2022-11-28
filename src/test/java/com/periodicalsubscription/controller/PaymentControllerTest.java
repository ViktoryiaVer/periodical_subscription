package com.periodicalsubscription.controller;

import com.periodicalsubscription.util.TestObjectUtil;
import com.periodicalsubscription.constant.PageConstant;
import com.periodicalsubscription.controller.util.PagingUtil;
import com.periodicalsubscription.exceptions.payment.PaymentNotFoundException;
import com.periodicalsubscription.service.api.PaymentService;
import com.periodicalsubscription.service.dto.PaymentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PaymentController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;
    @MockBean
    private PagingUtil pagingUtil;
    @MockBean
    private PaymentService paymentService;
    @MockBean
    private MessageSource messageSource;
    private PaymentDto paymentDtoWithId;

    @BeforeEach
    public void setup() {
        paymentDtoWithId = TestObjectUtil.getPaymentDtoWithId();
    }

    @Test
    void whenRequestAllPayments_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/payments/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("payment/payments"));
    }

    @Test
    void whenRequestNonExistingPayment_thenReturnCorrectViewAndExceptionMessage() throws Exception {
        when(paymentService.findById(paymentDtoWithId.getId())).thenThrow(PaymentNotFoundException.class);
        this.mockMvc.perform(get("/payments/" + paymentDtoWithId.getId()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name(PageConstant.ERROR));
    }

    @Test
    void whenRequestPaymentRegistrationForm_thenReturnCorrectView() throws Exception {
        PaymentDto paymentDtoWithoutId = TestObjectUtil.getPaymentDtoWithoutId();
        this.mockMvc.perform(get("/payments/register/" + paymentDtoWithoutId.getSubscriptionDto().getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(PageConstant.CREATE_PAYMENT));
    }

    @Test
    void whenSendPaymentRegistrationFormCorrectData_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        PaymentDto paymentDtoWithoutId = TestObjectUtil.getPaymentDtoWithoutId();
        when(paymentService.processPaymentRegistration(paymentDtoWithoutId.getSubscriptionDto().getId(),
                paymentDtoWithoutId.getPaymentTime().toString(), paymentDtoWithoutId.getPaymentMethodDto().toString()))
                .thenReturn(paymentDtoWithId);
        this.mockMvc.perform(post("/payments/register")
                        .param("subscriptionId", paymentDtoWithoutId.getSubscriptionDto().getId().toString())
                        .param("paymentTime", paymentDtoWithoutId.getPaymentTime().toString())
                        .param("paymentMethodDto", paymentDtoWithoutId.getPaymentMethodDto().toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/payments/" + paymentDtoWithId.getId()))
                .andExpect(request().sessionAttribute("message", messageSource.getMessage("msg.success.payment.registered", null, LocaleContextHolder.getLocale())));
    }

    @Test
    void whenRequestPaymentUpdateForm_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/payments/update/" + paymentDtoWithId.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(PageConstant.UPDATE_PAYMENT));
    }

    @Test
    void whenSendPaymentUpdateForm_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        when(paymentService.processPaymentUpdate(paymentDtoWithId.getId(),
                paymentDtoWithId.getPaymentTime().toString(), paymentDtoWithId.getPaymentMethodDto().toString()))
                .thenReturn(paymentDtoWithId);
        this.mockMvc.perform(post("/payments/update")
                        .param("paymentId", paymentDtoWithId.getId().toString())
                        .param("paymentTime", paymentDtoWithId.getPaymentTime().toString())
                        .param("paymentMethodDto", paymentDtoWithId.getPaymentMethodDto().toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/payments/" + paymentDtoWithId.getId()))
                .andExpect(request().sessionAttribute("message", messageSource.getMessage("msg.success.payment.updated", null, LocaleContextHolder.getLocale())));
    }

    @Test
    void whenRequestOnePayment_thenReturnCorrectViewWithModelAttribute() throws Exception {
        when(paymentService.findById(paymentDtoWithId.getId())).thenReturn(paymentDtoWithId);
        this.mockMvc.perform(get("/payments/" + paymentDtoWithId.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("payment", paymentDtoWithId))
                .andExpect(view().name(PageConstant.PAYMENT));
    }

    @Test
    void whenRequestOnePaymentAndFormatExceptionOccurs_thenReturnErrorPage() throws Exception {
        when(paymentService.findById(paymentDtoWithId.getId())).thenThrow(MethodArgumentTypeMismatchException.class);
        this.mockMvc.perform(get("/payments/" + paymentDtoWithId.getId()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(view().name(PageConstant.ERROR));
    }
}
