package com.bucams.bucams.lecture.domain.dto;

import com.bucams.bucams.lecture.domain.entity.Lecture;
import com.bucams.bucams.lecture.domain.entity.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureResponseDto {

	private Long lectureId;
	private String name; // 강의 제목


	// 교수님 Table 조인 필요
	private String phone; // 교수님 전화번호
	private String email; // 교수님 이메일
	private String professorName; // 교수님 이름
	private String department; // 학과


	private String schedule; // 강의 시간

	private int limitCount; // 최대 수강인원
	private int currCount; // 현재 수강인원
	private int credit; // 학점
	private Type lectureType; // 전공/교양


	public LectureResponseDto(Lecture lecture) {
		this.lectureId = lecture.getId();
		this.name = lecture.getName();
		this.schedule = lecture.getSchedule();
		this.limitCount = lecture.getLimitCount();
		this.currCount = lecture.getCurrCount();
		this.credit = lecture.getCredit();
		this.lectureType = lecture.getType();

		// member → 교수 정보
		this.professorName = lecture.getMember().getName();
		this.phone = lecture.getMember().getPhone();
		this.email = lecture.getMember().getEmail();

		// 학과
		this.department = lecture.getMember().getDepartment().getName();
	}



}
