package com.bit.envdev.entity;

import com.bit.envdev.dto.MemberGraphDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@IdClass(MemberGraphId.class)
public class MemberGraph {
    @Id
    private LocalDateTime registration_date;
    @Id
    private Long user_count;

    public MemberGraphDTO toDTO() {
        return MemberGraphDTO.builder()
                .registration_date(registration_date)
                .user_count(user_count)
                .build();
    }
}
