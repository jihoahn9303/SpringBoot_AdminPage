package com.example.study.model.network.request;

import com.example.study.model.enumclass.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUserApiRequest {

    private Long id;

    private String account;

    private String password;

    private UserStatus status;

    private String role;

    private LocalDateTime registeredAt;

    private Integer loginFailCount;

    private LocalDateTime lastLoginAt;

    private LocalDateTime unregisteredAt;

    private LocalDateTime passwordUpdatedAt;
}
