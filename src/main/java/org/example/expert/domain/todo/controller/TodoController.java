package org.example.expert.domain.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.TodoFindResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    /**
     * 일정 생성 API
     *
     * @param authUser        로그인한 유저 정보
     * @param todoSaveRequest 일정 생성에 필요한 요청 데이터
     * @return 생성된 일정 정보
     */
    @PostMapping
    public ResponseEntity<TodoSaveResponse> saveTodo(
            @Auth AuthUser authUser,
            @Valid @RequestBody TodoSaveRequest todoSaveRequest) {
        return ResponseEntity.ok(todoService.saveTodo(authUser, todoSaveRequest));
    }

    /**
     * 전체 일정 조회 API
     *
     * @param pageNum  페이지 번호
     * @param pageSize 페이지 크기
     * @return 조회된 일정 정보
     */
    @GetMapping
    public ResponseEntity<Page<TodoFindResponse>> findAllTodo(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(todoService.findAllTodo(pageNum, pageSize));
    }

    /**
     * 특정 일정 조회 API
     *
     * @param todoId 일정 ID
     * @return 조회된 일정 정보
     */
    @GetMapping("/{todoId}")
    public ResponseEntity<TodoFindResponse> findTodoById(@PathVariable long todoId) {
        return ResponseEntity.ok(todoService.findTodoById(todoId));
    }
}
