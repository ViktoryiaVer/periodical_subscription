package com.periodicalsubscription.controller;

import com.periodicalsubscription.constant.PageConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @GetMapping(value = {"/", "/home"})
    public String homepage() {
        return PageConstant.HOME;
    }
}
