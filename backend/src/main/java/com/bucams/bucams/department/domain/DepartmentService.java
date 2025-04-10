package com.bucams.bucams.department.domain;

import com.bucams.bucams.department.domain.dto.ResponseDepartmentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<ResponseDepartmentDto> findAllDepartments() {
        return departmentRepository.findAllBy();
    }
}
