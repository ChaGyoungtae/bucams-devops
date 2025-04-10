package com.bucams.bucams.department.domain;

import com.bucams.bucams.department.domain.dto.ResponseDepartmentDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DepartmentRepository {

    private final EntityManager em;

    public List<ResponseDepartmentDto> findAllBy() {
        return em.createQuery("select new com.bucams.bucams.department.domain.dto.ResponseDepartmentDto(d.id, d.name) " +
                "from Department d", ResponseDepartmentDto.class).getResultList();
    }

    public Optional<Department> findById(long departmentId){
        return Optional.ofNullable(em.find(Department.class,departmentId));
    }
}
