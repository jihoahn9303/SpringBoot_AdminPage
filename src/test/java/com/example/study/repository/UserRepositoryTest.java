package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {
    // Dependency Injection (DI) : singleton pattern
    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        String account = "Test02";
        String password = "Test02";
        UserStatus status = UserStatus.REGISTERED;
        String email = "Test01@gmail.com";
        String phoneNumber = "010-1111-2222";
        LocalDateTime registeredAt = LocalDateTime.now();
//        LocalDateTime createdAt = LocalDateTime.now();
//        String createdBy = "AdminServer";

        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);
//        user.setCreatedAt(createdAt);
//        user.setCreatedBy(createdBy);

        // Builder pattern을 활용한 객체 생성 예시
        // User u = User.builder().account(account).password(password).status(status).email(email).build();

        User newUser = userRepository.save(user);
        Assertions.assertNotNull(newUser);
    }

    @Test
    @Transactional
    public void read() {
        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");

        if(user != null) {
            user.getOrderGroupList().stream().forEach(orderGroup -> {
                System.out.println("------------------주문 묶음--------------------");
                System.out.println("총 금액 : " + orderGroup.getTotalPrice());
                System.out.println("총 수량 : " + orderGroup.getTotalQuantity());
                System.out.println("수령지 : " + orderGroup.getRevAddress());
                System.out.println("수령인 : " + orderGroup.getRevName());

                System.out.println("------------------주문 상세--------------------");
                orderGroup.getOrderDetailList().forEach(orderDetail -> {
                    System.out.println("파트너사 이름 : " + orderDetail.getItem().getPartner().getName());
                    System.out.println("파트너사 카테고리 : " + orderDetail.getItem().getPartner().getCategory().getTitle());
                    System.out.println("주문 상품 : " + orderDetail.getItem().getName());
                    System.out.println("고객센터 번호 : " + orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("주문의 상태 : " + orderDetail.getStatus());
                    System.out.println("도착 예정일자 : " + orderDetail.getArrivalDate());
                });
            });

            Assertions.assertNotNull(user);
        }
    }

    @Test
    public void update() {
        //     id가 2번인 user를 조회하라.
        Optional<User> user = userRepository.findById(2L);
        //     user가 null일수도 있기 때문에, 존재하는 경우에만 업데이트 해준다.
        user.ifPresent(selectUser->{
            selectUser.setAccount("pppp");

//          userRepository.findById(2L);를 통해 id가 2번인 유저가 존재하면 기존에 존재하는 유저라고 판단하고, 업데이트를 실시함.
            userRepository.save(selectUser);
        });

        //Accessor(chain = true) pattern을 활용한 객체 속성 갱신 예시
        /*Optional<User> targetUser = userRepository.findById(2L);
        targetUser.ifPresent(u -> u = new User().setAccount("").setEmail("").setPassword(""));*/
    }

    @Test
//  Transactional annotation :  Do roll back transaction
    @Transactional
    public void delete() {
        Optional<User> user = userRepository.findById(3L);
        Assertions.assertTrue(user.isPresent());

        user.ifPresent(selectUser->userRepository.delete(selectUser));

        Optional<User> deleteUser = userRepository.findById(2L);

        Assertions.assertFalse(deleteUser.isPresent());
    }
}
