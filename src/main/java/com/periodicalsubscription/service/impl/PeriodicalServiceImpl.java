package com.periodicalsubscription.service.impl;

import com.periodicalsubscription.model.repository.PeriodicalRepository;
import com.periodicalsubscription.model.entity.Periodical;
import com.periodicalsubscription.service.api.PeriodicalService;
import com.periodicalsubscription.dto.PeriodicalDto;
import com.periodicalsubscription.mapper.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PeriodicalServiceImpl implements PeriodicalService {
    private final PeriodicalRepository periodicalRepository;
    private final ObjectMapper mapper;

    @Override
    public List<PeriodicalDto> findAll() {
        return periodicalRepository.findAll().stream()
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
    public void delete(PeriodicalDto dto) {
        //TODO some validation?
        periodicalRepository.delete(mapper.toEntity(dto));
    }
}
