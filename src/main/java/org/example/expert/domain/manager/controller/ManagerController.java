package org.example.expert.domain.manager.controller;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.config.JwtUtil;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest;
import org.example.expert.domain.manager.dto.response.ManagerFindResponse;
import org.example.expert.domain.manager.dto.response.ManagerSaveResponse;
import org.example.expert.domain.manager.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos/{todoId}/managers")
public class ManagerController {

    private final ManagerService managerService;
    private final JwtUtil jwtUtil;

    /**
     * 관리자 생성 API
     *
     * @param authUser           로그인한 유저 정보
     * @param todoId             일정 ID
     * @param managerSaveRequest 관리자 생성에 필요한 요청 데이터
     * @return 생성된 관리자 정보
     */
    @PostMapping
    public ResponseEntity<ManagerSaveResponse> saveManager(
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @Valid @RequestBody ManagerSaveRequest managerSaveRequest) {
        return ResponseEntity.ok(managerService.saveManager(authUser, todoId, managerSaveRequest));
    }

    /**
     * 관리자 조회 API
     *
     * @param todoId 일정 ID
     * @return 조회된 관리자 정보
     */
    @GetMapping
    public ResponseEntity<List<ManagerFindResponse>> findManagerByTodoId(@PathVariable long todoId) {
        return ResponseEntity.ok(managerService.findManagerByTodoId(todoId));
    }

    /**
     * 관리자 삭제 API
     *
     * @param bearerToken 토큰
     * @param todoId      일정 ID
     * @param managerId   관리자 ID
     */
    @DeleteMapping("/{managerId}")
    public void deleteManager(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable long todoId,
            @PathVariable long managerId) {
        Claims claims = jwtUtil.extractClaims(jwtUtil.extractToken(bearerToken));
        long userId = Long.parseLong(claims.getSubject());
        managerService.deleteManager(userId, todoId, managerId);
    }
}
