package com.bucams.bucams.member.domain;

import com.bucams.bucams.department.domain.Department;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member-id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(unique = true, length = 20)
    private String no;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department-id")
    private Department department;

    private int currentCredits = 0;

    public void addCurrentCredits(int credits) {this.currentCredits += credits;}

    public Member(String name, String no, String email, String password, String phone, Status status, Role role, Department department) {
        this.name = name;
        this.no = no;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.status = status;
        this.role = role;
        this.department = department;
    }

}


