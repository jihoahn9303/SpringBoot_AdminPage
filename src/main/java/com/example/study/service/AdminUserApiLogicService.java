package com.example.study.service;

import com.example.study.model.entity.AdminUser;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.AdminUserApiRequest;
import com.example.study.model.network.response.AdminUserApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AdminUserApiLogicService extends BasicService<AdminUserApiRequest, AdminUserApiResponse, AdminUser> {

    @Override
    public Header<AdminUserApiResponse> create(Header<AdminUserApiRequest> request) {
        AdminUserApiRequest body = request.getData();

        AdminUser user = AdminUser.builder()
                .account(body.getAccount()).password(body.getPassword()).status(UserStatus.AWAITING)
                .role(body.getRole()).loginFailCount(0).passwordUpdatedAt(LocalDateTime.now())
                .registeredAt(LocalDateTime.now()).build();

        AdminUser newUser = baseRepository.save(user);

        return Header.OK(response(newUser));
    }

    @Override
    public Header<AdminUserApiResponse> read(Long id) {
        Optional<AdminUser> optional = baseRepository.findById(id);

        return optional.map(adminUser -> Header.OK(response(adminUser)))
                .orElseGet(() -> Header.ERROR("해당 id의 관리자가 존재하지 않음."));
    }

    @Override
    public Header<AdminUserApiResponse> update(Header<AdminUserApiRequest> request) {
        AdminUserApiRequest body = request.getData();

        Optional<AdminUser> optional = baseRepository.findById(body.getId());

        return optional.map(adminUser -> {
            adminUser.setAccount(body.getAccount()).setPassword(body.getPassword()).setStatus(body.getStatus())
                    .setLoginFailCount(body.getLoginFailCount()).setLastLoginAt(body.getLastLoginAt()).setUnregisteredAt(body.getUnregisteredAt())
                    .setRole(body.getRole()).setPasswordUpdatedAt(body.getPasswordUpdatedAt());

            AdminUser newAdminUser = baseRepository.save(adminUser);

            return Header.OK(response(newAdminUser));
        })
                .orElseGet(() -> Header.ERROR("해당 id의 관리자가 존재하지 않음."));
    }

    @Override
    public Header delete(Long id) {
        Optional<AdminUser> optional = baseRepository.findById(id);

        return optional.map(adminUser -> {
            baseRepository.delete(adminUser);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("해당 id의 관리자가 존재하지 않음."));
    }

    @Override
    public Header<List<AdminUserApiResponse>> search(Pageable pageable) {
        Page<AdminUser> adminUsers = baseRepository.findAll(pageable);

        List<AdminUserApiResponse> adminUserApiResponseList = adminUsers.stream().map(adminUser -> response(adminUser))
                .collect(Collectors.toList());

        return Header.OK(adminUserApiResponseList);
    }

    public AdminUserApiResponse response(AdminUser user) {
        AdminUserApiResponse body = AdminUserApiResponse.builder()
                .id(user.getId()).account(user.getAccount()).password(user.getPassword())
                .status(user.getStatus()).role(user.getRole()).loginFailCount(user.getLoginFailCount())
                .passwordUpdatedAt(user.getPasswordUpdatedAt()).registeredAt(user.getRegisteredAt()).lastLoginAt(user.getLastLoginAt())
                .unregisteredAt(user.getUnregisteredAt()).build();

        return body;
    }
}
