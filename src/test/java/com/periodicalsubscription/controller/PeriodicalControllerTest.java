package com.periodicalsubscription.controller;

import com.periodicalsubscription.util.TestObjectUtil;
import com.periodicalsubscription.constant.PageConstant;
import com.periodicalsubscription.controller.util.PagingUtil;
import com.periodicalsubscription.exceptions.ImageUploadException;
import com.periodicalsubscription.exceptions.periodical.PeriodicalAlreadyExistsException;
import com.periodicalsubscription.exceptions.periodical.PeriodicalNotFoundException;
import com.periodicalsubscription.exceptions.periodical.PeriodicalServiceException;
import com.periodicalsubscription.service.api.PeriodicalService;
import com.periodicalsubscription.service.dto.PeriodicalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PeriodicalController.class)
@ExtendWith(MockitoExtension.class)
class PeriodicalControllerTest {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;
    @MockBean
    private PeriodicalService periodicalService;
    @MockBean
    private PagingUtil pagingUtil;
    @MockBean
    private MessageSource messageSource;
    private PeriodicalDto periodicalDtoWithId;

    @BeforeEach
    public void setup() {
        periodicalDtoWithId = TestObjectUtil.getPeriodicalDtoWithId();
    }

    @Test
    void whenRequestAllPeriodicals_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/periodicals/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("periodical/periodicals"));
    }

    @Test
    void whenRequestOnePeriodical_thenReturnCorrectViewWithModelAttribute() throws Exception {
        when(periodicalService.findById(periodicalDtoWithId.getId())).thenReturn(periodicalDtoWithId);
        this.mockMvc.perform(get("/periodicals/" + periodicalDtoWithId.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("periodical", periodicalDtoWithId))
                .andExpect(view().name(PageConstant.PERIODICAL));
    }

    @Test
    void whenRequestNonExistingPeriodical_thenReturnCorrectViewAndExceptionMessage() throws Exception {
        when(periodicalService.findById(periodicalDtoWithId.getId())).thenThrow(PeriodicalNotFoundException.class);
        this.mockMvc.perform(get("/periodicals/" + periodicalDtoWithId.getId()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name(PageConstant.ERROR));
    }

    @Test
    void whenRequestPeriodicalCreationForm_thenReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/periodicals/create"))
                .andExpect(status().isOk())
                .andExpect(view().name(PageConstant.CREATE_PERIODICAL));
    }

    @Test
    void whenSendPeriodicalCreationCorrectFormData_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        PeriodicalDto periodicalDtoFromCreationForm = TestObjectUtil.getPeriodicalDtoFromCreationForm();
        MockMultipartFile multipartFile = new MockMultipartFile("imageFile", "test_periodical.jpg", "text/plain", "some xml".getBytes());
        when(periodicalService.processPeriodicalCreation(periodicalDtoFromCreationForm, multipartFile)).thenReturn(periodicalDtoWithId);

        this.mockMvc.perform(multipart("/periodicals/create")
                        .file(multipartFile)
                        .param("title", periodicalDtoFromCreationForm.getTitle())
                        .param("publisher", periodicalDtoFromCreationForm.getPublisher())
                        .param("description", periodicalDtoFromCreationForm.getDescription())
                        .param("publicationDate", periodicalDtoFromCreationForm.getPublicationDate().toString())
                        .param("issuesAmountInYear", periodicalDtoFromCreationForm.getIssuesAmountInYear().toString())
                        .param("price", periodicalDtoFromCreationForm.getPrice().toString())
                        .param("language", periodicalDtoFromCreationForm.getLanguage())
                        .param("typeDto", periodicalDtoFromCreationForm.getTypeDto().toString())
                        .param("categoryDtos", periodicalDtoFromCreationForm.getCategoryDtos().get(0).getCategoryDto().toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/periodicals/" + periodicalDtoWithId.getId()))
                .andExpect(request().sessionAttribute("message", messageSource.getMessage("msg.success.periodical.created", null, LocaleContextHolder.getLocale())));
    }

    @Test
    void whenSendPeriodicalCreationIncorrectFormData_thenGetErrorsAndReturnSamePage() throws Exception {
        PeriodicalDto periodicalDtoFromCreationForm = TestObjectUtil.getPeriodicalDtoFromCreationForm();
        MockMultipartFile multipartFile = new MockMultipartFile("imageFile", "test_periodical.jpg", "text/plain", "some xml".getBytes());
        when(periodicalService.processPeriodicalCreation(periodicalDtoFromCreationForm, multipartFile)).thenReturn(periodicalDtoWithId);

        this.mockMvc.perform(multipart("/periodicals/create")
                        .file(multipartFile)
                        .param("title", periodicalDtoFromCreationForm.getTitle())
                        .param("publisher", periodicalDtoFromCreationForm.getPublisher())
                        .param("description", periodicalDtoFromCreationForm.getDescription())
                        .param("issuesAmountInYear", periodicalDtoFromCreationForm.getIssuesAmountInYear().toString())
                        .param("price", periodicalDtoFromCreationForm.getPrice().toString())
                        .param("language", periodicalDtoFromCreationForm.getLanguage())
                        .param("typeDto", periodicalDtoFromCreationForm.getTypeDto().toString())
                        .param("categoryDtos", periodicalDtoFromCreationForm.getCategoryDtos().get(0).getCategoryDto().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(view().name(PageConstant.CREATE_PERIODICAL));
    }

    @Test
    void whenSendPeriodicalCreationFormWithExistingTitle_thenReturnErrorViewAndExceptionMessage() throws Exception {
        PeriodicalDto periodicalDtoFromCreationForm = TestObjectUtil.getPeriodicalDtoFromCreationForm();
        MockMultipartFile multipartFile = new MockMultipartFile("imageFile", "test_periodical.jpg", "text/plain", "some xml".getBytes());
        when(periodicalService.processPeriodicalCreation(periodicalDtoFromCreationForm, multipartFile)).thenThrow(PeriodicalAlreadyExistsException.class);
        this.mockMvc.perform(multipart("/periodicals/create")
                        .file(multipartFile)
                        .param("title", periodicalDtoFromCreationForm.getTitle())
                        .param("publisher", periodicalDtoFromCreationForm.getPublisher())
                        .param("description", periodicalDtoFromCreationForm.getDescription())
                        .param("publicationDate", periodicalDtoFromCreationForm.getPublicationDate().toString())
                        .param("issuesAmountInYear", periodicalDtoFromCreationForm.getIssuesAmountInYear().toString())
                        .param("price", periodicalDtoFromCreationForm.getPrice().toString())
                        .param("language", periodicalDtoFromCreationForm.getLanguage())
                        .param("typeDto", periodicalDtoFromCreationForm.getTypeDto().toString())
                        .param("categoryDtos", periodicalDtoFromCreationForm.getCategoryDtos().get(0).getCategoryDto().toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name(PageConstant.ERROR));

    }

    @Test
    void whenRequestPeriodicalUpdateForm_thenReturnCorrectView() throws Exception {
        when(periodicalService.findById(periodicalDtoWithId.getId())).thenReturn(periodicalDtoWithId);
        this.mockMvc.perform(get("/periodicals/update/" + periodicalDtoWithId.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("periodical", periodicalDtoWithId))
                .andExpect(view().name(PageConstant.UPDATE_PERIODICAL));
    }

    @Test
    void whenSendPeriodicalUpdateCorrectFormData_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        PeriodicalDto periodicalDtoFromCreationForm = TestObjectUtil.getPeriodicalDtoFromCreationForm();
        MockMultipartFile multipartFile = new MockMultipartFile("imageFile", "test_periodical.jpg", "text/plain", "some xml".getBytes());
        when(periodicalService.processPeriodicalUpdate(periodicalDtoFromCreationForm, multipartFile)).thenReturn(periodicalDtoWithId);

        this.mockMvc.perform(multipart("/periodicals/update")
                        .file(multipartFile)
                        .param("title", periodicalDtoFromCreationForm.getTitle())
                        .param("publisher", periodicalDtoFromCreationForm.getPublisher())
                        .param("description", periodicalDtoFromCreationForm.getDescription())
                        .param("publicationDate", periodicalDtoFromCreationForm.getPublicationDate().toString())
                        .param("issuesAmountInYear", periodicalDtoFromCreationForm.getIssuesAmountInYear().toString())
                        .param("price", periodicalDtoFromCreationForm.getPrice().toString())
                        .param("language", periodicalDtoFromCreationForm.getLanguage())
                        .param("typeDto", periodicalDtoFromCreationForm.getTypeDto().toString())
                        .param("categoryDtos", periodicalDtoFromCreationForm.getCategoryDtos().get(0).getCategoryDto().toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/periodicals/" + periodicalDtoWithId.getId()))
                .andExpect(request().sessionAttribute("message", messageSource.getMessage("msg.success.periodical.updated", null, LocaleContextHolder.getLocale())));
    }

    @Test
    void whenSendPeriodicalUpdateIncorrectFormData_thenGetErrorsAndReturnSamePage() throws Exception {
        PeriodicalDto periodicalDtoFromUpdateForm = TestObjectUtil.getPeriodicalDtoFromUpdateForm();
        MockMultipartFile multipartFile = new MockMultipartFile("imageFile", "test_periodical.jpg", "text/plain", "some xml".getBytes());
        when(periodicalService.processPeriodicalUpdate(periodicalDtoFromUpdateForm, multipartFile)).thenReturn(periodicalDtoWithId);

        this.mockMvc.perform(multipart("/periodicals/update")
                        .file(multipartFile)
                        .param("title", periodicalDtoFromUpdateForm.getTitle())
                        .param("publisher", periodicalDtoFromUpdateForm.getPublisher())
                        .param("description", periodicalDtoFromUpdateForm.getDescription())
                        .param("issuesAmountInYear", periodicalDtoFromUpdateForm.getIssuesAmountInYear().toString())
                        .param("price", periodicalDtoFromUpdateForm.getPrice().toString())
                        .param("language", periodicalDtoFromUpdateForm.getLanguage())
                        .param("typeDto", periodicalDtoFromUpdateForm.getTypeDto().toString())
                        .param("categoryDtos", periodicalDtoFromUpdateForm.getCategoryDtos().get(0).getCategoryDto().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attributeExists("periodical"))
                .andExpect(view().name(PageConstant.UPDATE_PERIODICAL));
    }

    @Test
    void whenRequestPeriodicalUpdateStatus_thenReceiveCorrectDataAndDoRedirect() throws Exception {
        PeriodicalDto unavailablePeriodical = TestObjectUtil.getPeriodicalDtoWithId();
        unavailablePeriodical.setStatusDto(PeriodicalDto.StatusDto.UNAVAILABLE);
        when(periodicalService.updatePeriodicalStatus(PeriodicalDto.StatusDto.UNAVAILABLE, periodicalDtoWithId.getId())).thenReturn(unavailablePeriodical);
        this.mockMvc.perform(post("/periodicals/update/" + periodicalDtoWithId.getId() + "/status")
                        .param("statusDto", PeriodicalDto.StatusDto.UNAVAILABLE.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/periodicals/" + periodicalDtoWithId.getId()))
                .andExpect(request().sessionAttribute("message", messageSource.getMessage("msg.success.periodical.status.updated", null, LocaleContextHolder.getLocale())));
    }

    @Test
    void whenRequestPeriodicalDeleteSuccessfully_thenReturnCorrectMessageAndView() throws Exception {
        doNothing().when(periodicalService).deleteById(periodicalDtoWithId.getId());
        this.mockMvc.perform(post("/periodicals/delete/" + periodicalDtoWithId.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/periodicals/all"))
                .andExpect(request().sessionAttribute("message", messageSource.getMessage("msg.success.periodical.deleted", null, LocaleContextHolder.getLocale())));
    }

    @Test
    void whenRequestPeriodicalDeleteFailure_thenReturnErrorViewAndExceptionMessage() throws Exception {
        doThrow(PeriodicalServiceException.class).when(periodicalService).deleteById(periodicalDtoWithId.getId());
        this.mockMvc.perform(post("/periodicals/delete/" + periodicalDtoWithId.getId()))
                .andExpect(status().isInternalServerError())
                .andExpect(view().name(PageConstant.ERROR));
    }

    @Test
    void whenSendPeriodicalUpdateCorrectFormDataAndImageUploadExceptionOccurs_thenReturnErrorPage() throws Exception {
        PeriodicalDto periodicalDtoFromCreationForm = TestObjectUtil.getPeriodicalDtoFromCreationForm();
        MockMultipartFile multipartFile = new MockMultipartFile("imageFile", "test_periodical.jpg", "text/plain", "some xml".getBytes());
        when(periodicalService.processPeriodicalUpdate(periodicalDtoFromCreationForm, multipartFile)).thenThrow(ImageUploadException.class);

        this.mockMvc.perform(multipart("/periodicals/update")
                        .file(multipartFile)
                        .param("title", periodicalDtoFromCreationForm.getTitle())
                        .param("publisher", periodicalDtoFromCreationForm.getPublisher())
                        .param("description", periodicalDtoFromCreationForm.getDescription())
                        .param("publicationDate", periodicalDtoFromCreationForm.getPublicationDate().toString())
                        .param("issuesAmountInYear", periodicalDtoFromCreationForm.getIssuesAmountInYear().toString())
                        .param("price", periodicalDtoFromCreationForm.getPrice().toString())
                        .param("language", periodicalDtoFromCreationForm.getLanguage())
                        .param("typeDto", periodicalDtoFromCreationForm.getTypeDto().toString())
                        .param("categoryDtos", periodicalDtoFromCreationForm.getCategoryDtos().get(0).getCategoryDto().toString()))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(view().name(PageConstant.ERROR));
    }
}
