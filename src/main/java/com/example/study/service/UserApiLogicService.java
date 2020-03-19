package com.example.study.service;

import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.sun.tools.javac.util.Assert;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// 해당 클래스는 서비스로 동작함. 서비스와 관련하여 실질적인 동작이 구현되는 곳
@Service
@Accessors(chain = true)
public class UserApiLogicService extends BasicService<UserApiRequest, UserApiResponse, User> {

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {
        // 1. request data
        UserApiRequest userApiRequest = request.getData();

        // 2. user 생성
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now()).build();

        User newUser = baseRepository.save(user);
        Assert.checkNonNull(newUser);

        // 3. 생성된 데이터를 통해 -> UserApiResponse return
        return Header.OK(response(user));
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        // id -> repository getOne, getById
        Optional<User> optional = baseRepository.findById(id);

        // user -> userApiResponse return
        return optional.map(user -> response(user)).map(userApiResponse -> Header.OK(userApiResponse))
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        // 1. Get request
        UserApiRequest data = request.getData();

        // 2. find user
        Optional<User> targetUser = baseRepository.findById(data.getId());
        Assert.checkNonNull(targetUser);

        return targetUser.map(user -> {
            // 3. update user
            user.setAccount(data.getAccount()).setPassword(data.getPassword()).setStatus(data.getStatus()).setPhoneNumber(data.getPhoneNumber()).setEmail(data.getEmail());
            return user;
        }).map(user -> baseRepository.save(user))
                // 4. user to UserApiResponse
                .map(user -> response(user)).map(userApiResponse -> Header.OK(userApiResponse))
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        // 1. find user by id
        Optional<User> targetUser = baseRepository.findById(id);

        // 2. delete user
        return targetUser.map(user -> {
            baseRepository.delete(user);

            // 3. response
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    // Paging code
    @Override
    public Header<List<UserApiResponse>> search(Pageable pageable) {
        Page<User> users = baseRepository.findAll(pageable);

        List<UserApiResponse> userApiResponseList = users.stream()
                .map(user -> response(user))
                .collect(Collectors.toList());

        return Header.OK(userApiResponseList);
    }

    private UserApiResponse response(User user) {
        // user -> Header<UserApiResponse>

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .account(user.getAccount())
                .email(user.getEmail())
                .password(user.getPassword())  // todo 암호화, 길이 etc
                .status(user.getStatus())
                .phoneNumber(user.getPhoneNumber())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .id(user.getId()).build();

        // Header + data
        return userApiResponse;
    }
}
