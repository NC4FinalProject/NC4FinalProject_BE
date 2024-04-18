package com.bit.envdev.repository;

import com.bit.envdev.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentRepositoryCustom {
    List<Payment> findByMemberMemberId(Long memberId);
}
