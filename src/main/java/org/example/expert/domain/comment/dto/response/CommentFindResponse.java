package org.example.expert.domain.comment.dto.response;

import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserFindResponse;

@Getter
public class CommentFindResponse {

    private final Long id;

    private final String contents;

    private final UserFindResponse user;

    public CommentFindResponse(Long id, String contents, UserFindResponse user) {
        this.id = id;
        this.contents = contents;
        this.user = user;
    }
}
