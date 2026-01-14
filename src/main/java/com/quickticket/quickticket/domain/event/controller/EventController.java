package com.quickticket.quickticket.domain.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class EventController {
    @GetMapping("/event")
    public String event(Model model) {
        return "event";
    }

    @GetMapping("/admin/event")
    public String adminEvent(Model model) {
        return "admin/event";
    }

    @GetMapping("/editEvent/{eventId}")
    public String editEvent(Model model, @PathVariable String eventId) {
        return "admin/editEvent";
    }

    @GetMapping("/newEvent")
    public String newEvent(Model model) {
        return "admin/newEvent";
    }
}
