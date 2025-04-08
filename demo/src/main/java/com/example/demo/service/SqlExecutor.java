package com.example.demo.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.example.demo.dto.GreetingUserDTO;

/**
 * SQLファイルを読み込み、JDBCを使ってクエリを実行する補助クラス。
 */
@Component
public class SqlExecutor {
    private final JdbcTemplate jdbcTemplate;

    /**
     * SqlExecutorのコンストラクタ。
     *
     * @param jdbcTemplate SQL実行に使用するJdbcTemplate
     */
    public SqlExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * クラスパス上のSQLファイルを読み込んで実行し、
     * 結果をGreetingUserDTOのリストとして返します。
     *
     * @return クエリ結果のGreetingUserDTOのリスト
     */
    public List<GreetingUserDTO> getGreetingsWithUsers() {
        try {
            String sql = StreamUtils.copyToString(
                new ClassPathResource("query.sql").getInputStream(),
                StandardCharsets.UTF_8
            );
            return jdbcTemplate.query(sql, (rs, rowNum) -> new GreetingUserDTO(
                rs.getLong("id"),
                rs.getString("message"),
                rs.getString("memo"),
                rs.getString("name")
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
