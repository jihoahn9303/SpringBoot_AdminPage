package com.example.study.model.network.request;

import com.example.study.model.enumclass.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnerApiRequest {

    private Long id;

    private String name;

    private UserStatus status;

    private String address;

    private String callCenter;

    private String businessNumber;

    private String partnerNumber;

    private String ceoName;

    private Long categoryId;
}
