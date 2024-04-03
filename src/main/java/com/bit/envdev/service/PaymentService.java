package com.bit.envdev.service;

import com.bit.envdev.dto.PaymentDTO;
import com.bit.envdev.entity.CustomUserDetails;

import java.util.List;

public interface PaymentService {

    List<PaymentDTO> getPaymentList(long loginMemberId);
}
