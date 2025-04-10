package com.bucams.bucams.lecture.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bucams.bucams.lecture.domain.entity.Lecture;


public interface LectureRepository extends JpaRepository<Lecture,Long>, LectureRepositoryCustom {

	// 해당 강의가 교수님이 개설한 강의인지 확인
	Optional<Lecture> findByIdAndMemberId(Long lectureId, Long memberId);

	// 해당 교수님이 개설한 모든 강의 조회
	List<Lecture> findAllByMemberId(Long memberId);


}
