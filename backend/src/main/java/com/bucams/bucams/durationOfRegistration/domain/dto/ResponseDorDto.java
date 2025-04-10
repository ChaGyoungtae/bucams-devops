package com.bucams.bucams.durationOfRegistration.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ResponseDorDto {

    private Long id;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분")
    private LocalDateTime endDate;
}
