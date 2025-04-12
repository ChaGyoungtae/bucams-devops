package com.bucams.bucams.config.swagger;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final Environment environment;
    private final DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String ddlAuto = environment.getProperty("spring.jpa.hibernate.ddl-auto");

        if ("create".equalsIgnoreCase(ddlAuto)) {
            System.out.println("✅ ddl-auto가 create이므로 data.sql을 수동 실행합니다.");

            try (Connection conn = dataSource.getConnection()) {
                Resource resourceDepartment = new ClassPathResource("department.sql");
                String sqlDepartment = new String(resourceDepartment.getInputStream().readAllBytes());

                for (String statement : sqlDepartment.split(";")) {
                    if (!statement.trim().isEmpty()) {
                        try (Statement stmt = conn.createStatement()) {
                            stmt.execute(statement.trim());
                        }
                    }
                }

                System.out.println("✅ department.sql 실행 완료");

                Resource resourceMember = new ClassPathResource("member.sql");
                String sqlMember = new String(resourceMember.getInputStream().readAllBytes());

                for (String statement : sqlMember.split(";")) {
                    if (!statement.trim().isEmpty()) {
                        try (Statement stmt = conn.createStatement()) {
                            stmt.execute(statement.trim());
                        }
                    }
                }

                System.out.println("✅ member.sql 실행 완료");

                Resource resourceLecture = new ClassPathResource("lecture.sql");
                String sqlLecture = new String(resourceLecture.getInputStream().readAllBytes());

                for (String statement : sqlLecture.split(";")) {
                    if (!statement.trim().isEmpty()) {
                        try (Statement stmt = conn.createStatement()) {
                            stmt.execute(statement.trim());
                        }
                    }
                }

                System.out.println("✅ lecture.sql 실행 완료");



            }
        } else {
            System.out.println("❌ ddl-auto가 create이 아님. data.sql 실행 생략");
        }
    }
}
