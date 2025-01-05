package org.example.expert.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserFindResponse {

    private final Long id;

    private final String email;

    public UserFindResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
