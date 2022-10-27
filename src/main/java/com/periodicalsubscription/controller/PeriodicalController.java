package com.periodicalsubscription.controller;

import com.periodicalsubscription.dto.PeriodicalDto;
import com.periodicalsubscription.manager.PageManager;
import com.periodicalsubscription.service.api.PeriodicalCategoryService;
import com.periodicalsubscription.service.api.PeriodicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/periodical")
public class PeriodicalController {
    private final PeriodicalService periodicalService;
    private final PeriodicalCategoryService periodicalCategoryService;

    @GetMapping(value = "/all")
    public String getAllPeriodicals(Model model) {
        List<PeriodicalDto> periodicals = periodicalService.findAll();

        if(periodicals.isEmpty()) {
            model.addAttribute("message", "No periodicals could be found");
            return PageManager.PERIODICALS;
        }
        model.addAttribute("periodicals", periodicals);

        return PageManager.PERIODICALS;
    }

    @GetMapping("/{id}")
    public String getPeriodical(Model model, @PathVariable Long id) {
        PeriodicalDto periodical = periodicalService.findById(id);

        model.addAttribute("periodical", periodical);

        return PageManager.PERIODICAL;
    }

    @GetMapping("/create")
    public String createPeriodicalForm() {
        return PageManager.CREATE_PERIODICAL;
    }

    @PostMapping("/create")
    public String createPeriodical(@ModelAttribute PeriodicalDto periodical, MultipartFile imageFile, Model model) {
        periodical.setStatusDto(PeriodicalDto.StatusDto.AVAILABLE);
        periodical.setImagePath(getImagePath(imageFile));
        periodicalService.save(periodical);
        model.addAttribute("message", "Periodical was created successfully");
        return PageManager.HOME;
    }

    @GetMapping("/update/{id}")
    public String updatePeriodicalForm(@PathVariable Long id, Model model) {
        PeriodicalDto periodical = periodicalService.findById(id);
        model.addAttribute("periodical", periodical);
        return PageManager.UPDATE_PERIODICAL;
    }

    @PostMapping("/update")
    public String updatePeriodical(@ModelAttribute PeriodicalDto periodical, MultipartFile imageFile, Model model) {
        periodical.setStatusDto(PeriodicalDto.StatusDto.AVAILABLE);

        if(!imageFile.isEmpty()) {
            periodical.setImagePath(getImagePath(imageFile));
        }
        periodicalCategoryService.deleteAllCategoriesForPeriodical(periodical);
        periodicalService.update(periodical);
        model.addAttribute("message", "Periodical was updated successfully");
        return PageManager.HOME;
    }

    @PostMapping("/delete/{id}")
    public String deletePeriodical(@PathVariable Long id, Model model) {
        periodicalService.delete(id);
        model.addAttribute("message", "Periodical was deleted successfully");
        return PageManager.HOME;
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
