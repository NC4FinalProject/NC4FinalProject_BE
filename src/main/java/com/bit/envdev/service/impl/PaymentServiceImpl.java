package com.bit.envdev.service.impl;

import com.bit.envdev.dto.PaymentDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Payment;
import com.bit.envdev.repository.PaymentRepository;
import com.bit.envdev.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    @Override
    public List<PaymentDTO> getPaymentList(long loginMemberId) {

        System.out.println("loginMemberId = " + loginMemberId);

        List<Payment> paymentList = paymentRepository.findByMemberId(loginMemberId);

        return paymentList.stream()
                .map(Payment::toDTO)
                .collect(Collectors.toList());
    }
}
