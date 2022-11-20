package com.periodicalsubscription.controller;

import com.periodicalsubscription.TestObjectUtil;
import com.periodicalsubscription.constant.PageConstant;
import com.periodicalsubscription.controller.util.PagingUtil;
import com.periodicalsubscription.exceptions.user.UserAlreadyExistsException;
import com.periodicalsubscription.exceptions.user.UserNotFoundException;
import com.periodicalsubscription.service.api.UserService;
import com.periodicalsubscription.service.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private PagingUtil pagingUtil;
    @MockBean
    private MessageSource messageSource;
    private UserDto userDtoWithId;

    @BeforeEach
    public void setup() {
        userDtoWithId = TestObjectUtil.getUserDtoWithId();
    }

    @Test
    void whenRequestAllUsers_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/users/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/users"));
    }

    @Test
    void whenRequestOneUser_thenReturnCorrectViewWithModelAttribute() throws Exception {
        when(userService.findById(userDtoWithId.getId())).thenReturn(userDtoWithId);
        this.mockMvc.perform(get("/users/" + userDtoWithId.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userDtoWithId))
                .andExpect(view().name(PageConstant.USER));
    }

    @Test
    void whenRequestNonExistingUser_thenReturnCorrectViewAndExceptionMessage() throws Exception {
        when(userService.findById(userDtoWithId.getId())).thenThrow(UserNotFoundException.class);
        this.mockMvc.perform(get("/users/" + userDtoWithId.getId()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name(PageConstant.ERROR));
    }

    @Test
    void whenRequestUserCreationForm_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/users/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(PageConstant.SIGNUP));
    }

    @Test
    void givenAlreadyLoggedInUser_whenRequestUserCreationForm_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/users/create")
                        .sessionAttr("user", userDtoWithId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(PageConstant.ALREADY_LOGGED_IN));
    }

    @Test
    void whenSendUserCreationFormCorrectData_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        UserDto userDtoWithoutId = TestObjectUtil.getUserDtoWithoutId();
        when(userService.save(userDtoWithoutId)).thenReturn(userDtoWithId);
        this.mockMvc.perform(post("/users/create")
                        .param("firstName", userDtoWithoutId.getFirstName())
                        .param("lastName", userDtoWithoutId.getLastName())
                        .param("email", userDtoWithoutId.getEmail())
                        .param("password", userDtoWithoutId.getPassword())
                        .param("phoneNumber", userDtoWithoutId.getPhoneNumber()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/users/" + userDtoWithId.getId()))
                .andExpect(request().sessionAttribute("user", userDtoWithId))
                .andExpect(request().sessionAttribute("message", messageSource.getMessage("msg.success.user.created", null, LocaleContextHolder.getLocale())));
    }

    @Test
    void whenSendUserCreationFormIncorrectData_thenGetErrorsAndReturnSamePage() throws Exception {
        UserDto userDtoWithoutId = TestObjectUtil.getUserDtoWithoutId();
        this.mockMvc.perform(post("/users/create")
                        .param("firstName", userDtoWithoutId.getFirstName())
                        .param("lastName", userDtoWithoutId.getLastName())
                        .param("email", userDtoWithoutId.getEmail())
                        .param("phoneNumber", userDtoWithoutId.getPhoneNumber()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(view().name(PageConstant.SIGNUP));
    }

    @Test
    void whenSendUserCreationFormWithExistingEmail_thenReturnErrorViewAndExceptionMessage() throws Exception {
        UserDto userDtoWithoutId = TestObjectUtil.getUserDtoWithoutId();
        when(userService.save(userDtoWithoutId)).thenThrow(UserAlreadyExistsException.class);
        this.mockMvc.perform(post("/users/create")
                        .param("firstName", userDtoWithoutId.getFirstName())
                        .param("lastName", userDtoWithoutId.getLastName())
                        .param("email", userDtoWithoutId.getEmail())
                        .param("password", userDtoWithoutId.getPassword())
                        .param("phoneNumber", userDtoWithoutId.getPhoneNumber()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name(PageConstant.ERROR));
    }

    @Test
    void whenRequestUserUpdateForm_thenReturnCorrectView() throws Exception {
        when(userService.findById(userDtoWithId.getId())).thenReturn(userDtoWithId);
        this.mockMvc.perform(get("/users/update/" + userDtoWithId.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userDtoWithId))
                .andExpect(view().name(PageConstant.UPDATE_USER));
    }

    @Test
    void whenSendUserUpdateFormCorrectData_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        when(userService.update(userDtoWithId)).thenReturn(userDtoWithId);
        this.mockMvc.perform(post("/users/update")
                        .param("id", String.valueOf(userDtoWithId.getId()))
                        .param("firstName", userDtoWithId.getFirstName())
                        .param("lastName", userDtoWithId.getLastName())
                        .param("email", userDtoWithId.getEmail())
                        .param("password", userDtoWithId.getPassword())
                        .param("phoneNumber", userDtoWithId.getPhoneNumber())
                        .param("roleDto", userDtoWithId.getRoleDto().toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/users/" + userDtoWithId.getId()))
                .andExpect(request().sessionAttribute("message", messageSource.getMessage("msg.success.user.updated", null, LocaleContextHolder.getLocale())));
    }

    @Test
    void whenSendUserUpdateFormIncorrectData_thenGetErrorsAndReturnSamePage() throws Exception {
        this.mockMvc.perform(post("/users/update")
                        .param("id", String.valueOf(userDtoWithId.getId()))
                        .param("firstName", userDtoWithId.getFirstName())
                        .param("lastName", userDtoWithId.getLastName())
                        .param("email", userDtoWithId.getEmail())
                        .param("phoneNumber", userDtoWithId.getPhoneNumber()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name(PageConstant.UPDATE_USER));
    }

    @Test
    void whenRequestUserDeleteSuccessfully_thenReturnCorrectMessageAndView() throws Exception {
        doNothing().when(userService).deleteById(userDtoWithId.getId());
        this.mockMvc.perform(post("/users/delete/" + userDtoWithId.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/users/all"))
                .andExpect(request().sessionAttribute("message", messageSource.getMessage("msg.success.user.deleted", null, LocaleContextHolder.getLocale())));
    }

    @Test
    void whenSendUserUpdateFormCorrectDataAndRuntimeExceptionOccurs_thenReturnErrorPage() throws Exception {
        when(userService.update(userDtoWithId)).thenThrow(RuntimeException.class);
        this.mockMvc.perform(post("/users/update")
                        .param("id", String.valueOf(userDtoWithId.getId()))
                        .param("firstName", userDtoWithId.getFirstName())
                        .param("lastName", userDtoWithId.getLastName())
                        .param("email", userDtoWithId.getEmail())
                        .param("password", userDtoWithId.getPassword())
                        .param("phoneNumber", userDtoWithId.getPhoneNumber())
                        .param("roleDto", userDtoWithId.getRoleDto().toString()))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(view().name(PageConstant.ERROR));
    }
}
