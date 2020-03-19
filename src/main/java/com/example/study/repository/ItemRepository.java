package com.example.study.repository;

import com.example.study.model.entity.Item;
import com.example.study.model.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByPartner(Partner partner);
}
