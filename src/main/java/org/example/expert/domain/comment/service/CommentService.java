package org.example.expert.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.dto.response.CommentFindResponse;
import org.example.expert.domain.comment.dto.response.CommentSaveResponse;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.comment.repository.CommentRepository;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.common.util.ResponseMapper;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserFindResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 생성
     *
     * @param authUser           로그인한 유저 정보
     * @param todoId             일정 ID
     * @param commentSaveRequest 댓글 생성에 필요한 요청 데이터
     * @return 생성된 댓글 정보
     */
    @Transactional
    public CommentSaveResponse saveComment(AuthUser authUser, long todoId, CommentSaveRequest commentSaveRequest) {
        User user = User.fromAuthUser(authUser);
        Todo todo = todoRepository.findById(todoId).orElseThrow(() ->
                new InvalidRequestException("Todo not found"));

        Comment newComment = Comment.of(commentSaveRequest.getContents(), user, todo);

        Comment savedComment = commentRepository.save(newComment);

        return CommentSaveResponse.of(
                savedComment.getId(),
                savedComment.getContents(),
                new UserFindResponse(user.getId(), user.getEmail())
        );
    }

    /**
     * 댓글 조회
     *
     * @param todoId 일정 ID
     * @return 조회된 댓글 정보
     */
    public List<CommentFindResponse> findAllComment(long todoId) {
        List<Comment> commentList = commentRepository.findAllByTodoId(todoId);

        return ResponseMapper.mapToList(commentList, CommentFindResponse::of);
    }
}
