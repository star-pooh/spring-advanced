package org.example.expert.domain.comment.dto.response;

import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserFindResponse;

@Getter
public class CommentSaveResponse {

    private final Long id;

    private final String contents;

    private final UserFindResponse user;

    private CommentSaveResponse(Long id, String contents, UserFindResponse user) {
        this.id = id;
        this.contents = contents;
        this.user = user;
    }

    public static CommentSaveResponse of(Long id, String contents, UserFindResponse user) {
        return new CommentSaveResponse(id, contents, user);
    }
}
