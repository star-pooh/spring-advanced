package org.example.expert.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.auth.dto.request.SigninRequest;
import org.example.expert.domain.auth.dto.request.SignupRequest;
import org.example.expert.domain.auth.dto.response.SigninResponse;
import org.example.expert.domain.auth.dto.response.SignupResponse;
import org.example.expert.domain.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 회원 가입 API
     *
     * @param signupRequest 회원 가입에 필요한 요청 데이터
     * @return 토큰
     */
    @PostMapping("/signup")
    public SignupResponse signup(@Valid @RequestBody SignupRequest signupRequest) {
        return authService.signup(signupRequest);
    }

    /**
     * 로그인 API
     *
     * @param signinRequest 로그인에 필요한 요청 데이터
     * @return 토큰
     */
    @PostMapping("/signin")
    public SigninResponse signin(@Valid @RequestBody SigninRequest signinRequest) {
        return authService.signin(signinRequest);
    }
}
