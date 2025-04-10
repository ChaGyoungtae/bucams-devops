package com.bucams.bucams.lecture.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bucams.bucams.lecture.domain.dto.LectureCreateRequestDto;
import com.bucams.bucams.lecture.domain.dto.LectureUpdateRequestDto;
import com.bucams.bucams.lecture.domain.dto.LectureResponseDto;
import com.bucams.bucams.lecture.domain.dto.RegisterLectureResponseDto;
import com.bucams.bucams.lecture.domain.entity.SearchOption;

@Service
public interface LectureService {

	///  교수님 용
	// 강의 개설
	LectureResponseDto createLecture(LectureCreateRequestDto lectureRequestDto);

	// 강의 수정
	LectureResponseDto updateLecture(LectureUpdateRequestDto lectureRequestDto);

	// 강의 삭제
	void deleteLecture(Long memberId, Long lectureId);

	// 내가 만든 강의 조회
	List<LectureResponseDto> getMyAllLectures(Long memberId);
	
	// 강의 단건 조회
	LectureResponseDto getLecture(Long memberId, Long lectureId);


	// 강의 전체 조회 검색
	List<LectureResponseDto> getAllLectures( String keyword, SearchOption searchOption);

	// 수강신청
	RegisterLectureResponseDto registerLecture(Long studentId, Long lectureId);

	/// 학생용
	// 모든 강의 조회(누구나) + 조회
	// 교수별 강의 검색
	// 학과별 강의 검색
	// 전공별 강의 검색
	// 교양별 강의 검색
	// 강의명 강의 검색
}

