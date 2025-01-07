package org.example.expert.domain.comment.service;

import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.dto.response.CommentSaveResponse;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.comment.repository.CommentRepository;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.service.TodoService;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private TodoService todoService;
    @InjectMocks
    private CommentService commentService;

    // 비즈니스 로직이 변경되어 필요없는 테스트이나 참고를 위해 남겨놓음
//    @Test
//    public void comment_등록_중_할일을_찾지_못해_에러가_발생한다() {
//        // given
//        long todoId = 1;
//        CommentSaveRequest request = new CommentSaveRequest("contents");
//        AuthUser authUser = new AuthUser(1L, "email", UserRole.USER);
//
//        given(todoRepository.findById(anyLong())).willReturn(Optional.empty());
//
//        // when
//        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
//            commentService.saveComment(authUser, todoId, request);
//        });
//
//        // then
//        assertEquals("Todo not found", exception.getMessage());
//    }

    @Test
    public void comment를_정상적으로_등록한다() {
        // given
        long todoId = 1;
        CommentSaveRequest request = new CommentSaveRequest("contents");
        AuthUser authUser = new AuthUser(1L, "email", UserRole.USER);
        User user = User.fromAuthUser(authUser);
        Todo todo = Todo.of("title", "title", "contents", user);
        Comment comment = Comment.of(request.getContents(), user, todo);

        given(todoService.getTodoById(anyLong())).willReturn(todo);
        given(commentRepository.save(any())).willReturn(comment);

        // when
        CommentSaveResponse result = commentService.saveComment(authUser, todoId, request);

        // then
        assertNotNull(result);
    }
}
