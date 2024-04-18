package com.bit.envdev.entity;

import com.bit.envdev.dto.QnaDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member askUser;

    @Column(name = "answer")
    private String answerUser;

    @Column(name = "category")
    private String category;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "answered")
    private boolean answered;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date createdAt;

    public QnaDTO toDTO() {
        return QnaDTO.builder()
                .id(this.id)
                .askUser(this.askUser)
                .answerUser(this.answerUser)
                .category(this.category)
                .content(this.content)
                .answered(this.answered)
                .createdAt(this.createdAt)
                .build();
    }
}
