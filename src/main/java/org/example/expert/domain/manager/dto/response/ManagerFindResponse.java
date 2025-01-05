package org.example.expert.domain.manager.dto.response;

import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserFindResponse;

@Getter
public class ManagerFindResponse {

    private final Long id;

    private final UserFindResponse user;

    public ManagerFindResponse(Long id, UserFindResponse user) {
        this.id = id;
        this.user = user;
    }
}
