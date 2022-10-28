package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.mapper.PeriodicalMapper;
import com.periodicalsubscription.model.repository.PeriodicalRepository;
import com.periodicalsubscription.model.entity.Periodical;
import com.periodicalsubscription.service.api.PeriodicalCategoryService;
import com.periodicalsubscription.service.api.PeriodicalService;
import com.periodicalsubscription.dto.PeriodicalDto;
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
    private final PeriodicalMapper mapper;

    @Override
    public List<PeriodicalDto> findAll() {
        return periodicalRepository.findAllDistinctFetchCategories().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PeriodicalDto findById(Long id) {
        Periodical periodical = periodicalRepository.findById(id).orElseThrow(RuntimeException::new);

        return mapper.toDto(periodical);
    }

    @Override
    public PeriodicalDto save(PeriodicalDto dto) {
        //TODO some validation
        return mapper.toDto(periodicalRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public PeriodicalDto update(PeriodicalDto dto) {
        //TODO some validation
        return mapper.toDto(periodicalRepository.save(mapper.toEntity(dto)));
    }

    @Override
    public void delete(Long id) {
        //TODO some validation?
        periodicalRepository.deleteById(id);
    }

    @Override
    public PeriodicalDto processPeriodicalCreation(PeriodicalDto periodicalDto, MultipartFile imageFile) {
        periodicalDto.setStatusDto(PeriodicalDto.StatusDto.AVAILABLE);
        periodicalDto.setImagePath(getImagePath(imageFile));
        return save(periodicalDto);
    }

    @Transactional
    @Override
    public PeriodicalDto processPeriodicalUpdate(PeriodicalDto periodicalDto, MultipartFile imageFile) {
        periodicalDto.setStatusDto(PeriodicalDto.StatusDto.AVAILABLE);

        if(!imageFile.isEmpty()) {
            periodicalDto.setImagePath(getImagePath(imageFile));
        }
        periodicalCategoryService.deleteAllCategoriesForPeriodical(periodicalDto);
        return update(periodicalDto);
    }

    private String getImagePath(MultipartFile imageFile) {
        String imageName;
        try {
            imageName = imageFile.getOriginalFilename();
            String location = "periodicals/";
            File partFile = new File(location + imageName);
            imageFile.transferTo(partFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageName;
    }

}
