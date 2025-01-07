package org.example.expert.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.client.WeatherClient;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.common.util.ResponseMapper;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.TodoFindResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserFindResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final WeatherClient weatherClient;

    /**
     * 일정 생성
     *
     * @param authUser        로그인한 유저 정보
     * @param todoSaveRequest 일정 생성에 필요한 요청 데이터
     * @return 생성된 일정 정보
     */
    @Transactional
    public TodoSaveResponse saveTodo(AuthUser authUser, TodoSaveRequest todoSaveRequest) {
        User user = User.fromAuthUser(authUser);

        String weather = weatherClient.getTodayWeather();

        Todo newTodo = Todo.of(
                todoSaveRequest.getTitle(),
                todoSaveRequest.getContents(),
                weather,
                user
        );
        Todo savedTodo = todoRepository.save(newTodo);

        return TodoSaveResponse.of(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getContents(),
                weather,
                UserFindResponse.of(user.getId(), user.getEmail())
        );
    }

    /**
     * 전체 일정 조회
     *
     * @param pageNum  페이지 번호
     * @param pageSize 페이지 크기
     * @return 조회된 일정 정보
     */
    public Page<TodoFindResponse> findAllTodo(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);

        Page<Todo> todos = todoRepository.findAllByOrderByModifiedAtDesc(pageable);

        return ResponseMapper.mapToPage(todos, TodoFindResponse::toTodoFindResponse);
    }

    /**
     * 특정 일정 조회
     *
     * @param todoId 일정 ID
     * @return 조회된 일정 정보
     */
    public TodoFindResponse findTodoById(long todoId) {
        Todo todo = getTodoById(todoId);

        return TodoFindResponse.toTodoFindResponse(todo);
    }

    @Transactional(readOnly = true)
    public Todo getTodoById(long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new InvalidRequestException("Todo not found"));
    }
}
