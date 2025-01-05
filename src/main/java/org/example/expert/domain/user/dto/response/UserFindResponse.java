package org.example.expert.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserFindResponse {

    private final Long id;

    private final String email;

    private UserFindResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public static UserFindResponse of(Long id, String email) {
        return new UserFindResponse(id, email);
    }
}
