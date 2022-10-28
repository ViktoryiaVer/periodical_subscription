package com.periodicalsubscription.service.api;

import com.periodicalsubscription.dto.PeriodicalDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PeriodicalService {
    List<PeriodicalDto> findAll();

    PeriodicalDto findById(Long id);

    PeriodicalDto save(PeriodicalDto dto);

    PeriodicalDto update (PeriodicalDto dto);

    void delete(Long id);

    PeriodicalDto processPeriodicalCreation(PeriodicalDto periodicalDto, MultipartFile imageFile);

    PeriodicalDto processPeriodicalUpdate(PeriodicalDto periodicalDto, MultipartFile imageFile);

}
