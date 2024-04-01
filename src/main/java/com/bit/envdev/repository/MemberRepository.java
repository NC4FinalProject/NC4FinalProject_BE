package com.bit.envdev.repository;

import com.bit.envdev.entity.Member;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<Member> findByUserNickname(String userNickname);

    @Transactional
    void deleteByUsername(String username);
}
