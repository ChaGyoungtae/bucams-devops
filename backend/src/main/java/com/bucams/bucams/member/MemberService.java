package com.bucams.bucams.member;

import com.bucams.bucams.department.domain.Department;
import com.bucams.bucams.department.domain.DepartmentRepository;
import com.bucams.bucams.member.domain.Member;
import com.bucams.bucams.member.dto.MemberJoinDto;
import com.bucams.bucams.member.dto.MemberLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;

    // 회원가입
    @Transactional
    public void join(MemberJoinDto memberJoinDto) {
        // 학과 검색
        Department department = null;
        if (memberJoinDto.getDepartmentId() != null) {
            department = departmentRepository.findById(memberJoinDto.getDepartmentId())
                    .orElse(null);
        }

        // 중복되는 이메일이 있는지 확인
        validateDuplicateEmail(memberJoinDto.getEmail());

        // 비밀번호 인코딩
        memberJoinDto.setPassword(BCrypt.hashpw(memberJoinDto.getPassword(), BCrypt.gensalt()));

        // Member 객체 생성, 저장
        Member member = memberJoinDto.toEntity(department);
        memberRepository.save(member);
    }

    // 로그인
    @Transactional
    public Long login(MemberLoginDto memberLoginDto) {

        String email = memberLoginDto.getEmail();
        String password = memberLoginDto.getPassword();

        Optional<Member> member = memberRepository.findByEmail(email);

        // Member 인증
        return member
                .filter(m -> BCrypt.checkpw(password, m.getPassword()))
                .map(Member::getId)
                .orElseThrow(() -> new IllegalStateException("이메일 또는 비밀번호가 일치하지 않습니다."));
    }

    // 중복되는 이메일이 있는지 확인하는 메서드
    private void validateDuplicateEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }
}
