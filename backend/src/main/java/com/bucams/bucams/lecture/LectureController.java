package com.bucams.bucams.lecture;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bucams.bucams.lecture.domain.dto.LectureCreateRequestDto;
import com.bucams.bucams.lecture.domain.dto.LectureUpdateRequestDto;
import com.bucams.bucams.lecture.domain.dto.LectureResponseDto;
import com.bucams.bucams.lecture.domain.dto.RegisterLectureResponseDto;
import com.bucams.bucams.lecture.domain.entity.SearchOption;
import com.bucams.bucams.lecture.domain.service.LectureService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/lectures")
public class LectureController {

	private final LectureService lectureService;

	///  교수님 용
	// 강의 개설
	@Operation(summary = "강의 등록", description = "강의 등록 ")
	@PostMapping
	public ResponseEntity<LectureResponseDto> createLecture(
		@RequestBody @Valid LectureCreateRequestDto lectureRequestDto) {


		LectureResponseDto lectureResponseDto = lectureService.createLecture( lectureRequestDto);
		return ResponseEntity.ok(lectureResponseDto);
	}

	// 강의 수정
	@Operation(summary = "강의 수정", description = "강의 수정")
	@PostMapping("/update")
	public ResponseEntity<LectureResponseDto> updateLecture(@RequestBody @Valid LectureUpdateRequestDto lectureRequestDto ){


		LectureResponseDto lectureResponseDto = lectureService.updateLecture(lectureRequestDto);

		return ResponseEntity.ok(lectureResponseDto);
	}

	// 강의 삭제
	@Operation(summary = "강의 삭제", description = "강의 삭제")
	@DeleteMapping("/{lecture-id}")
	public ResponseEntity<String> deleteLecture(	@RequestParam Long memberId,
													@PathVariable("lecture-id") Long lectureId) {


		lectureService.deleteLecture(memberId, lectureId);

		return ResponseEntity.status(HttpStatus.OK).body("강의가 삭제되었습니다.");
	}


	// 내가 만든 강의 조회
	@Operation(summary = "내가 만든 강의 조회", description = "내가 만든 강의 조회")
	@GetMapping("/myLectures")
	public ResponseEntity<List<LectureResponseDto>> getMyLectures(@RequestParam Long memberId){

		List<LectureResponseDto> myAllLectures = lectureService.getMyAllLectures(memberId);

		return ResponseEntity.ok(myAllLectures);
	}


	/// 학생용
	// 수강신청
	@Operation(summary = "수강신청", description = "수강신청")
	@PostMapping("/registration/{lecture-id}")
	public ResponseEntity<RegisterLectureResponseDto> registerLecture(@PathVariable("lecture-id") Long lectureId, @RequestParam Long studentId){
		// 수강신청
		RegisterLectureResponseDto registerLectureResponseDto = lectureService.registerLecture(studentId, lectureId);

		return ResponseEntity.ok(registerLectureResponseDto);
	}



	/// 모든 강의 조회(누구나) + 검색
	// 교수별 강의 검색
	// 학과별 강의 검색
	// 전공별 강의 검색
	// 교양별 강의 검색
	// 강의명 강의 검색
	@Operation(summary = "모든 강의 조회", description = "모든 강의 조회")
	@GetMapping("/search")
	public ResponseEntity<List<LectureResponseDto>> searchAllLectures(@RequestParam(required = false) String keyword,
																	@RequestParam(required = false) SearchOption searchOption) {

		List<LectureResponseDto> allLectures = lectureService.getAllLectures(keyword, searchOption);

		return ResponseEntity.ok(allLectures);
	}

	// 강의 단건 조회(로그인한 사람)
	@Operation(summary = "강의 단건 조회", description = "강의 단건 조회")
	@GetMapping("/{lecture-id}")
	public ResponseEntity<LectureResponseDto> getLecture(@PathVariable("lecture-id") Long lectureId,
															@RequestParam Long memberId) {


		LectureResponseDto lecture = lectureService.getLecture(lectureId, memberId);

		return ResponseEntity.ok(lecture);
	}


}
