package com.bucams.bucams.member.dto;

import com.bucams.bucams.member.domain.Member;
import com.bucams.bucams.member.domain.Role;
import com.bucams.bucams.member.domain.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberMyInfoDto {

    private String name;

    private String no;

    private String email;

    private String phone;

    private Status status;

    private Role role;

    private String departmentName;

    public MemberMyInfoDto(Member member) {
        this.name = member.getName();
        this.no = member.getNo();
        this.email = member.getEmail();
        this.phone = member.getPhone();
        this.status = member.getStatus();
        this.role = member.getRole();
        this.departmentName = member.getDepartment().getName();
    }
}
