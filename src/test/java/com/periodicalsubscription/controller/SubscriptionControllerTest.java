package com.periodicalsubscription.controller;

import com.periodicalsubscription.service.api.UserService;
import com.periodicalsubscription.util.TestObjectUtil;
import com.periodicalsubscription.constant.PageConstant;
import com.periodicalsubscription.controller.util.PagingUtil;
import com.periodicalsubscription.exceptions.subscription.SubscriptionNotFoundException;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.service.dto.SubscriptionDto;
import com.periodicalsubscription.service.dto.UserDto;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SubscriptionController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ExtendWith(MockitoExtension.class)
class SubscriptionControllerTest {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;
    @MockBean
    private SubscriptionService subscriptionService;
    @MockBean
    private UserService userService;
    @MockBean
    private PagingUtil pagingUtil;
    @MockBean
    private MessageSource messageSource;
    private SubscriptionDto subscriptionDtoWithId;

    @BeforeEach
    public void setup() {
        subscriptionDtoWithId = TestObjectUtil.getSubscriptionDtoWithId();
    }

    @Test
    void whenRequestAllSubscriptions_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/subscriptions/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("subscription/subscriptions"));
    }

    @Test
    @WithMockUser
    void whenRequestAllSubscriptionsByUser_thenReturnCorrectView() throws Exception {
        UserDto userDto = TestObjectUtil.getUserDtoWithId();
        when(userService.findByUsername("user")).thenReturn(userDto);
        this.mockMvc.perform(get("/subscriptions/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("subscription/subscriptions"));
    }

    @Test
    void whenRequestOneSubscription_thenReturnCorrectViewWithModelAttribute() throws Exception {
        when(subscriptionService.findById(subscriptionDtoWithId.getId())).thenReturn(subscriptionDtoWithId);
        this.mockMvc.perform(get("/subscriptions/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("subscription", subscriptionDtoWithId))
                .andExpect(view().name(PageConstant.SUBSCRIPTION));
    }

    @Test
    @WithAnonymousUser
    void givenUserNotLoggedIn_whenRequestSubscriptionCreation_thenReturnLoginPage() throws Exception {
        when(userService.findByUsername("anonymous")).thenReturn(null);
        this.mockMvc.perform(post("/subscriptions/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", messageSource.getMessage("msg.error.login.required.subscription", null, LocaleContextHolder.getLocale())))
                .andExpect(view().name(PageConstant.LOGIN));
    }

    @Test
    @WithMockUser
    void givenUserLoggedIn_whenRequestSubscriptionCreation_thenReturnCorrectViewAndAttributesAndRedirect() throws Exception {
        UserDto userDto = TestObjectUtil.getUserDtoWithId();
        Map<Long, Integer> cart = TestObjectUtil.getCartWithOneItem();

        when(userService.findByUsername("user")).thenReturn(userDto);
        when(subscriptionService.createSubscriptionFromCart(userDto, cart)).thenReturn(TestObjectUtil.getSubscriptionDtoWithId());
        this.mockMvc.perform(post("/subscriptions/create")
                        .sessionAttr("cart", cart))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/subscriptions/" + subscriptionDtoWithId.getId()))
                .andExpect(request().sessionAttributeDoesNotExist("cart"))
                .andExpect(request().sessionAttribute("message", messageSource.getMessage("msg.success.subscription.created", null, LocaleContextHolder.getLocale())));
    }

    @Test
    void whenRequestSubscriptionUpdateStatus_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        SubscriptionDto cancelledSubscription = TestObjectUtil.getSubscriptionDtoWithId();
        cancelledSubscription.setStatusDto(SubscriptionDto.StatusDto.CANCELED);
        when(subscriptionService.updateSubscriptionStatus(SubscriptionDto.StatusDto.CANCELED, subscriptionDtoWithId.getId())).thenReturn(cancelledSubscription);
        this.mockMvc.perform(post("/subscriptions/update/" + subscriptionDtoWithId.getId())
                        .param("statusDto", SubscriptionDto.StatusDto.CANCELED.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/subscriptions/" + subscriptionDtoWithId.getId()))
                .andExpect(request().sessionAttribute("message", messageSource.getMessage("msg.success.subscription.status.updated", null, LocaleContextHolder.getLocale())));
    }

    @Test
    void whenRequestNonExistingSubscription_thenReturnCorrectViewAndExceptionMessage() throws Exception {
        when(subscriptionService.findById(subscriptionDtoWithId.getId())).thenThrow(SubscriptionNotFoundException.class);
        this.mockMvc.perform(get("/subscriptions/" + subscriptionDtoWithId.getId()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name(PageConstant.ERROR));
    }
}
