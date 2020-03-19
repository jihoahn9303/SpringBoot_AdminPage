package com.example.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Spring Bean : The Object that is instantiated, assembled, and otherwise managed by a Spring IOC Container.

@Configuration  // 스프링 IOC Container에게 해당 클래스를 Bean 구성 Class임을 알려주는 어노테이션. 설정과 관련된 어노테이션이다
@EnableJpaAuditing // JPA Auditing을 활성화 하고, 현재 자기 자신의 Auditor를 찾기 위해 사용하는 Bean을 설정
public class JpaConfig {

    // Bean for AppllicationTests
//    @Bean
//    public AuditorAware<String> loginUserAuditorAware() {
//        return new LoginUserAuditorAware();  // AuditorAware interface를 구현한 LoginUserAuditorAware instance를 생성하여 Bean으로 등록 -> 이곳에서 Auditor가 제공된다.
//    }
}
