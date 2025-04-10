package com.bucams.bucams.registration;

//import com.bucams.bucams.registration.dto.RegistrationCreateDto;
//import com.bucams.bucams.registration.dto.RegistrationCreateResponseDto;
import com.bucams.bucams.registration.dto.RegistrationRequestDto;
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

    // 수강신청 내역 생성
//    public ResponseEntity<Long> createRegistration(RegistrationRequestDto dto) {
//        Long registrationId = registrationService.createRegistration(dto);
//        return ResponseEntity.ok().body(registrationId);
//    }

//    @PostMapping
//    public ResponseEntity<RegistrationCreateResponseDto> createRegistration(@RequestBody RegistrationCreateDto dto) {
//        RegistrationCreateResponseDto responseDto = RegistrationService.createRegistration(dto);
//        return ResponseEntity.ok().body(responseDto);
//    }

    // 자신의 수강신청 내역 조회
    @GetMapping("/{member-id}")
    public ResponseEntity<List<RegistrationRequestDto>> findByPlaceId(@PathVariable("member-id") Long memberId) {
        List<RegistrationRequestDto> registrationRequestDto = registrationService.findByMemberId(memberId);

        return ResponseEntity.ok().body(registrationRequestDto);

    }

    // 전체 수강신청 내역 조회
//    perp
    @GetMapping
    public ResponseEntity<List<RegistrationRequestDto>> findAllRegistrations() {
        List<RegistrationRequestDto> registrations = registrationService.findAllRegistrations();
        return ResponseEntity.ok().body(registrations);
    }


//    ver.1
//    @GetMapping
//    public ResponseEntity<List<RegistrationRequestDto>> findByRegistrationId(@PathVariable("registration-id") Long registrationId) {
//        List<RegistrationRequestDto> registrationRequestDto = registrationService.findByregistrationId(registrationId);
//
//        return ResponseEntity.ok().body(registrationRequestDto);
//
//    }

    // 수강신청 취소
    @DeleteMapping("/{registration-id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable("registration-id") Long registrationId) {
        registrationService.deleteRegistration(registrationId);
        return ResponseEntity.ok().build();
    }
}
