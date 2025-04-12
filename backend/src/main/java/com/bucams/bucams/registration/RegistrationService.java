package com.bucams.bucams.registration;

import com.bucams.bucams.lecture.domain.entity.Lecture;
import com.bucams.bucams.lecture.domain.repository.LectureRepository;
import com.bucams.bucams.lecture.domain.service.LectureService;
import com.bucams.bucams.member.MemberRepository;
import com.bucams.bucams.member.MemberService;
//import com.bucams.bucams.registration.dto.RegistrationCreateDto;
//import com.bucams.bucams.registration.dto.RegistrationCreateResponseDto;
import com.bucams.bucams.member.domain.Member;
import com.bucams.bucams.registration.domain.Registration;
import com.bucams.bucams.registration.dto.AllRegistrationResponseDto;
import com.bucams.bucams.registration.dto.RegistrationRequestDto;
import com.bucams.bucams.registration.dto.RegistrationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    private final LectureService lectureService;

    private final MemberService memberService;

    private final LectureRepository lectureRepository;
    private final MemberRepository memberRepository;

    // 수강 신청 내역 생성
//    public Long createRegistration(Long memberId, Long lectureId, RegistrationRequestDto registrationRequestDto) {
//        Member member = getMember(memberId); // Member 조회
//        Lecture lecture = getLecture(lectureId); // Lecture 조회
//
//        // DTO로부터 Registration 객체 생성
//        Registration registration = registrationRequestDto.toRegistration(member, lecture);
//
//        Long id = registrationRepository.create(registration);
//
//        // 확인용 로그
//        log.info("Registration created: {}", id);
//        return id;
//    }
    

    public Long createRegistration(@RequestBody RegistrationRequestDto registrationRequestDto) {

        Member member = memberRepository.findById(registrationRequestDto.getMemberId()).orElse(null);
        Lecture lecture = lectureRepository.findById(registrationRequestDto.getLectureId()).orElse(null);
    // dto로부터 객체 생성
    Registration registration = registrationRequestDto.toRegistration(member, lecture);

    Long id = registrationRepository.create(registration);


    // 확인용 로그
        log.info("Registration created: {}", id);
        return id;
    }

// CreateResponseDto 사용 version
//    public RegistrationCreateResponseDto createRegistration(@RequestBody RegistrationCreateDto registrationCreateDto) {
//
//        Member member = memberService.getMember();
//
//        Lecture lecture = lectureRepository.findOne(registrationCreateDto.getLectureId());
//
//        if (lecture == null) {
//            throw new RuntimeException("Lecture not found");
//        }
//
//        Optional<Registration> newRegistration = registrationRepository.findById(id);
//
//        if(newRegistration.isEmpty()){
//            throw new RuntimeException("수강신청 내역 생성 실패");
//        }
//
//        Registration registration = registrationCreateDto.toRegistration(member, lecture);
//
//        Long id = registrationRepository.create(registration);
//
//        RegistrationCreateResponseDto responseDto = registrationCreateDto.toResponseDto(newRegistration.get().getRegistration(), member.getId(), newRegistration.get().getRegisteredAt());
//        return responseDto;

//        CommentCreateResponseDto responseDto = commentCreateDto.toResponseDto(member.getName(), newComment.get().getComment(), member.getId(), newComment.get().getRegDate());
//        return responseDto;
//    }

    // memberID 전달
    @Transactional(readOnly = true)
    public List<RegistrationResponseDto> findByMemberId(Long memberId) {
        List<RegistrationResponseDto> registrationResponseDtos = registrationRepository.findRegistrationRequestDtoByMemberId(memberId);

        return registrationResponseDtos;
    }

    // 삭제
    @Transactional
    public void deleteRegistration(Long registrationId) {
        Registration deleteRegistration = registrationRepository.findById(registrationId);
        if(deleteRegistration != null) {
            registrationRepository.delete(deleteRegistration);
        } else {
            log.info("Registration not found: {}", registrationId);
        }

    }

    // registrationId 전달 ver.1
//    @Transactional(readOnly = true)
//    public List<RegistrationRequestDto> findByregistrationId(Long registrationId) {
//        List<RegistrationRequestDto> registrationRequestDtos = registrationRepository.findRegistrationRequestDtoByRegistrationId(registrationId);
//
//        return registrationRequestDtos;
//    }


    @Transactional(readOnly = true)
    public List<AllRegistrationResponseDto> findAllRegistrations() {
        return registrationRepository.findAllRegistrations();
    }

}
