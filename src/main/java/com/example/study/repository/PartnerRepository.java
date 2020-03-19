package com.example.study.repository;

import com.example.study.model.entity.Category;
import com.example.study.model.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    // partner table의 외래키인 category_id를 활용하여, partner table을 조회( partner : category = N : 1 관계 )
    List<Partner> findByCategory(Category category);
}
