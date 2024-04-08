package com.bit.envdev.repository;

import com.bit.envdev.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByMemberMemberId(Long memberId);
}
