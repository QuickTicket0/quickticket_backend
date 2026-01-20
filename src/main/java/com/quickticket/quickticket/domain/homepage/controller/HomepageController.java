package com.quickticket.quickticket.domain.homepage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HomepageController {
    @GetMapping("/")
    public String homepage(Model model) {
        if (true) {
            return "index";
        } else {
            return "admin/index";
        }
    }

    @GetMapping("/search/{query}")
    public String search(Model model, @PathVariable String query) {
        return "search";
    }
}
