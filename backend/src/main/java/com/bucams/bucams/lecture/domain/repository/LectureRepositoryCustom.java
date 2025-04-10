package com.bucams.bucams.lecture.domain.repository;

import java.util.List;

import com.bucams.bucams.lecture.domain.dto.LectureResponseDto;
import com.bucams.bucams.lecture.domain.entity.SearchOption;


public interface LectureRepositoryCustom {
	List<LectureResponseDto> searchAllLectures(String keyword, SearchOption searchOption);
}
