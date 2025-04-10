package com.bucams.bucams.department;

import com.bucams.bucams.department.domain.DepartmentService;
import com.bucams.bucams.department.domain.dto.ResponseDepartmentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/departments")
@CrossOrigin(origins = "http://localhost:5173")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<ResponseDepartmentDto>> showAllDepartment(){
        return ResponseEntity.ok().body(departmentService.findAllDepartments());
    }
}
