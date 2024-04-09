package com.bit.envdev.dto;

import com.bit.envdev.entity.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private Long paymentId;
    private Long totalPrice;
    private Date paymentDate;
    private String paymentUniqueNo;
    private MemberDTO memberDTO;
    private String purchaseName; // 인풋박스로 사용자로부터 입력받기 , required
    private List<PaymentContentDTO> contentsList; // 구매한 컨텐츠들이 담기는 리스트

    public Payment toEntity() {
        return Payment.builder()
                .paymentId(this.paymentId)
                .totalPrice(this.totalPrice)
                .paymentDate(this.paymentDate)
                .paymentUniqueNo(this.paymentUniqueNo)
                .contentsList(this.contentsList.stream().map(PaymentContentDTO::toEntity).toList())
                .member(this.memberDTO.toEntity())
                .build();
    }
}

