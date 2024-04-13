package com.bit.envdev.service;

import com.bit.envdev.dto.PaymentContentDTO;
import com.bit.envdev.dto.PaymentDTO;
import com.bit.envdev.entity.CustomUserDetails;
import com.bit.envdev.entity.Member;

import java.util.List;

public interface PaymentService {

    List<PaymentDTO> getPaymentList(long loginMemberId);

    long savePayment(PaymentDTO paymentDTO, List<PaymentContentDTO> paymentContentDTOList, long memberId);

    PaymentDTO getPayment(long paymentId, long memberId);
}
