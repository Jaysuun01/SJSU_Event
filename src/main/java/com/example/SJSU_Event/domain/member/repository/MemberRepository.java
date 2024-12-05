package com.example.SJSU_Event.domain.member.repository;

import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.entity.Role;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // findByUsername 메서드 추가
    public Optional<Member> findById(Long memberId) {
        String sql = "SELECT * FROM member WHERE member_id = ?";
        List<Member> members = jdbcTemplate.query(sql,
                new RowMapper<Member>() {
                    @Override
                    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return Member.builder()
                                .id(rs.getLong("member_id"))
                                .name(rs.getString("name"))
                                .role(Role.valueOf(rs.getString("role")))
                                .createdDate(Optional.ofNullable(rs.getTimestamp("created_date"))
                                        .map(timestamp -> timestamp.toLocalDateTime())
                                        .orElse(null))
                                .lastModifiedDate(Optional.ofNullable(rs.getTimestamp("last_modified_date"))
                                        .map(timestamp -> timestamp.toLocalDateTime())
                                        .orElse(null))
                                .username(rs.getString("username"))
                                .build();
                    }
                },
                memberId);
        return members.isEmpty() ? Optional.empty() : Optional.of(members.get(0));
    }


    public Member save(Member member) {
        String sql = "INSERT INTO member (name, role, username) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new
                    String[]{"id"});
            ps.setString(1, member.getName());
            ps.setString(2, member.getRole().getKey());
            ps.setString(3, member.getUsername());
            return ps;
        }, keyHolder);
        long key = keyHolder.getKey().longValue();
        member.setId(key);
        return member;
    }
    public void delete(Long memberId) {
        String sql = "DELETE FROM member WHERE member_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, memberId);

        if (rowsAffected == 0) {
            throw new RuntimeException("No member found with username: " + memberId);
        }
    }
}

