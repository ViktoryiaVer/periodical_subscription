package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.PeriodicalDto;
import com.periodicalsubscription.service.dto.filter.PeriodicalFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PeriodicalService {
    Page<PeriodicalDto> findAll(Pageable pageable);

    PeriodicalDto findById(Long id);

    PeriodicalDto save(PeriodicalDto dto);

    PeriodicalDto update(PeriodicalDto dto);

    void deleteById(Long id);

    PeriodicalDto processPeriodicalCreation(PeriodicalDto periodicalDto, MultipartFile imageFile);

    PeriodicalDto processPeriodicalUpdate(PeriodicalDto periodicalDto, MultipartFile imageFile);

    Page<PeriodicalDto> filterPeriodical(PeriodicalFilterDto filterDto, Pageable pageable);

    Page<PeriodicalDto> searchForPeriodicalByKeyword(String keyword, Pageable pageable);
}
