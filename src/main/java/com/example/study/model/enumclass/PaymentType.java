package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentType {

    CARD(0, "카드 결제"),
    CASH(1, "현금 결제"),
    TRANSFER(2, "계좌 이체"),
    CALL(3, "전화 결제"),
    POINT(4, "포인트 결제");

    private Integer paymentTypeId;

    private String title;
}
