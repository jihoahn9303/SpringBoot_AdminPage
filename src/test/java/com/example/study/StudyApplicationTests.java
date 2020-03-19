package com.example.study;

import com.example.study.config.JpaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Import(JpaConfig.class)  // Annotation for Entity Auditing
@DataJpaTest  // Annotation for Database testing
@Transactional(propagation = Propagation.NOT_SUPPORTED) // rollback 방지
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // 테스트 결과가 실제 데이터베이스에 반영되도록 함.
public class StudyApplicationTests {

    @Test
    void contextLoads() {
    }

}
