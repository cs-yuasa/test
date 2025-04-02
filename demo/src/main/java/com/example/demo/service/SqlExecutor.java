package com.example.demo.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.example.demo.dto.GreetingUserDTO;

@Component
public class SqlExecutor {
    private final JdbcTemplate jdbcTemplate;

    public SqlExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<GreetingUserDTO> getGreetingsWithUsers() {
        try {
            String sql = StreamUtils.copyToString(
                new ClassPathResource("query.sql").getInputStream(),
                StandardCharsets.UTF_8
            );
            return jdbcTemplate.query(sql, (rs, rowNum) -> new GreetingUserDTO(
                rs.getLong("id"),
                rs.getString("message"),
                rs.getString("name")
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
