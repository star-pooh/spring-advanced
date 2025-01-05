package org.example.expert.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.dto.request.UserChangeRoleRequest;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;

    /**
     * 유저 권한 변경
     *
     * @param userId                유저 ID
     * @param userChangeRoleRequest 유저 권한 변경에 필요한 요청 데이터
     */
    @Transactional
    public void changeUserRole(long userId, UserChangeRoleRequest userChangeRoleRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidRequestException("User not found"));
        user.updateRole(UserRole.of(userChangeRoleRequest.getRole()));
    }
}
