package com.nanum.enrollservice.housetour.domain;

import com.nanum.common.BaseTimeEntity;
import com.nanum.common.HouseTourStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "update house_tour set delete_at=now() where id=?")
@Where(clause = "delete_at is null")
public class HouseTour extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long houseId;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long hostId;

    @Column(nullable = false)
    @Comment("희망 투어 날짜 -> 당일 예약 불가")
    private LocalDate tourDate;

    @Comment("문의 내용")
    private String inquiry;

    @Comment("투어 상태 -> 대기 중, 승인, 거부, 취소, 투어 완료")
    @Enumerated(EnumType.STRING)
    private HouseTourStatus houseTourStatus;

    @OneToOne
    HouseTourTime houseTourTime;
}
