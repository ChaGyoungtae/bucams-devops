package com.bucams.bucams.lecture.domain.entity;

import com.bucams.bucams.lecture.domain.dto.LectureUpdateRequestDto;
import com.bucams.bucams.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture-id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private int limitCount;

    @Column(nullable = false)
    private int credit;

    @Column(nullable = false)
    private int currCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    // 강의 시간 -> "수 1-3" , "목 1-3"  형식으로 저장.
    @Column(nullable = false)
    private String schedule;


    public void addCurrCount() {
        this.currCount++;
    }

    @Builder
    public Lecture(LectureUpdateRequestDto lectureRequestDto, Member member) {
        this.name = lectureRequestDto.getLectureName();
        this.schedule =  String.join( "," , lectureRequestDto.getSchedule() );
        this.limitCount = lectureRequestDto.getLimitCount();
        this.credit = lectureRequestDto.getCredit();
        this.type = lectureRequestDto.getLectureType();
        this.member = member;
    }

    public void update(LectureUpdateRequestDto lectureRequestDto){
        this.name = lectureRequestDto.getLectureName();
        this.schedule = String.join( "," , lectureRequestDto.getSchedule() );
        this.limitCount = lectureRequestDto.getLimitCount();
        this.credit = lectureRequestDto.getCredit();
        this.type = lectureRequestDto.getLectureType();
    }
}
