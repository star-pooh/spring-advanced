package org.example.expert.domain.manager.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.common.util.ResponseMapper;
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest;
import org.example.expert.domain.manager.dto.response.ManagerFindResponse;
import org.example.expert.domain.manager.dto.response.ManagerSaveResponse;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.manager.repository.ManagerRepository;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.helper.TodoValidator;
import org.example.expert.domain.todo.service.TodoService;
import org.example.expert.domain.user.dto.response.UserFindResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final UserService userService;
    private final TodoService todoService;

    /**
     * 관리자 생성
     *
     * @param authUser           로그인한 유저 정보
     * @param todoId             일정 ID
     * @param managerSaveRequest 관리자 생성에 필요한 요청 데이터
     * @return 생성된 관리 정보
     */
    @Transactional
    public ManagerSaveResponse saveManager(AuthUser authUser, long todoId, ManagerSaveRequest managerSaveRequest) {
        if (ObjectUtils.nullSafeEquals(authUser.getId(), managerSaveRequest.getManagerUserId())) {
            throw new InvalidRequestException("일정 작성자는 본인을 담당자로 등록할 수 없습니다.");
        }

        // 일정을 만든 유저
        Todo todo = todoService.getTodoById(todoId);

        TodoValidator.verifyTodoUser(todo, authUser.getId());

        User managerUser = userService.getUserById(managerSaveRequest.getManagerUserId());

        Manager newManagerUser = new Manager(managerUser, todo);
        Manager savedManagerUser = managerRepository.save(newManagerUser);

        return ManagerSaveResponse.of(
                savedManagerUser.getId(),
                UserFindResponse.of(managerUser.getId(), managerUser.getEmail())
        );
    }

    /**
     * 관리자 조회
     *
     * @param todoId 일정 ID
     * @return 조회된 관리자 정보
     */
    public List<ManagerFindResponse> findManagerByTodoId(long todoId) {
        Todo todo = todoService.getTodoById(todoId);

        List<Manager> managerList = managerRepository.findAllByTodoId(todo.getId());

        return ResponseMapper.mapToList(managerList, ManagerFindResponse::toManagerFindResponse);
    }

    /**
     * 관리자 삭제
     *
     * @param userId    유저 ID
     * @param todoId    일정 ID
     * @param managerId 관리자 ID
     */
    @Transactional
    public void deleteManager(long userId, long todoId, long managerId) {
        User user = userService.getUserById(userId);
        Todo todo = todoService.getTodoById(todoId);

        TodoValidator.verifyTodoUser(todo, user.getId());

        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new InvalidRequestException("Manager not found"));

        if (!ObjectUtils.nullSafeEquals(todo.getId(), manager.getTodo().getId())) {
            throw new InvalidRequestException("해당 일정에 등록된 담당자가 아닙니다.");
        }

        managerRepository.delete(manager);
    }
}
