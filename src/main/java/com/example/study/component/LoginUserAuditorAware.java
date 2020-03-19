package com.example.study.component;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

// createdBy나 lastModifiedBy도 자동으로 설정하기 위한 클래스
// 추후에 SpringBoot Security를 적용할 것!!(권한 통과한 사람에게만 값을 반환(계정 정보 etc..)

//@bean과 @component의 차이점 : https://galid1.tistory.com/494?category=769011, https://goodgid.github.io/Spring-Component-vs-Bean/
@Component
public class LoginUserAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("AdminServer");
    }
}
