package com.example.SJSU_Event.domain.member.service;

import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.entity.Role;
import com.example.SJSU_Event.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    @Transactional
    @Override
    public Long userRegister(String name) {
        Member member = Member.of(String.valueOf(UUID.randomUUID()), name, Role.USER);
        return memberRepository.save(member).getId();
    }
    @Transactional
    @Override
    public Long adminRegister(String name) {
        Member member = Member.of(String.valueOf(UUID.randomUUID()), name, Role.USER);
        return memberRepository.save(member).getId();
    }

    @Transactional
    @Override
    public Long updateInfo(Long memberId, String name) {
        Member member = Member.of(String.valueOf(UUID.randomUUID()), name, Role.USER);
        member.modifyInfo(name);
        return member.getId();
    }

    @Transactional
    @Override
    public void deleteMember(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);

        if (memberOptional.isPresent()) {
            memberRepository.delete(memberId);
        } else {
            throw new RuntimeException("Member not found with username: " + memberId);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Member getByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with username: " + memberId));
    }
}
