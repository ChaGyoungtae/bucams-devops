package com.bucams.bucams.durationOfRegistration.domain.dto;

import com.bucams.bucams.durationOfRegistration.domain.DurationOfRegistration;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RequestDorDto {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public DurationOfRegistration toDor(){
        return DurationOfRegistration.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
