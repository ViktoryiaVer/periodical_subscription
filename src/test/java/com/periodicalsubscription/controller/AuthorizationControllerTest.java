package com.periodicalsubscription.controller;

import com.periodicalsubscription.util.TestObjectUtil;
import com.periodicalsubscription.constant.PageConstant;
import com.periodicalsubscription.exceptions.LoginException;
import com.periodicalsubscription.service.api.UserService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthorizationController.class)
@ExtendWith(MockitoExtension.class)
class AuthorizationControllerTest {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private MessageSource messageSource;

    @Test
    void givenUserNotLoggedIn_whenRequestLoginForm_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(PageConstant.LOGIN));
    }

    @Test
    void givenUserAlreadyLoggedIn_whenRequestLoginForm_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/login")
                        .sessionAttr("user", TestObjectUtil.getUserDtoWithId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(PageConstant.ALREADY_LOGGED_IN));
    }

    @Test
    void whenSendLoginFormCorrectData_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        UserDto userDto = TestObjectUtil.getUserDtoWithId();
        when(userService.login(userDto.getEmail(), userDto.getPassword())).thenReturn(userDto);

        this.mockMvc.perform(post("/login")
                        .param("email", userDto.getEmail())
                        .param("password", userDto.getPassword()))
                .andExpect(request().sessionAttribute("user", userDto))
                .andExpect(model().attribute("message", messageSource.getMessage("msg.success.user.logged.in", null,
                        LocaleContextHolder.getLocale())))
                .andExpect(status().isOk())
                .andExpect(view().name(PageConstant.HOME));
    }

    @Test
    void whenSendLoginFormEmptyData_thenReceiveCorrectMessageAndView() throws Exception {
        UserDto userDto = TestObjectUtil.getUserDtoWithId();
        when(userService.login(userDto.getEmail(), userDto.getPassword())).thenReturn(userDto);

        this.mockMvc.perform(post("/login")
                        .param("email", "")
                        .param("password", userDto.getPassword()))
                .andExpect(request().sessionAttributeDoesNotExist("user"))
                .andExpect(model().attribute("message", messageSource.getMessage("msg.error.login.data.not.specified", null,
                        LocaleContextHolder.getLocale())))
                .andExpect(status().isOk())
                .andExpect(view().name(PageConstant.LOGIN));
    }

    @Test
    void whenSendLoginFormInCorrectEmail_thenReturnCorrectViewAndExceptionMessage() throws Exception {
        UserDto userDto = TestObjectUtil.getUserDtoWithId();
        when(userService.login(userDto.getEmail(), userDto.getPassword())).thenThrow(LoginException.class);

        this.mockMvc.perform(post("/login")
                        .param("email", userDto.getEmail())
                        .param("password", userDto.getPassword()))
                .andExpect(request().sessionAttributeDoesNotExist("user"))
                .andExpect(model().attributeExists("message"))
                .andExpect(status().isUnauthorized())
                .andExpect(view().name(PageConstant.LOGIN));
    }

    @Test
    void givenUserAndCartInSession_whenRequestLogout_thenReturnCorrectViewAndAttributes() throws Exception {
        UserDto userDto = TestObjectUtil.getUserDtoWithId();
        Map<Long, Integer> cart = TestObjectUtil.getCartWithOneItem();

        this.mockMvc.perform(get("/logout")
                        .sessionAttr("user", userDto)
                        .sessionAttr("cart", cart))
                .andExpect(request().sessionAttributeDoesNotExist("user"))
                .andExpect(request().sessionAttributeDoesNotExist("cart"))
                .andExpect(model().attribute("message", messageSource.getMessage("msg.success.user.logged.out", null,
                        LocaleContextHolder.getLocale())))
                .andExpect(status().isOk())
                .andExpect(view().name(PageConstant.HOME));
    }
}
