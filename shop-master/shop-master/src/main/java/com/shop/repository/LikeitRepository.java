package com.shop.repository;

import com.shop.entity.Likeit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeitRepository extends JpaRepository<Likeit, Long> {

    Likeit findByMemberId(Long memberId);

}