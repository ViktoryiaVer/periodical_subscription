package com.periodicalsubscription.service.api;

import com.periodicalsubscription.service.dto.PeriodicalDto;
import com.periodicalsubscription.service.dto.filter.PeriodicalFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * interface with methods for periodical business logic
 */
public interface PeriodicalService {

    /**
     * finds all periodicals
     * @param pageable Pageable object for result pagination
     * @return page of PeriodicalDto object
     */
    Page<PeriodicalDto> findAll(Pageable pageable);

    /**
     * finds a periodical by id
     * @param id id of the periodical
     * @return found periodical
     */
    PeriodicalDto findById(Long id);

    /**
     * saves a periodical
     * @param dto PeriodicalDto object to be saved
     * @return saved PeriodicalDto object
     */
    PeriodicalDto save(PeriodicalDto dto);

    /**
     * updates a periodical
     * @param dto PeriodicalDto object to be updated
     * @return updated PeriodicalDto object
     */
    PeriodicalDto update(PeriodicalDto dto);

    /**
     * deletes a periodical by id
     * @param id id of the periodical
     */
    void deleteById(Long id);

    /**
     * processes periodical creation
     * @param periodicalDto periodicalDto object to be saved
     * @param imageFile Multipart object to be set as periodical image
     * @return saved PeriodicalDto object
     */
    PeriodicalDto processPeriodicalCreation(PeriodicalDto periodicalDto, MultipartFile imageFile);

    /**
     * processes periodical update
     * @param periodicalDto periodicalDto object to be updated
     * @param imageFile Multipart object to be set as the new periodical image
     * @return updated PeriodicalDto object
     */
    PeriodicalDto processPeriodicalUpdate(PeriodicalDto periodicalDto, MultipartFile imageFile);

    /**
     * filters periodical
     * @param filterDto filter object for periodical filtering
     * @param pageable Pageable object for result pagination
     * @return page of PeriodicalDto object
     */
    Page<PeriodicalDto> filterPeriodical(PeriodicalFilterDto filterDto, Pageable pageable);

    /**
     * searches for a periodical by a keyword
     * @param keyword word to be searched by
     * @param pageable Pageable object for result pagination
     * @return page of PeriodicalDto object
     */
    Page<PeriodicalDto> searchForPeriodicalByKeyword(String keyword, Pageable pageable);

    /**
     * updates Periodical status
     * @param status status to be set
     * @param id id of the periodical
     * @return updated periodical
     */
    PeriodicalDto updatePeriodicalStatus(PeriodicalDto.StatusDto status, Long id);

    /**
     * checks if periodical has status "Unavailable"
     * if periodical is unavailable, throws an PeriodicalUnavailableException
     * @param id id of the periodical
     */
    void checkIfPeriodicalIsUnavailable(Long id);
}
