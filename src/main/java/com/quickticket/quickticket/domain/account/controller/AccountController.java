package com.quickticket.quickticket.domain.account.controller;

import com.quickticket.quickticket.domain.account.dto.AccountRequest;
import com.quickticket.quickticket.domain.user.domain.User;
import com.quickticket.quickticket.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "account/login";
    }

    @PostMapping("/api/account/login")
    public String login(@ModelAttribute AccountRequest.Login requestDto) {
        var user = userService.findUserByUsername(requestDto.username());


    }

    @PostMapping("/api/account/logout")
    public String logout() {

    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        return "account/signup";
    }

    @PostMapping("/api/account/signup")
    public String signup(@ModelAttribute AccountRequest.Signup requestDto) {
        var newUser = userService.signupNewUser(requestDto);
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
