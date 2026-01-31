package com.quickticket.quickticket.domain.account.controller;

import com.quickticket.quickticket.domain.account.dto.AccountRequest;
import com.quickticket.quickticket.domain.user.domain.User;
import com.quickticket.quickticket.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // 로그인 및 로그아웃 처리는 SecurityConfig의 설정을 바탕으로 자동으로 이뤄집니다

    @GetMapping("/signup")
    public String signupPage(Model model) {
        return "account/signup";
    }

    @PostMapping("/api/account/signup")
    public void signup(
            @ModelAttribute AccountRequest.Signup requestDto
    ) {
        var newUser = userService.signupNewUser(requestDto);

        var authentication = new UsernamePasswordAuthenticationToken(
                newUser,
                null,
                newUser.getAuthorities()
        );

        // 수동으로 로그인 상태 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);
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
