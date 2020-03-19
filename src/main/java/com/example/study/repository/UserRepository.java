package com.example.study.repository;

import com.example.study.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository : 따로 쿼리문을 작성하지 않아도, 기본적인 CRUD 연산을 가능하게 함.
// @Repository 어노테이션을 사용하며, JpaRepository interface를 상속한다.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 하나의 번호로 여러 명이 가입할 수 있기 때문에, 가장 최근에 등록한(Desc) 유저를 뽑아줌.
    User findFirstByPhoneNumberOrderByIdDesc(String phoneNumber);
}
