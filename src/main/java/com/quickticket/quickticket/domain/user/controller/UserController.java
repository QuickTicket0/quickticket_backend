package com.quickticket.quickticket.domain.user.controller;

import com.quickticket.quickticket.domain.user.domain.UserRole;
import com.quickticket.quickticket.domain.user.dto.UserResponse;
import com.quickticket.quickticket.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/myPage/userInfo")
    public String myPage(Model model) {
        // [유저정보]
        /*
        UserResponse.Details user = UserResponse.Details.builder()
                .id(1L)
                .username("sjg04142")
                .realname("조민지")
                .birthday(LocalDate.of(1995, 8, 20))
                .email("test@quickticket.com")
                .phone("010-1234-5678")
                .address("서울시 마포구 양화로 123 401호")
                .credit(3000L)
                .role(UserRole.USER)
                .createdAt(LocalDateTime.now())
                .build();

        model.addAttribute("user", user);
        */
        return "myPage/userInfo";
    }
}
