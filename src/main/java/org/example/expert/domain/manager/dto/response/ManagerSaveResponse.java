package org.example.expert.domain.manager.dto.response;

import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserFindResponse;

@Getter
public class ManagerSaveResponse {

    private final Long id;

    private final UserFindResponse user;

    private ManagerSaveResponse(Long id, UserFindResponse user) {
        this.id = id;
        this.user = user;
    }

    public static ManagerSaveResponse of(Long id, UserFindResponse user) {
        return new ManagerSaveResponse(id, user);
    }
}
