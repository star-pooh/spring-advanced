package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.dto.request.UserChangeRoleRequest;
import org.example.expert.domain.user.service.UserAdminService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAdminController {

    private final UserAdminService userAdminService;

    /**
     * 유저 권한 변경
     *
     * @param userId                유저 ID
     * @param userChangeRoleRequest 유저 권한 변경에 필요한 요청 데이터
     */
    @PatchMapping("/admin/users/{userId}")
    // TODO : requestBody에 valid 필요?
    public void changeUserRole(@PathVariable long userId, @RequestBody UserChangeRoleRequest userChangeRoleRequest) {
        userAdminService.changeUserRole(userId, userChangeRoleRequest);
    }
}
