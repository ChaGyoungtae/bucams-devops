package com.bucams.bucams.registration.dto;

import com.bucams.bucams.lecture.domain.entity.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDto {

    private Long registrationId;

    private Long memberId;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분")
    private LocalDateTime registeredAt;

    private Long lectureId;

    private String lectureName;

    private String professorName;

    private String schedule;

    private Type type;

    private int credit;


}
