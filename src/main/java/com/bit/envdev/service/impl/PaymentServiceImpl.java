package com.bit.envdev.service.impl;

import com.bit.envdev.dto.PaymentContentDTO;
import com.bit.envdev.dto.PaymentDTO;
import com.bit.envdev.dto.ReviewDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.Member;
import com.bit.envdev.entity.Payment;
import com.bit.envdev.entity.PaymentContent;
import com.bit.envdev.repository.MemberRepository;
import com.bit.envdev.repository.PaymentRepository;
import com.bit.envdev.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<PaymentDTO> getPaymentList(long loginMemberId) {

        System.out.println("loginMemberId = " + loginMemberId);

        List<Payment> paymentList = paymentRepository.findByMemberMemberId(loginMemberId);

        return paymentList.stream()
                .map(payment -> {
                    PaymentDTO dto = payment.toDTO();
                    dto.getMemberDTO().setPassword("");
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public long savePayment(PaymentDTO paymentDTO, List<PaymentContentDTO> paymentContentDTOList, long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();

        paymentDTO.setMemberDTO(member.toDTO());

        System.out.println(member.toDTO());
        paymentContentDTOList.forEach(paymentContentDTO -> System.out.println(paymentContentDTO));

        Payment payment = paymentDTO.toEntity();

        List<PaymentContent> paymentContentList = paymentContentDTOList.stream().map(paymentContentDTO -> paymentContentDTO.toEntity(payment)).toList();

        paymentContentList.forEach(paymentContent -> payment.addContentsList(paymentContent));

        paymentRepository.save(payment);

        return payment.getPaymentId();
    }

    @Override
    public PaymentDTO getPayment(long paymentId, long memberId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        return Payment.builder()
                .paymentId(payment.getPaymentId())
                .paymentDate(payment.getPaymentDate())
                .totalPrice(payment.getTotalPrice())
                .contentsList(payment.getContentsList())
                .paymentUniqueNo(payment.getPaymentUniqueNo())
                .member(member)
                .build()
                .toDTO();
    }

    @Override
    public Page<Map<String, Object>> getPurchaseList(Pageable pageable, Member member) {
        return paymentRepository.getPurchaseList(pageable, member.getMemberId());
    }
}
