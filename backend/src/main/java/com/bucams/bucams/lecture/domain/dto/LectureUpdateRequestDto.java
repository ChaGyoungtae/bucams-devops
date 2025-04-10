package com.bucams.bucams.lecture.domain.dto;

import com.bucams.bucams.lecture.domain.entity.Type;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureUpdateRequestDto {

	private Long professorId;

	private Long lectureId;

	@NotBlank(message="강의 제목은 필수 작성 항목입니다.")
	private String lectureName; // 강의 제목

	@NotBlank(message="강의 시간은 필수 작성 항목입니다.")
	private String schedule; // 강의 시간

	@NotNull
	@Min(value = 15, message = "수강 정원은 15 이상이어야 합니다.")
	private int limitCount;

	@NotNull
	@Min(value = 1, message = "학점은 1 이상이어야 합니다.")
	private int credit;

	private Type lectureType;
}
