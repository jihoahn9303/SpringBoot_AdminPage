package com.example.study.sample;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Category;
import com.example.study.model.entity.Partner;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.repository.CategoryRepository;
import com.example.study.repository.PartnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


public class PartnerSample extends StudyApplicationTests {

    private Random random;

    // 코드상으로는 문제가 없는데, junit5 환경에서 repository 의존성 주입 과정에서 nullpointexception이 나오는 경우 -> 올바른 @Test 어노테이션이 적용되었는지 패키지 확인할 것!
    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void sampleCreate(){
        random = new Random();
        List<Category> categoryList = categoryRepository.findAll();

        for(int i = 0; i < categoryList.size(); i++){
            Category category = categoryList.get(i);

            for(int j = 1; j < 10; j++){

                // 가입 상태 랜덤
                int div = (random.nextInt(10)+1) % 3;
                UserStatus status = UserStatus.UNREGISTERED;
                switch (div) {
                    case 0:
                        status = UserStatus.UNREGISTERED;
                        break;
                    case 1:
                        status = UserStatus.REGISTERED;
                        break;
                    case 2:
                        status = UserStatus.AWAITING;
                        break;
                }

                Partner partner = Partner.builder()
                        .category(category)
                        .name(category.getTitle()+j+" 호점")
                        .status(status)
                        .address("서울시 강남구 "+j+"번길"+random.nextInt(100)+1+"호")
                        .callCenter("070-"+String.format("%04d", random.nextInt(100)+1)+"-"+String.format("%04d", random.nextInt(100)+1))
                        .partnerNumber("010-1111-"+String.format("%04d", i))
                        .businessNumber((random.nextInt(999999999)+1)+""+j)
                        .ceoName(j+" 대표")
                        .registeredAt(getRandomDate())
                        .unregisteredAt(status.equals(UserStatus.UNREGISTERED) ? getRandomDate() : null )
                        .build();

//                log.info("{}",partner);
                partnerRepository.save(partner);
            }
        }
    }


    private LocalDateTime getRandomDate(){
        return LocalDateTime.of(2020,getRandomNumber(),getRandomNumber(),getRandomNumber(),getRandomNumber(),getRandomNumber());
    }

    private int getRandomNumber(){
        return random.nextInt(11)+1;
    }
}
