package com.nanum.enrollservice.movein.domain;

import com.nanum.common.BaseTimeEntity;
import com.nanum.common.MoveInStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "update move_in set delete_at=now() where id=?")
@Where(clause = "delete_at is null")

public class MoveIn extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long houseId;

    @Column(nullable = false)
    private String houseName;

    private String houseImg;

    private String streetAddress;

    private String lotAddress;

    private String detailAddress;

    private String zipCode;

    @Column(nullable = false)
    private Long roomId;

    private String roomName;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long hostId;

    @Column(nullable = false)
    @Comment("희망 입주일 -> 당일 예약 불가")
    private LocalDate moveDate;

    @Comment("계약 만료일")
    private LocalDate expireDate;

    @Comment("문의 내용")
    private String inquiry;

    @Comment("입주 신청 상태 -> 대기 중, 계약 중, 거부됨, 취소됨, 계약 완료됨")
    @Enumerated(EnumType.STRING)
    private MoveInStatus moveInStatus;

}
