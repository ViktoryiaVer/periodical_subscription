package com.periodicalsubscription.service.api;

import com.periodicalsubscription.dto.PeriodicalDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PeriodicalService {
    Page<PeriodicalDto> findAll(Pageable pageable);

    PeriodicalDto findById(Long id);

    PeriodicalDto save(PeriodicalDto dto);

    PeriodicalDto update (PeriodicalDto dto);

    void deleteById(Long id);

    PeriodicalDto processPeriodicalCreation(PeriodicalDto periodicalDto, MultipartFile imageFile);

    PeriodicalDto processPeriodicalUpdate(PeriodicalDto periodicalDto, MultipartFile imageFile);

}
