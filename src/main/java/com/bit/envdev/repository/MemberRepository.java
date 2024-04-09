package com.bit.envdev.repository;

import com.bit.envdev.constant.Role;
import com.bit.envdev.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<Member> findByUserNickname(String userNickname);

    @Transactional
    void deleteByUsername(String username);


    List<Member> findTop4ByOrderByMemberIdDesc();


    Page<Member> findByRoleAndUserNicknameContainingOrderByCreatedAtDesc(Pageable pageable, Role role, String searchKeyword);

    long countByRole(Role role);

    List<Member> findTop4ByRole(Role role);

    Page<Member> findByUserNicknameContainingOrderByCreatedAtDesc(Pageable pageable, String searchKeyword);

    @Query("SELECT COUNT(m) FROM Member m WHERE DATE(m.createdAt) = CURRENT_DATE")
    long countTodayRegistrations();
}
