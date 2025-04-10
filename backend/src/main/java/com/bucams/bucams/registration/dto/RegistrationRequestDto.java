package com.bucams.bucams.registration.dto;

import com.bucams.bucams.lecture.domain.entity.Lecture;
import com.bucams.bucams.member.domain.Member;
import com.bucams.bucams.registration.domain.Registration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDto {

    private Long registrationId;

    private Long memberId;

    private Long lectureId;

    private LocalDateTime registeredAt;


    public Registration toRegistration(Member member, Lecture lecture) {
        return Registration.builder()
                .member(member)
                .lecture(lecture)
                .registeredAt(LocalDateTime.now())
                .build();
    }
}