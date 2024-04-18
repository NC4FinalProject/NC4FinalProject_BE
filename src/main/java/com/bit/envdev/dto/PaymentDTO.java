package com.bit.envdev.dto;

import com.bit.envdev.entity.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaymentDTO {
    private long paymentId;
    private long totalPrice;
    private Date paymentDate;
    private String paymentUniqueNo;
    private MemberDTO memberDTO;
    private String purchaseName; // 인풋박스로 사용자로부터 입력받기 , required
    private List<PaymentContentDTO> contentsList; // 구매한 컨텐츠들이 담기는 리스트
    private long cartId;

    public Payment toEntity() {
        return Payment.builder()
                .paymentId(this.paymentId)
                .totalPrice(this.totalPrice)
                .paymentDate(this.paymentDate)
                .paymentUniqueNo(this.paymentUniqueNo)
                .contentsList(new ArrayList<>())
                .member(this.memberDTO.toEntity())
                .build();
    }
}

