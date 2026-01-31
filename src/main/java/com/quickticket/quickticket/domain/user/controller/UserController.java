package com.quickticket.quickticket.domain.user.controller;

import com.quickticket.quickticket.domain.user.domain.User;
import com.quickticket.quickticket.domain.user.dto.UserResponse;
import com.quickticket.quickticket.domain.user.mapper.UserMapper;
import com.quickticket.quickticket.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/myPage/userInfo")
    public String myPage(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        var userEntity = userMapper.toEntity(user);
        var details = UserResponse.Details.from(userEntity);
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
        */

        model.addAttribute(details);

        return "myPage/userInfo";
    }
}
