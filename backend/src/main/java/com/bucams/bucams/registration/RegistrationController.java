package com.bucams.bucams.registration;

import com.bucams.bucams.registration.dto.AllRegistrationResponseDto;
import com.bucams.bucams.registration.dto.RegistrationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    // 자신의 수강신청 내역 조회
    @GetMapping("/{member-id}")
    public ResponseEntity<List<RegistrationResponseDto>> findByPlaceId(@PathVariable("member-id") Long memberId) {
        List<RegistrationResponseDto> registrationResponseDtos = registrationService.findByMemberId(memberId);

        return ResponseEntity.ok().body(registrationResponseDtos);

    }

    // 전체 수강신청 내역 조회
//    perp
    @GetMapping
    public ResponseEntity<List<AllRegistrationResponseDto>> findAllRegistrations() {
        List<AllRegistrationResponseDto> registrations = registrationService.findAllRegistrations();
        return ResponseEntity.ok().body(registrations);
    }

    // 수강신청 취소
    @DeleteMapping("/{registration-id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable("registration-id") Long registrationId) {
        registrationService.deleteRegistration(registrationId);
        return ResponseEntity.ok().build();
    }
}
