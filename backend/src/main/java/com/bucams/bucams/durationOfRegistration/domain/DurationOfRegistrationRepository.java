package com.bucams.bucams.durationOfRegistration.domain;

import com.bucams.bucams.durationOfRegistration.domain.dto.ResponseDorDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DurationOfRegistrationRepository {

    private final EntityManager em;

    public ResponseDorDto findDorDto(){
        return em.createQuery("select new com.bucams.bucams.durationOfRegistration.domain.dto.ResponseDorDto(d.id,d.startDate,d.endDate) " +
                "from DurationOfRegistration d", ResponseDorDto.class).getSingleResult();
    }

    public Long createDor(DurationOfRegistration durationOfRegistration){
        em.persist(durationOfRegistration);

        return durationOfRegistration.getId();
    }

    public DurationOfRegistration findOne(Long dorId){
        return em.find(DurationOfRegistration.class,dorId);
    }

    public void deleteDor(DurationOfRegistration durationOfRegistration) {
        em.remove(durationOfRegistration);
    }

    public List<DurationOfRegistration> findAll(){
        return em.createQuery("select d " +
                "from DurationOfRegistration d ", DurationOfRegistration.class).getResultList();
    }
}
