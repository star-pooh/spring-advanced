package org.example.expert.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserFindResponse;
import org.example.expert.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 유저 조회
     *
     * @param userId 유저 ID
     * @return 조회된 유저 정보
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserFindResponse> findUserById(@PathVariable long userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    /**
     * 비밀번호 변경
     *
     * @param authUser                  로그인한 유저 정보
     * @param userChangePasswordRequest 비밀번호 변경에 필요한 요청 데이터
     */
    @PutMapping
    public void changePassword(@Auth AuthUser authUser, @Valid @RequestBody UserChangePasswordRequest userChangePasswordRequest) {
        userService.changePassword(authUser.getId(), userChangePasswordRequest);
    }
}
