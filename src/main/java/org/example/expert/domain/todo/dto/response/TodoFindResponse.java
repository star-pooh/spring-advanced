package org.example.expert.domain.todo.dto.response;

import lombok.Getter;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.dto.response.UserFindResponse;

import java.time.LocalDateTime;

@Getter
public class TodoFindResponse {

    private final Long id;

    private final String title;

    private final String contents;

    private final String weather;

    private final UserFindResponse user;

    private final LocalDateTime createdAt;

    private final LocalDateTime modifiedAt;

    private TodoFindResponse(
            Long id,
            String title,
            String contents,
            String weather,
            UserFindResponse user,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.weather = weather;
        this.user = user;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static TodoFindResponse toTodoFindResponse(Todo todo) {
        return new TodoFindResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserFindResponse(
                        todo.getUser().getId(),
                        todo.getUser().getEmail()
                ),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }
}
