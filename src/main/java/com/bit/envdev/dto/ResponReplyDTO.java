package com.bit.envdev.dto;

import com.bit.envdev.constant.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResponReplyDTO {

    private String videoReplyContent;
}
