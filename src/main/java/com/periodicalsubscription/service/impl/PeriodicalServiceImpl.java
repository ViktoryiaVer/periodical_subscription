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
import com.periodicalsubscription.model.specification.PeriodicalSpecifications;
import com.periodicalsubscription.service.api.PeriodicalCategoryService;
import com.periodicalsubscription.service.api.PeriodicalService;
import com.periodicalsubscription.service.dto.PeriodicalDto;
import com.periodicalsubscription.service.api.SubscriptionDetailService;
import com.periodicalsubscription.service.dto.filter.PeriodicalFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PeriodicalServiceImpl implements PeriodicalService {
    private final PeriodicalRepository periodicalRepository;
    private final PeriodicalCategoryService periodicalCategoryService;
    private final SubscriptionDetailService subscriptionDetailService;
    private final PeriodicalMapper mapper;
    private final MessageSource messageSource;

    @Override
    @LogInvocationService
    public Page<PeriodicalDto> findAll(Pageable pageable) {
        return periodicalRepository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public PeriodicalDto findById(Long id) {
        Periodical periodical = periodicalRepository.findById(id).orElseThrow(() -> {
            throw new PeriodicalNotFoundException(messageSource.getMessage("msg.error.periodical.find.by.id", null,
                    LocaleContextHolder.getLocale()));
        });

        return mapper.toDto(periodical);
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public PeriodicalDto save(PeriodicalDto dto) {
        if (periodicalRepository.findByTitle(dto.getTitle()) != null) {
            throw new PeriodicalAlreadyExistsException(messageSource.getMessage("msg.error.periodical.title.exists", null,
                    LocaleContextHolder.getLocale()));
        }
        return mapper.toDto(periodicalRepository.save(mapper.toEntity(dto)));
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public PeriodicalDto update(PeriodicalDto dto) {
        Periodical existingPeriodical = periodicalRepository.findByTitle(dto.getTitle());

        if (existingPeriodical != null && !existingPeriodical.getId().equals(dto.getId())) {
            throw new PeriodicalAlreadyExistsException(messageSource.getMessage("msg.error.periodical.title.exists", null,
                    LocaleContextHolder.getLocale()));
        }
        return mapper.toDto(periodicalRepository.save(mapper.toEntity(dto)));
    }

    @Override
    @LogInvocationService
    @ServiceEx
    public void deleteById(Long id) {
        PeriodicalDto periodicalDto = findById(id);
        if (subscriptionDetailService.checkIfSubscriptionExistsByPeriodical(periodicalDto)) {
            throw new PeriodicalDeleteException(messageSource.getMessage("msg.error.periodical.delete.subscription", null,
                    LocaleContextHolder.getLocale()));
        }
        periodicalRepository.deleteById(id);

        if (periodicalRepository.existsById(id)) {
            throw new PeriodicalServiceException(messageSource.getMessage("msg.error.periodical.service.delete", null,
                    LocaleContextHolder.getLocale()));
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

        if (!imageFile.isEmpty()) {
            periodicalDto.setImagePath(getImagePath(imageFile));
        }

        periodicalCategoryService.deleteAllCategoriesForPeriodical(periodicalDto);
        return update(periodicalDto);
    }

    @Override
    @LogInvocationService
    public Page<PeriodicalDto> filterPeriodical(PeriodicalFilterDto filterDto, Pageable pageable) {
        Specification<Periodical> specification = Specification
                .where(filterDto.getCategory() == null  || filterDto.getCategory().isEmpty() ? null : PeriodicalSpecifications.hasCategory(filterDto.getCategory()))
                .and(filterDto.getType() == null || filterDto.getType().isEmpty()  ? null : PeriodicalSpecifications.hastType(filterDto.getType()));
        return periodicalRepository.findAll(specification, pageable).map(mapper::toDto);
    }

    @Override
    @LogInvocationService
    public Page<PeriodicalDto> searchForPeriodicalByKeyword(String keyword, Pageable pageable) {
        Specification<Periodical> specification = PeriodicalSpecifications.hasIdLike(keyword)
                .or(PeriodicalSpecifications.hasTitleLike(keyword))
                .or(PeriodicalSpecifications.hasPublisherLike(keyword))
                .or(PeriodicalSpecifications.hasDescriptionLike(keyword))
                .or(PeriodicalSpecifications.hasLanguageLike(keyword))
                .or(PeriodicalSpecifications.hasPriceLike(keyword));
        return periodicalRepository.findAll(specification, pageable).map(mapper::toDto);
    }

    @LogInvocationService
    @ImageUploadEx
    private String getImagePath(MultipartFile imageFile) {
        String imageName;
        try {
            imageName = imageFile.getOriginalFilename();
            String location = "periodicals/";
            File partFile = new File(location + imageName);
            imageFile.transferTo(partFile);
        } catch (IOException e) {
            throw new ImageUploadException(messageSource.getMessage("msg.error.image.upload", null,
                    LocaleContextHolder.getLocale()));
        }
        return imageName;
    }
}
