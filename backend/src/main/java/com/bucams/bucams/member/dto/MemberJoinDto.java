package com.bucams.bucams.member.dto;

import com.bucams.bucams.department.domain.Department;
import com.bucams.bucams.member.domain.Member;
import com.bucams.bucams.member.domain.Role;
import com.bucams.bucams.member.domain.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberJoinDto {

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    private String no;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    // @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    // @Pattern(regexp = "^\\S+$", message = "비밀번호를 확인해주세요.")
    private String password;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phone;

    private Status status;

    @NotNull(message = "역할을 입력해주세요.")
    private Role role;

    private Long departmentId;

    public Member toEntity(Department department) {

        return new Member(this.name, this.no, this.email, this.password, this.phone, this.status, this.role, department);
    }
}
