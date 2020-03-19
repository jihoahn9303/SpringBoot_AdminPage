package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    DEPOSIT_WAITING(0, "입금 확인 중"),
    DEPOSIT_FINISH(1, "입금 확인 완료"),
    DELIVER_READY(2, "배송 준비 중"),
    DELIVER_START(3, "배송 중"),
    DELIVER_FINISH(4, "배송 완료");

    private Integer statusId;

    private String title;
}
