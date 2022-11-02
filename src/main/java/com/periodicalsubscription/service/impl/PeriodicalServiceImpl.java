package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.aspect.logging.annotation.ImageUploadEx;
import com.periodicalsubscription.aspect.logging.annotation.LogInvocationService;
import com.periodicalsubscription.aspect.logging.annotation.ServiceEx;
import com.periodicalsubscription.exceptions.ImageUploadException;
import com.periodicalsubscription.exceptions.periodical.PeriodicalAlreadyExistsException;
import com.periodicalsubscription.exceptions.periodical.PeriodicalDeleteException;
import com.periodicalsubscription.exceptions.periodical.PeriodicalNotFoundException;
import com.periodicalsubscription.exceptions.periodical.PeriodicalServiceException;
import com.periodicalsubscription.mapper.PeriodicalMapper;
import com.periodicalsubscription.model.repository.PeriodicalRepository;
import com.periodicalsubscription.model.entity.Periodical;
import com.periodicalsubscription.service.api.PeriodicalCategoryService;
import com.periodicalsubscription.service.api.PeriodicalService;
import com.periodicalsubscription.dto.PeriodicalDto;
import com.periodicalsubscription.service.api.SubscriptionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PeriodicalServiceImpl implements PeriodicalService {
    private final PeriodicalRepository periodicalRepository;
    private final PeriodicalCategoryService periodicalCategoryService;
    private final SubscriptionDetailService subscriptionDetailService;
    private final PeriodicalMapper mapper;

    @Override
    @LogInvocationService
    public List<PeriodicalDto> findAll() {
        return periodicalRepository.findAllDistinctFetchCategories().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public PeriodicalDto findById(Long id) {
        Periodical periodical = periodicalRepository.findById(id).orElseThrow(() -> {
            throw new PeriodicalNotFoundException("Periodical with id  " + id + " could not be found.");
        });

        return mapper.toDto(periodical);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public PeriodicalDto save(PeriodicalDto dto) {
        if(periodicalRepository.findByTitle(dto.getTitle()) != null) {
            throw new PeriodicalAlreadyExistsException("Periodical with title " + dto.getTitle() +  " already exists.");
        }
        return mapper.toDto(periodicalRepository.save(mapper.toEntity(dto)));
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public PeriodicalDto update(PeriodicalDto dto) {
        Periodical existingPeriodical = periodicalRepository.findByTitle(dto.getTitle());

        if(existingPeriodical != null && !existingPeriodical.getId().equals(dto.getId())) {
            throw new PeriodicalAlreadyExistsException("Periodical with title " + dto.getTitle() +  " already exists.");
        }
        return mapper.toDto(periodicalRepository.save(mapper.toEntity(dto)));
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public void deleteById(Long id) {
        PeriodicalDto periodicalDto = findById(id);
        if(subscriptionDetailService.checkIfSubscriptionExistsByPeriodical(periodicalDto)) {
            throw new PeriodicalDeleteException("Periodical ordered in subscription can't be deleted.");
        }
        periodicalRepository.deleteById(id);

        if(periodicalRepository.existsById(id)) {
            throw new PeriodicalServiceException("Error while deleting periodical with id " + id + ".");
        }
    }

    @Override
    @LogInvocationService
    public PeriodicalDto processPeriodicalCreation(PeriodicalDto periodicalDto, MultipartFile imageFile) {
        periodicalDto.setStatusDto(PeriodicalDto.StatusDto.AVAILABLE);
        periodicalDto.setImagePath(getImagePath(imageFile));
        return save(periodicalDto);
    }

    @Override
    @LogInvocationService
    @Transactional
    public PeriodicalDto processPeriodicalUpdate(PeriodicalDto periodicalDto, MultipartFile imageFile) {
        periodicalDto.setStatusDto(PeriodicalDto.StatusDto.AVAILABLE);

        if(!imageFile.isEmpty()) {
            periodicalDto.setImagePath(getImagePath(imageFile));
        }

        periodicalCategoryService.deleteAllCategoriesForPeriodical(periodicalDto);
        return update(periodicalDto);
    }

    @LogInvocationService
    @ImageUploadEx
    private String getImagePath(MultipartFile imageFile) {
        String imageName = "";
        try {
            imageName = imageFile.getOriginalFilename();
            String location = "periodicals/";
            File partFile = new File(location + imageName);
            imageFile.transferTo(partFile);
        } catch (IOException e) {
            throw new ImageUploadException("Error while uploading image " + imageName + ".", e);
        }
        return imageName;
    }
}
