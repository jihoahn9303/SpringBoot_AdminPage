package com.example.study.model.network.response;

import com.example.study.model.enumclass.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnerApiResponse {
    private Long id;

    private String name;

    private UserStatus status;

    private String address;

    private String callCenter;

    private String businessNumber;

    private String partnerNumber;

    private String ceoName;

    private Long categoryId;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

}
