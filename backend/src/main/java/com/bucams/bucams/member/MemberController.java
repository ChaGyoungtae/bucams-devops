package com.bucams.bucams.member;

import com.bucams.bucams.member.domain.Member;
import com.bucams.bucams.member.dto.MemberJoinDto;
import com.bucams.bucams.member.dto.MemberLoginDto;
import com.bucams.bucams.member.dto.MemberMyInfoDto;
import com.bucams.bucams.member.dto.MemberResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/register")
    public ResponseEntity<Void> join(@RequestBody @Valid MemberJoinDto memberJoinDto) {

        memberService.join(memberJoinDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody @Valid MemberLoginDto memberLoginDto) {

        Long memberId = memberService.login(memberLoginDto);

        return ResponseEntity.ok(memberId);
    }

    @GetMapping("/my-info")
    public ResponseEntity<MemberMyInfoDto> myInfo(@RequestParam Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("회원 정보를 찾지 못했습니다."));

        MemberMyInfoDto memberMyInfoDto = new MemberMyInfoDto(member);

        return ResponseEntity.ok(memberMyInfoDto);
    }

    @GetMapping("/show-all")
    public ResponseEntity<List<MemberResponseDto>> showAll() {
        List<Member> members = memberRepository.findAll();

        List<MemberResponseDto> memberDtos = members.stream()
                .map(member -> new MemberResponseDto(
                        member.getId(),
                        member.getName(),
                        member.getNo() != null ? member.getNo() : "",
                        member.getEmail(),
                        member.getPhone(),
                        member.getStatus(),
                        member.getRole(),
                        member.getDepartment() != null ? member.getDepartment().getName() : "",
                        member.getCurrentCredits()
                ))
                .toList();

        return ResponseEntity.ok(memberDtos);
    }



}
