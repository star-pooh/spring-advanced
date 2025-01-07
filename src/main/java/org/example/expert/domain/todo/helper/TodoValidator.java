package org.example.expert.domain.todo.helper;

import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.util.ObjectUtils;

public class TodoValidator {

    public static void verifyTodoUser(Todo todo, long userId) {
        if (todo.getUser() == null || !ObjectUtils.nullSafeEquals(todo.getUser().getId(), userId)) {
            throw new InvalidRequestException("해당 일정을 만든 유저가 유효하지 않습니다.");
        }
    }
}
