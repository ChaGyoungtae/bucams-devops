package com.bucams.bucams.member.dto;

import com.bucams.bucams.member.domain.Role;
import com.bucams.bucams.member.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;

    private String name;

    private String no;

    private String email;

    private String phone;

    private Status status;

    private Role role;

    private String departmentName;

    private int currentCredits;
}
