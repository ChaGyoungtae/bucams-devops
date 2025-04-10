package com.bucams.bucams.lecture.domain.dto;

import com.bucams.bucams.lecture.domain.entity.Lecture;
import com.bucams.bucams.lecture.domain.entity.Type;
import com.bucams.bucams.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterLectureResponseDto {

	// 강의 - 교수님 정보
	private Long lectureId;
	private String lectureName;
	private String schedule;
	private String professorName;
	private String departmentName;
	private Type lectureType;

	// 학생 정보
	private String studentName;

	@Builder
	public RegisterLectureResponseDto(Lecture lecture, Member student) {
		this.lectureId = lecture.getId();
		this.lectureName = lecture.getName();
		this.schedule = lecture.getSchedule();
		this.professorName = lecture.getMember().getName();
		this.departmentName = lecture.getMember().getDepartment().getName();
		this.lectureType = lecture.getType();
		this.studentName = student.getName();
	}
}
