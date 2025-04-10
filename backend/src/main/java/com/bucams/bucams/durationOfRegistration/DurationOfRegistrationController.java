package com.bucams.bucams.durationOfRegistration;

import com.bucams.bucams.durationOfRegistration.domain.DurationOfRegistrationService;
import com.bucams.bucams.durationOfRegistration.domain.dto.RequestDorDto;
import com.bucams.bucams.durationOfRegistration.domain.dto.ResponseDorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/duration")
@CrossOrigin(origins = "http://localhost:5173")
public class DurationOfRegistrationController {

    private final DurationOfRegistrationService durationOfRegistrationService;

    // 조회
    @GetMapping
    public ResponseEntity<ResponseDorDto> showDor(){
        return ResponseEntity.ok().body(durationOfRegistrationService.showDorDto());
    }


    // 생성
    @PostMapping
    public ResponseEntity<Long> newDor(@RequestBody RequestDorDto requestDorDto){
        return ResponseEntity.ok().body(durationOfRegistrationService.newDor(requestDorDto));
    }


    // 삭제
    @DeleteMapping("/{dor-id}")
    public ResponseEntity<Long> deleteDor(@PathVariable("dor-id") Long dorId){
        return ResponseEntity.ok().body(durationOfRegistrationService.deleteDor(dorId));
    }
}
