package com.quickticket.quickticket.domain.homepage.controller;

import com.quickticket.quickticket.domain.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HomepageController {
    private final EventService eventService;
    
    @GetMapping("/")
    public String homepage(Model model) {
        var hotEvents = eventService.getHotEventsTopN(5);
        var closingSoonEvents = eventService.getClosingSoonEvents(5);

        
        if (true) {
            model.addAttribute("hotEvents", hotEvents);
            model.addAttribute("closingSoon", closingSoonEvents);

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
