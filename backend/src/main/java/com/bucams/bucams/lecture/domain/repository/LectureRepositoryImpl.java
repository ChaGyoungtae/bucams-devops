package com.bucams.bucams.lecture.domain.repository;

import static com.bucams.bucams.department.domain.QDepartment.*;
import static com.bucams.bucams.lecture.domain.entity.QLecture.*;
import static com.bucams.bucams.member.domain.QMember.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bucams.bucams.lecture.domain.dto.LectureResponseDto;
import com.bucams.bucams.lecture.domain.entity.SearchOption;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;


@Repository
public class LectureRepositoryImpl implements LectureRepositoryCustom {


	private final JPAQueryFactory queryFactory;
	public LectureRepositoryImpl(EntityManager em){
		this.queryFactory = new JPAQueryFactory(em);
	}


	// 검색 조건
	private BooleanExpression searchConditions(String keyword, SearchOption searchOption) {
		if (keyword == null || keyword.isBlank() || searchOption == null) {
			return null; // 검색 조건 없이 전체 조회
		}

		return switch (searchOption) {
			case LECTURE_DEPARTMENT -> department.name.containsIgnoreCase(keyword);
			case LECTURE_PROFESSOR -> member.name.containsIgnoreCase(keyword);
			case LECTURE_NAME -> lecture.name.containsIgnoreCase(keyword);
			case LECTURE_TYPE -> lecture.type.stringValue().equalsIgnoreCase(keyword);
		};
	}

	// 게시글 검색 + 전체 조회
	@Override
	public List<LectureResponseDto> searchAllLectures(String keyword, SearchOption searchOption) {

		List<LectureResponseDto> content = queryFactory

			.select(Projections.constructor(LectureResponseDto.class,
				lecture.id,
				lecture.name,
				member.phone,
				member.email,
				member.name,
				member.department.name,
				lecture.schedule,
				lecture.limitCount,
				lecture.currCount,
				lecture.credit,
				lecture.type
			))
			.from(lecture)
			.leftJoin(lecture.member, member)
			.leftJoin(member.department, department)
			.where(
				searchConditions(keyword, searchOption)
			)
			.fetch();


		return content;

	}



}
