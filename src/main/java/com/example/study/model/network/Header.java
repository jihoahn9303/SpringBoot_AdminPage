package com.example.study.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonInclude()
public class Header<T> {

    // 공통부 작성
    //------------------------------------------------------------------------
    // api 통신 시간
    private LocalDateTime transactionTime;  // ISO, YYYY-MM-DD hh:mm:ss

    // api 응답 코드
    private String resultCode;

    // api 부가 설명
    private String description;

    //------------------------------------------------------------------------
    // 사용자의 요청마다 다를 수 있는 데이터 부분은, 보통 제네릭을 통해 객체로 관리한다.
    private T data;

    // OK
    public static <T> Header<T> OK() {
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK").build();
    }

    // DATA OK
    public static <T> Header<T> OK(T data) {
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data).build();
    }

    // ERROR
    public static <T> Header<T> ERROR(String description) {
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description).build();
    }
}
