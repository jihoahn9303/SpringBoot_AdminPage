package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public abstract class BasicService<Request, Response, Entity> implements CrudInterface<Request, Response> {

    // 기본적으로, 각 서비스는 자신의 객체에 대한 repository를 생성하게 되어있다.
    @Autowired(required = false)
    protected JpaRepository<Entity, Long> baseRepository;

}
