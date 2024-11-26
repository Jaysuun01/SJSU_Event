package com.example.SJSU_Event.domain.member.repository;

import com.example.SJSU_Event.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);
}
