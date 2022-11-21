package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.util.TestObjectUtil;
import com.periodicalsubscription.exceptions.ImageUploadException;
import com.periodicalsubscription.exceptions.periodical.PeriodicalAlreadyExistsException;
import com.periodicalsubscription.exceptions.periodical.PeriodicalDeleteException;
import com.periodicalsubscription.exceptions.periodical.PeriodicalNotFoundException;
import com.periodicalsubscription.exceptions.periodical.PeriodicalServiceException;
import com.periodicalsubscription.exceptions.periodical.PeriodicalUnavailableException;
import com.periodicalsubscription.mapper.PeriodicalMapper;
import com.periodicalsubscription.model.entity.Periodical;
import com.periodicalsubscription.model.repository.PeriodicalRepository;
import com.periodicalsubscription.service.api.PeriodicalCategoryService;
import com.periodicalsubscription.service.api.PeriodicalService;
import com.periodicalsubscription.service.api.SubscriptionDetailService;
import com.periodicalsubscription.service.dto.PeriodicalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeriodicalServiceImplTest {
    @Mock
    private PeriodicalRepository periodicalRepository;
    @Mock
    private PeriodicalCategoryService periodicalCategoryService;
    @Mock
    private SubscriptionDetailService subscriptionDetailService;
    @Mock
    private PeriodicalMapper mapper;
    @Mock
    private MessageSource messageSource;
    private PeriodicalService periodicalService;
    private Periodical periodical;
    private PeriodicalDto periodicalDto;

    @BeforeEach
    public void setup() {
        periodicalService = new PeriodicalServiceImpl(periodicalRepository, periodicalCategoryService, subscriptionDetailService, mapper, messageSource);
        periodical = TestObjectUtil.getPeriodicalWithoutId();
        periodicalDto = TestObjectUtil.getPeriodicalDtoWithoutId();
    }

    @Test
    void whenFindAllPeriodicals_thenReturnPeriodicals() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Periodical> pagePeriodical = new PageImpl<>(new ArrayList<>());
        Page<PeriodicalDto> pagePeriodicalDto = new PageImpl<>(new ArrayList<>());

        when(periodicalRepository.findAll(pageable)).thenReturn(pagePeriodical);
        when(periodicalRepository.findAll(pageable).map(mapper::toDto)).thenReturn(pagePeriodicalDto);

        Page<PeriodicalDto> foundPage = periodicalService.findAll(pageable);

        assertNotNull(foundPage);
        verify(periodicalRepository, times(1)).findAll(pageable);
    }

    @Test
    void whenFindExitingPeriodicalById_thenReturnPeriodical() {
        Long periodicalId = 1L;
        when(periodicalRepository.findById(periodicalId)).thenReturn(Optional.of(periodical));
        mockMapperToDto();

        PeriodicalDto foundPeriodical = periodicalService.findById(periodicalId);

        assertEquals(periodicalDto, foundPeriodical);
        verify(periodicalRepository, times(1)).findById(periodicalId);
    }

    @Test
    void whenFindNonExitingPeriodicalById_thenThrowException() {
        Long periodicalId = 1L;
        when(periodicalRepository.findById(periodicalId)).thenReturn(Optional.empty());

        assertThrows(PeriodicalNotFoundException.class, () -> periodicalService.findById(periodicalId));
        verify(mapper, never()).toDto(any(Periodical.class));
    }

    @Test
    void whenSaveNewPeriodical_thenReturnNewPeriodical() {
        when(periodicalRepository.existsByTitle(periodical.getTitle())).thenReturn(false);
        mockMapperToEntity();
        when(periodicalRepository.save(periodical)).thenReturn(periodical);
        mockMapperToDto();

        PeriodicalDto savedPeriodical = periodicalService.save(periodicalDto);

        assertEquals(periodicalDto, savedPeriodical);
        verify(periodicalRepository, times(1)).save(any(Periodical.class));
    }

    @Test
    void whenSavePeriodicalWithExistingTitle_thenThrowException() {
        when(periodicalRepository.existsByTitle(periodical.getTitle())).thenReturn(true);

        assertThrows(PeriodicalAlreadyExistsException.class, () -> periodicalService.save(periodicalDto));
        verify(periodicalRepository, never()).save(any(Periodical.class));
    }

    @Test
    void whenUpdatePeriodical_thenReturnUpdatedPeriodical() {
        periodical = TestObjectUtil.getPeriodicalWithId();
        periodicalDto = TestObjectUtil.getPeriodicalDtoWithId();

        when(periodicalRepository.findByTitle(periodical.getTitle())).thenReturn(Optional.ofNullable(periodical));
        mockMapperToEntity();
        when(periodicalRepository.save(periodical)).thenReturn(periodical);
        mockMapperToDto();

        periodical.setPublisher("Updated Publisher Test");
        periodical.setDescription("Updated Description Test");
        periodicalDto.setPublisher("Updated Publisher Test");
        periodicalDto.setDescription("Updated Description Test");

        PeriodicalDto updatedPeriodical = periodicalService.update(periodicalDto);
        assertEquals(periodicalDto, updatedPeriodical);
        verify(periodicalRepository, times(1)).save(any(Periodical.class));
    }

    @Test
    void whenUpdatePeriodicalWithSameTitleButAnotherId_thenThrowException() {
        periodical = TestObjectUtil.getPeriodicalWithId();
        periodicalDto = TestObjectUtil.getPeriodicalDtoWithId();
        Periodical existingPeriodical = TestObjectUtil.getPeriodicalWithId();
        existingPeriodical.setId(2L);

        when(periodicalRepository.findByTitle(periodical.getTitle())).thenReturn(Optional.of(existingPeriodical));

        assertThrows(PeriodicalAlreadyExistsException.class, () -> periodicalService.update(periodicalDto));
        verify(periodicalRepository, never()).save(any(Periodical.class));
    }

    @Test
    void whenDeletePeriodicalWithoutSubscriptions_thenPeriodicalIsDeleted() {
        Long periodicalId = 1L;
        when(subscriptionDetailService.checkIfSubscriptionDetailExistsByPeriodicalId(periodicalId)).thenReturn(false);
        doNothing().when(periodicalRepository).deleteById(periodicalId);

        periodicalService.deleteById(periodicalId);
        verify(periodicalRepository, times(1)).deleteById(periodicalId);
    }

    @Test
    void whenDeletePeriodicalWithSubscriptions_thenThrowException() {
        Long periodicalId = 1L;

        when(subscriptionDetailService.checkIfSubscriptionDetailExistsByPeriodicalId(periodicalId)).thenReturn(true);

        assertThrows(PeriodicalDeleteException.class, () -> periodicalService.deleteById(periodicalId));
        verify(periodicalRepository, never()).deleteById(periodicalId);
    }

    @Test
    void whenFailureWhileDeletingPeriodical_thenThroeException() {
        Long periodicalId = 1L;

        when(subscriptionDetailService.checkIfSubscriptionDetailExistsByPeriodicalId(periodicalId)).thenReturn(false);
        doNothing().when(periodicalRepository).deleteById(periodicalId);
        when(periodicalRepository.existsById(periodicalId)).thenReturn(true);

        assertThrows(PeriodicalServiceException.class, () -> periodicalService.deleteById(periodicalId));
    }

    @Test
    void whenProcessPeriodicalCreationFromRequest_thenPeriodicalIsCreated() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn(periodical.getImagePath());
        File file = new File("periodicals/" + periodical.getImagePath());

        doNothing().when(multipartFile).transferTo(file);
        when(periodicalRepository.existsByTitle(periodicalDto.getTitle())).thenReturn(false);
        mockMapperToEntity();
        when(periodicalRepository.save(periodical)).thenReturn(periodical);
        mockMapperToDto();

        PeriodicalDto savedPeriodical = periodicalService.processPeriodicalCreation(this.periodicalDto, multipartFile);

        assertEquals(periodicalDto, savedPeriodical);
        verify(multipartFile, times(1)).getOriginalFilename();
        verify(multipartFile, times(1)).transferTo(file);
        verify(periodicalRepository, times(1)).existsByTitle(periodicalDto.getTitle());
        verify(periodicalRepository, times(1)).save(periodical);
    }

    @Test
    void whenProcessPeriodicalCreationFromRequestAndIoExceptionOccurs_thenThrowCustomException() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        File file = new File("periodicals/" + periodical.getImagePath());

        when(multipartFile.getOriginalFilename()).thenReturn(periodical.getImagePath());
        doThrow(IOException.class).when(multipartFile).transferTo(file);

        assertThrows(ImageUploadException.class, () -> periodicalService.processPeriodicalCreation(this.periodicalDto, multipartFile));
        verify(multipartFile, times(1)).getOriginalFilename();
        verify(multipartFile, times(1)).transferTo(file);
        verify(periodicalRepository, never()).existsByTitle(periodicalDto.getTitle());
        verify(periodicalRepository, never()).save(periodical);
    }

    @Test
    void givenNotEmptyMultipartFile_whenProcessPeriodicalUpdate_thenReturnUpdatedPeriodical() throws IOException {
        periodical = TestObjectUtil.getPeriodicalWithId();
        periodicalDto = TestObjectUtil.getPeriodicalDtoWithId();
        MultipartFile multipartFile = mock(MultipartFile.class);
        File file = new File("periodicals/" + periodical.getImagePath());

        when(multipartFile.getOriginalFilename()).thenReturn(periodical.getImagePath());
        doNothing().when(multipartFile).transferTo(file);
        doNothing().when(periodicalCategoryService).deleteAllCategoriesForPeriodical(periodicalDto);
        when(periodicalRepository.findByTitle(periodicalDto.getTitle())).thenReturn(Optional.ofNullable(periodical));
        mockMapperToEntity();
        when(periodicalRepository.save(periodical)).thenReturn(periodical);
        mockMapperToDto();

        PeriodicalDto updatedPeriodical = periodicalService.processPeriodicalUpdate(this.periodicalDto, multipartFile);

        assertEquals(periodicalDto, updatedPeriodical);
        verify(multipartFile, times(1)).getOriginalFilename();
        verify(multipartFile, times(1)).transferTo(file);
        verify(periodicalCategoryService, times(1)).deleteAllCategoriesForPeriodical(periodicalDto);
        verify(periodicalRepository, times(1)).findByTitle(periodicalDto.getTitle());
        verify(periodicalRepository, times(1)).save(periodical);
    }

    @Test
    void givenEmptyMultipartFile_whenProcessPeriodicalUpdate_thenReturnUpdatedPeriodical() throws IOException {
        periodical = TestObjectUtil.getPeriodicalWithId();
        periodicalDto = TestObjectUtil.getPeriodicalDtoWithId();
        MultipartFile multipartFile = mock(MultipartFile.class);

        doNothing().when(periodicalCategoryService).deleteAllCategoriesForPeriodical(periodicalDto);
        when(periodicalRepository.findByTitle(periodicalDto.getTitle())).thenReturn(Optional.ofNullable(periodical));
        mockMapperToEntity();
        when(periodicalRepository.save(periodical)).thenReturn(periodical);
        mockMapperToDto();

        PeriodicalDto updatedPeriodical = periodicalService.processPeriodicalUpdate(this.periodicalDto, multipartFile);

        assertEquals(periodicalDto, updatedPeriodical);
        verify(multipartFile, never()).transferTo(new File("periodicals" + periodicalDto.getImagePath()));
        verify(periodicalCategoryService, times(1)).deleteAllCategoriesForPeriodical(periodicalDto);
        verify(periodicalRepository, times(1)).findByTitle(periodicalDto.getTitle());
        verify(periodicalRepository, times(1)).save(periodical);
    }

    @Test
    void whenUpdatePeriodicalStatusSetUnavailable_thenReturnUpdatedPeriodical() {
        Long periodicalId = 1L;
        doNothing().when(periodicalRepository).updatePeriodicalStatus(Periodical.Status.UNAVAILABLE, periodicalId);
        when(periodicalRepository.findById(periodicalId)).thenReturn(Optional.of(periodical));
        mockMapperToDto();

        periodical.setStatus(Periodical.Status.UNAVAILABLE);
        periodicalDto.setStatusDto(PeriodicalDto.StatusDto.UNAVAILABLE);

        PeriodicalDto updatedPeriodical = periodicalService.updatePeriodicalStatus(PeriodicalDto.StatusDto.UNAVAILABLE, periodicalId);

        assertEquals(periodicalDto, updatedPeriodical);
        verify(periodicalRepository, times(1)).updatePeriodicalStatus(Periodical.Status.UNAVAILABLE, periodicalId);
    }

    @Test
    void givenUnavailablePeriodical_whenCheckIfPeriodicalIsUnavailable_thenExceptionIsThrown() {
        Long periodicalId = 1L;
        when(periodicalRepository.existsPeriodicalByStatusEqualsAndId(Periodical.Status.UNAVAILABLE, periodicalId)).thenReturn(true);
        assertThrows(PeriodicalUnavailableException.class, () -> periodicalService.checkIfPeriodicalIsUnavailable(periodicalId));
    }

    @Test
    void givenAvailablePeriodical_whenCheckIfPeriodicalIsUnavailable_thenNoExceptionIsThrown() {
        Long periodicalId = 1L;
        when(periodicalRepository.existsPeriodicalByStatusEqualsAndId(Periodical.Status.UNAVAILABLE, periodicalId)).thenReturn(false);
        assertDoesNotThrow(() -> periodicalService.checkIfPeriodicalIsUnavailable(periodicalId));
    }

    private void mockMapperToEntity() {
        when(mapper.toEntity(periodicalDto)).thenReturn(periodical);
    }

    private void mockMapperToDto() {
        when(mapper.toDto(periodical)).thenReturn(periodicalDto);
    }
}
