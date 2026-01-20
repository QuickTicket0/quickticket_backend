package com.quickticket.quickticket.domain.account.controller;

import com.quickticket.quickticket.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {
    @GetMapping("/login")
    public String login(Model model) {
        return "account/login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        return "account/signup";
    }

    @GetMapping("/findMyId")
    public String findMyId(Model model) {
        return "account/findMyId";
    }

    @GetMapping("/findMyPassword")
    public String findMyPassword(Model model) {
        return "account/findMyPassword";
    }
}
