package org.example.expert.domain.todo.dto.response;

import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserFindResponse;

@Getter
public class TodoSaveResponse {

    private final Long id;

    private final String title;

    private final String contents;

    private final String weather;

    private final UserFindResponse user;

    private TodoSaveResponse(Long id, String title, String contents, String weather, UserFindResponse user) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.weather = weather;
        this.user = user;
    }

    public static TodoSaveResponse of(Long id, String title, String contents, String weather, UserFindResponse user) {
        return new TodoSaveResponse(id, title, contents, weather, user);
    }
}
