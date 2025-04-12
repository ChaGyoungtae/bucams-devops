package com.bucams.bucams.registration;

//import com.bucams.bucams.registration.dto.RegistrationCreateDto;
//import com.bucams.bucams.registration.dto.RegistrationCreateResponseDto;
import com.bucams.bucams.member.MemberRepository;
import com.bucams.bucams.member.MemberService;
import com.bucams.bucams.registration.domain.Registration;
import com.bucams.bucams.registration.dto.AllRegistrationResponseDto;
import com.bucams.bucams.registration.dto.RegistrationRequestDto;
import com.bucams.bucams.registration.dto.RegistrationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RegistrationRepository {
    private final EntityManager em;
    private final MemberService memberService;
    private final MemberRepository memberRepository;


    // 수강 신청 내역 생성
    public Long create(Registration registration) {
        em.persist(registration);
        return registration.getId();
    }

    // 자신의 수강 신청 내역 조회
    public List<RegistrationResponseDto> findRegistrationRequestDtoByMemberId(Long memberId) {
        return em.createQuery(
                        "SELECT new com.bucams.bucams.registration.dto.RegistrationResponseDto(" +
                                "r.id," +
                                " r.member.id," +
                                " r.registeredAt, " +
                                " r.lecture.id," +
                                " r.lecture.name," +
                                " r.lecture.member.name," +
                                " r.lecture.schedule, " +
                                " r.lecture.type, " +
                                " r.lecture.credit) " +
                                " FROM Registration r WHERE r.member.id = :memberId", RegistrationResponseDto.class)
                .setParameter("memberId", memberId)
                .getResultList();

    }

    // 수강신청 삭제
    public void delete(Registration findRegistration) {
        em.remove(findRegistration);
    }

    public Registration findById(Long registrationId) {
        return em.find(Registration.class, registrationId);
    }
//    public Optional<Registration> findById(Long registrationId) {
//        return Optional.ofNullable(em.find(Registration.class, registrationId));
//    }


    // 전체 수강신청 내역 조회 ver.1
//    public List<RegistrationRequestDto> findRegistrationRequestDtoByRegistrationId(Long registrationId) {
//        return em.createQuery(
//                        "SELECT new com.bucams.bucams.registration.dto.RegistrationRequestDto(" +
//                                "r.id, r.member.id, r.lecture.id, r.registeredAt) " +
//                                " FROM Registration r WHERE r.id = :registrationId", RegistrationRequestDto.class)
//                .setParameter("registrationId", registrationId)
//                .getResultList();
//    }





    public List<AllRegistrationResponseDto> findAllRegistrations() {

            return em.createQuery(
                    "SELECT new com.bucams.bucams.registration.dto.AllRegistrationResponseDto(" +
                            "r.id, " +
                            "r.member.id, " +
                            "r.registeredAt, " +
                            "r.lecture.id, " +
                            "m.name, " +
                            "r.lecture.name, " +
                            "r.lecture.member.name, " +
                            "r.lecture.schedule, " +
                            "r.lecture.type, " +
                            "r.lecture.credit) " +
                    "FROM Registration r " +
                    "JOIN r.member m", AllRegistrationResponseDto.class)
            .getResultList();
    }

    public List<Registration> findByMemberId(Long studentId) {
        return em.createQuery("select r " +
                "from Registration r " +
                "where r.member.id =: memberId",Registration.class)
                .setParameter("memberId",studentId)
                .getResultList();
    }

    public Long save(Registration registration){
        em.persist(registration);

        return registration.getId();
    }
}
