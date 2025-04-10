package com.bucams.bucams.lecture.domain.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.bucams.bucams.registration.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bucams.bucams.lecture.domain.dto.LectureCreateRequestDto;
import com.bucams.bucams.lecture.domain.dto.RegisterLectureResponseDto;
import com.bucams.bucams.lecture.domain.entity.Lecture;
import com.bucams.bucams.lecture.domain.dto.LectureUpdateRequestDto;
import com.bucams.bucams.lecture.domain.dto.LectureResponseDto;
import com.bucams.bucams.lecture.domain.entity.SearchOption;
import com.bucams.bucams.lecture.domain.repository.LectureRepository;
import com.bucams.bucams.member.MemberRepository;
import com.bucams.bucams.member.domain.Member;
import com.bucams.bucams.member.domain.Role;
import com.bucams.bucams.registration.domain.Registration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

	private final LectureRepository lectureRepository;
	private final MemberRepository memberRepository;
	private final RegistrationRepository registrationRepository;

	@Override
	@Transactional
	public LectureResponseDto createLecture(LectureCreateRequestDto lectureRequestDto) {


		Long memberId = lectureRequestDto.getProfessorId();
		// 1. member가 존재하는지 검증
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
		);

		// 2. member가 교수님인지 검증
		if(member.getRole() != Role.PROFESSOR){
			throw new IllegalArgumentException("해당 사용자는 교수님이 아닙니다.");
		}

		// DTO -> entity 로 변환
		Lecture lecture = Lecture.builder()
			.name(lectureRequestDto.getLectureName())
			.schedule(String.join( "," , lectureRequestDto.getSchedule()))
			.limitCount(lectureRequestDto.getLimitCount())
			.credit(lectureRequestDto.getCredit())
			.type(lectureRequestDto.getLectureType())
			.member(member)
			.build();

		// repository 에 entity 저장
		lectureRepository.save(lecture);

		// entity -> responseDto 로 변환 후 반환
		return new LectureResponseDto(lecture);

	}

	@Override
	@Transactional
	public LectureResponseDto updateLecture(LectureUpdateRequestDto lectureRequestDto) {



		// 1. user가 존재하는지 검증
		Long memberId = lectureRequestDto.getProfessorId();
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
		);

		// 2. member가 교수님인지 검증
		if(member.getRole() != Role.PROFESSOR){
			throw new IllegalArgumentException("해당 사용자는 교수님이 아닙니다.");
		}


		Long lectureId = lectureRequestDto.getLectureId();
		// 3. lecture가 존재하는지 검증
		Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
			() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다.")
		);


		// 4. lecture가 user가 개설한 강의가 먼지 검증
		lectureRepository.findByIdAndMemberId(lectureId, memberId).orElseThrow(
			() -> new IllegalArgumentException("해당 강의는 사용자가 개설한 강의가 아닙니다.")
		);


		// 5. lecture 수정
		lecture.update(lectureRequestDto);

		return new LectureResponseDto(lecture);
	}



	@Override
	@Transactional
	public void deleteLecture(Long memberId, Long lectureId){

		// 1. lecture 존재하는지 검증
		Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
			() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다.")
		);

		// 2. member 존재하는지 검증
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
		);

		// 3. member가 교수님인지 검증
		if(member.getRole() != Role.PROFESSOR){
			throw new IllegalArgumentException("해당 사용자는 교수님이 아닙니다.");
		}


		// 4. 해당 lecture 가 member 가 개설한 강의인지 검증
		lectureRepository.findByIdAndMemberId(lectureId, memberId).orElseThrow(
			() -> new IllegalArgumentException("해당 강의는 사용자가 개설한 강의가 아닙니다.")
		);


		// 5. lecture 삭제
		lectureRepository.deleteById(lectureId);
	}

	// 교수님이 본인이 개설한 모든 강의 조회
	@Override
	public List<LectureResponseDto> getMyAllLectures(Long memberId) {

		// 1. member 존재하는지 검증
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
		);


		// 2. 해당 member가  교수가 맞는지 검증
		if(member.getRole() != Role.PROFESSOR){
			throw new IllegalArgumentException("해당 사용자는 교수님이 아닙니다.");
		}

		// 3. member가 개설한 강의 조회
		List<Lecture> allLecture = lectureRepository.findAllByMemberId(memberId);

		if(allLecture.isEmpty()){
			throw new IllegalArgumentException("개설한 강의가 없습니다.");
		};

		return allLecture.stream()
			.map(LectureResponseDto::new)
			.collect(Collectors.toList());
	}

	// 강의 단건 조회
	@Override
	public LectureResponseDto getLecture(Long memberId, Long lectureId) {

		// 1. 로그인한 유저만 조회 가능
		memberRepository.findById(memberId).orElseThrow(
			() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
		);

		// 2. lecture가 존재하는지 검증
		Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(
			() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다.")
		);


		// 3. lecture -> responseDto 변환
		return new LectureResponseDto(lecture);
	}


	// 강의 전체 조회, 검색
	@Override
	public List<LectureResponseDto> getAllLectures(String keyword, SearchOption searchOption) {

		// 검색어가 있는데 검색 옵션을 선택하지 않은 경우 검색이 안됨
		if (keyword != null && searchOption == null) {
			throw new IllegalArgumentException("검색 옵션을 선택해 주십시오.");
		}

		List<LectureResponseDto> searchAllLectures = lectureRepository.searchAllLectures(keyword, searchOption);


		if(searchAllLectures.isEmpty()){
			throw new IllegalArgumentException("강의가 없습니다.");
		}

		return searchAllLectures;
	}

	@Override
	@Transactional
	public RegisterLectureResponseDto registerLecture(Long studentId, Long lectureId) {
		// 1. 학생 존재 확인
		Member student = memberRepository.findById(studentId)
			.orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

		// 2. 역할 확인
		if (student.getRole() != Role.STUDENT) {
			throw new IllegalArgumentException("해당 사용자는 학생이 아닙니다.");
		}

		// 3. 강의 존재 확인
		Lecture lecture = lectureRepository.findById(lectureId)
			.orElseThrow(() -> new IllegalArgumentException("해당 강의가 존재하지 않습니다."));

		// 4. 시간표 중복 체크 (방어 로직 포함)
		List<Registration> registrationList = registrationRepository.findByMemberId(studentId);
		String[] wantSchedule = Arrays.stream(lecture.getSchedule().split(","))
			.map(String::trim)
			.filter(s -> !s.isEmpty())
			.toArray(String[]::new);

		for (Registration registration : registrationList) {
			String[] existSchedule = Arrays.stream(registration.getLecture().getSchedule().split(","))
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.toArray(String[]::new);

			for (String exist : existSchedule) {
				for (String want : wantSchedule) {
					if (isScheduleConflict(exist, want)) {
						throw new IllegalArgumentException("이미 해당 시간에 수강 중인 강의가 있어 수강 신청이 불가능합니다.");
					}
				}
			}
		}

		// 5. 수강 인원 초과 확인
		if (lecture.getCurrCount() >= lecture.getLimitCount()) {
			throw new IllegalArgumentException("해당 강의는 수강신청이 마감되었습니다.");
		}

		// 6. 학점 초과 확인
		if (student.getCurrentCredits() + lecture.getCredit() > 21) {
			throw new IllegalArgumentException("수강신청 학점이 21학점을 초과했습니다.");
		}

		// 7. 교수 및 학과 정보 확인
		if (lecture.getMember() == null) {
			throw new IllegalStateException("해당 강의에 등록된 교수 정보가 없습니다.");
		}
		if (lecture.getMember().getDepartment() == null) {
			throw new IllegalStateException("교수님에게 학과 정보가 등록되어 있지 않습니다.");
		}

		// 8. 수강 신청 처리
		lecture.addCurrCount();
		student.addCurrentCredits(lecture.getCredit());

		registrationRepository.save(Registration.builder()
			.lecture(lecture)
			.member(student)
			.registeredAt(LocalDateTime.now())
			.build());

		return new RegisterLectureResponseDto(lecture, student);
	}


	/**
	 * 시간표 문자열 두 개가 겹치는지 검사
	 * 예: "수 2-5, 목 7-9" 와 "수 4-6, 금 11-1" → true
	 */
	private boolean isScheduleConflict(String s1, String s2) {
		try {
			if (s1 == null || s2 == null || s1.trim().isEmpty() || s2.trim().isEmpty()) {
				return false; // 공백 또는 null이면 겹치지 않은 것으로 처리
			}

			String day1 = s1.substring(0, 1); // 예: "수"
			String[] times1 = s1.substring(2).split("-");

			String day2 = s2.substring(0, 1); // 예: "수"
			String[] times2 = s2.substring(2).split("-");

			int start1 = Integer.parseInt(times1[0]);
			int end1 = Integer.parseInt(times1[1]);
			int start2 = Integer.parseInt(times2[0]);
			int end2 = Integer.parseInt(times2[1]);

			if (!day1.equals(day2)) return false;

			return start1 <= end2 && start2 <= end1;

		} catch (Exception e) {
			log.error("시간표 파싱 중 오류 발생: s1={}, s2={}, message={}", s1, s2, e.getMessage());
			return false; // 오류 나면 겹치지 않은 것으로 처리
		}
	}

}

