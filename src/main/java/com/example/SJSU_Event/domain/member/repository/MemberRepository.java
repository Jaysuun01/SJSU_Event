package com.example.SJSU_Event.domain.member.repository;

import com.example.SJSU_Event.domain.member.dto.MemberUpdateInfo;
import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.entity.Role;
import com.example.SJSU_Event.domain.member.exception.MemberHandler;
import com.example.SJSU_Event.global.exception.code.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Find member by ID
     * @param memberId ID of the member to find
     * @return Optional containing the member if found, empty Optional otherwise
     */
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
                                .password(rs.getString("password"))
                                .build();
                    }
                },
                memberId);
        return members.isEmpty() ? Optional.empty() : Optional.of(members.get(0));
    }

    /**
     * Save a new member
     * @param member Member entity to save
     * @return Saved member with generated ID
     */
    public Member save(Member member) {
        String sql = "INSERT INTO member (name, role, username, password, created_date, last_modified_date) values (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, member.getName());
            ps.setString(2, member.getRole().getKey());
            ps.setString(3, member.getUsername());
            ps.setString(4, member.getPassword());
            ps.setTimestamp(5, Timestamp.valueOf(now));
            ps.setTimestamp(6, Timestamp.valueOf(now));
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        member.setId(key);
        return member;
    }
    /**
     * Delete member by ID
     * @param memberId ID of the member to delete
     * @throws RuntimeException if member not found
     */
    public void delete(Long memberId) {
        String sql = "DELETE FROM member WHERE member_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, memberId);

        if (rowsAffected == 0) {
            throw new RuntimeException("No member found with username: " + memberId);
        }
    }
    /**
     * Find member by username
     * @param username Username to search for
     * @return Optional containing the member
     */
    public Optional<Member> findByUsername(String username) {
        String sql = "SELECT * FROM member WHERE username = ?";
        log.info("username = {}", username);
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
                                .password(rs.getString("password"))
                                .build();
                    }
                },
                username);
        return members.isEmpty() ? Optional.empty() : Optional.of(members.get(0));
    }
    /**
     * Update member information
     * @param memberId ID of the member to update
     * @param memberUpdateInfo Updated member information
     * @return Updated member entity
     * @throws MemberHandler if member not found
     */
    public Member update(Long memberId, MemberUpdateInfo memberUpdateInfo) {
        String sql = "UPDATE member " +
                     "SET name = ?, username = ?, last_modified_date = CURRENT_TIMESTAMP " +
                     "WHERE member_id = ?";

        int rowsAffected = jdbcTemplate.update(sql,
                memberUpdateInfo.getName(),
                memberUpdateInfo.getUsername(),
                memberId);

        if (rowsAffected == 0) {
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }

        return findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }
}

