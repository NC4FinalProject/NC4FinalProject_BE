package com.bit.envdev.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentContentDTO {
    private String teacherName;
    private String contentsTitle;
    private String thumbnail;
    private int price;
}
