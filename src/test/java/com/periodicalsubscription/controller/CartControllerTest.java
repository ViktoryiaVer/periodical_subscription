package com.periodicalsubscription.controller;

import com.periodicalsubscription.TestObjectUtil;
import com.periodicalsubscription.constant.PageConstant;
import com.periodicalsubscription.exceptions.periodical.PeriodicalUnavailableException;
import com.periodicalsubscription.service.api.CartService;
import com.periodicalsubscription.service.api.SubscriptionService;
import com.periodicalsubscription.service.dto.SubscriptionDto;
import com.periodicalsubscription.service.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CartController.class)
@ExtendWith(MockitoExtension.class)
class CartControllerTest {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;
    @MockBean
    private SubscriptionService subscriptionService;
    @MockBean
    private CartService cartService;
    @MockBean
    private MessageSource messageSource;

    @Test
    void givenEmptyCart_whenRequestAddToCart_thenReturnCorrectViewAndAttribute() throws Exception {
        Long periodicalId = 1L;
        Integer subscriptionDurationInYears = 1;
        Map<Long, Integer> cart = TestObjectUtil.getCartWithOneItem();

        when(cartService.add(periodicalId, subscriptionDurationInYears, null)).thenReturn(cart);

        this.mockMvc.perform(post("/cart/add/" + periodicalId)
                        .param("id", periodicalId.toString())
                        .param("subscriptionDurationInYears", subscriptionDurationInYears.toString()))
                .andExpect(request().sessionAttribute("cart", cart))
                .andExpect(request().sessionAttribute("message", messageSource.getMessage("msg.success.cart.added", null,
                        LocaleContextHolder.getLocale())))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/periodicals/" + periodicalId));
    }

    @Test
    void givenNonEmptyCart_whenRequestAddToCart_thenReturnCorrectViewAndAttribute() throws Exception {
        Long secondPeriodicalId = 2L;
        Integer subscriptionDurationInYears = 1;
        Map<Long, Integer> cartWithOneItem = TestObjectUtil.getCartWithOneItem();
        Map<Long, Integer> cartWithTwoItems = TestObjectUtil.getCartWithTwoItems();

        when(cartService.add(secondPeriodicalId, subscriptionDurationInYears, cartWithOneItem)).thenReturn(cartWithTwoItems);

        this.mockMvc.perform(post("/cart/add/" + secondPeriodicalId)
                        .param("id", secondPeriodicalId.toString())
                        .param("subscriptionDurationInYears", subscriptionDurationInYears.toString())
                        .sessionAttr("cart", cartWithOneItem))
                .andExpect(request().sessionAttribute("cart", cartWithTwoItems))
                .andExpect(request().sessionAttribute("message", messageSource.getMessage("msg.success.cart.added", null,
                        LocaleContextHolder.getLocale())))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/periodicals/" + secondPeriodicalId));
    }

    @Test
    void givenEmptyCart_whenRequestShowCart_thenReturnCorrectViewAndAttribute() throws Exception {
        this.mockMvc.perform(get("/cart/show/"))
                .andExpect(model().attribute("message", messageSource.getMessage("msg.success.cart.empty", null,
                        LocaleContextHolder.getLocale())))
                .andExpect(request().sessionAttributeDoesNotExist("cart"))
                .andExpect(status().isOk())
                .andExpect(view().name(PageConstant.CART));
    }

    @Test
    void givenNonEmptyCart_whenRequestShowCart_thenReturnCorrectViewAndAttribute() throws Exception {
        UserDto userDto = TestObjectUtil.getUserDtoWithId();
        SubscriptionDto subscriptionDto = TestObjectUtil.getSubscriptionDtoWithId();
        Map<Long, Integer> cart = TestObjectUtil.getCartWithOneItem();

        when(subscriptionService.processSubscriptionInCart(userDto, cart)).thenReturn(subscriptionDto);

        this.mockMvc.perform(get("/cart/show/")
                        .sessionAttr("user", userDto)
                        .sessionAttr("cart", cart))
                .andExpect(model().attribute("cart", subscriptionDto))
                .andExpect(request().sessionAttribute("cart", cart))
                .andExpect(status().isOk())
                .andExpect(view().name(PageConstant.CART));
    }

    @Test
    void givenCartWithTwoItems_whenRequestDeleteItemInCart_thenReturnCorrectViewAndAttribute() throws Exception {
        Map<Long, Integer> cartWithOneItem = TestObjectUtil.getCartWithOneItem();
        Map<Long, Integer> cartWithTwoItems = TestObjectUtil.getCartWithTwoItems();
        long secondPeriodicalId = 2L;

        this.mockMvc.perform(post("/cart/delete/" + secondPeriodicalId)
                        .sessionAttr("cart", cartWithTwoItems))
                .andExpect(request().sessionAttribute("cart", cartWithOneItem))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/cart/show"));
    }

    @Test
    void givenCartWithOneItem_whenRequestDeleteItemInCart_thenReturnCorrectViewAndAttribute() throws Exception {
        Map<Long, Integer> cartWithOneItem = TestObjectUtil.getCartWithOneItem();
        long periodicalId = 1L;

        this.mockMvc.perform(post("/cart/delete/" + periodicalId)
                        .sessionAttr("cart", cartWithOneItem))
                .andExpect(request().sessionAttributeDoesNotExist("cart"))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute("message", messageSource.getMessage("msg.success.cart.empty", null,
                        LocaleContextHolder.getLocale())))
                .andExpect(header().string("Location", "/cart/show"));
    }

    @Test
    void whenRequestDeleteAllItemsInCart_thenReturnCorrectViewAndAttribute() throws Exception {
        Map<Long, Integer> cartWithTwoItems = TestObjectUtil.getCartWithTwoItems();

        this.mockMvc.perform(post("/cart/delete/all")
                        .sessionAttr("cart", cartWithTwoItems))
                .andExpect(request().sessionAttributeDoesNotExist("cart"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", messageSource.getMessage("msg.success.cart.empty", null,
                        LocaleContextHolder.getLocale())))
                .andExpect(view().name(PageConstant.CART));
    }


    @Test
    void givenUnavailablePeriodicalAndEmptyCart_whenRequestAddToCart_thenReturnCorrectViewAndExceptionMessage() throws Exception {
        Long periodicalId = 1L;
        Integer subscriptionDurationInYears = 1;

        when(cartService.add(periodicalId, subscriptionDurationInYears, null)).thenThrow(PeriodicalUnavailableException.class);

        this.mockMvc.perform(post("/cart/add/" + periodicalId)
                        .param("id", periodicalId.toString())
                        .param("subscriptionDurationInYears", subscriptionDurationInYears.toString()))
                .andExpect(request().sessionAttributeDoesNotExist("cart"))
                .andExpect(model().attributeExists("message"))
                .andExpect(status().isInternalServerError())
                .andExpect(view().name(PageConstant.ERROR));
    }

    @Test
    void givenUnavailablePeriodicalAndNonEmptyCart_whenRequestAddToCart_thenReturnCorrectViewAndExceptionMessage() throws Exception {
        Long secondPeriodicalId = 2L;
        Integer subscriptionDurationInYears = 1;
        Map<Long, Integer> cartWithOneItem = TestObjectUtil.getCartWithOneItem();

        when(cartService.add(secondPeriodicalId, subscriptionDurationInYears, cartWithOneItem)).thenThrow(PeriodicalUnavailableException.class);

        this.mockMvc.perform(post("/cart/add/" + secondPeriodicalId)
                        .sessionAttr("cart", cartWithOneItem)
                        .param("id", secondPeriodicalId.toString())
                        .param("subscriptionDurationInYears", subscriptionDurationInYears.toString()))
                .andExpect(request().sessionAttribute("cart", cartWithOneItem))
                .andExpect(model().attributeExists("message"))
                .andExpect(status().isInternalServerError())
                .andExpect(view().name(PageConstant.ERROR));
    }
}
