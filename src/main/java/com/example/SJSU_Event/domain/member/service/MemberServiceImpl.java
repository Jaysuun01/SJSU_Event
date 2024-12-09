package com.example.SJSU_Event.domain.member.service;

import com.example.SJSU_Event.domain.member.dto.LoginDto;
import com.example.SJSU_Event.domain.member.dto.MemberUpdateInfo;
import com.example.SJSU_Event.domain.member.dto.SignUpDto;
import com.example.SJSU_Event.domain.member.entity.Member;
import com.example.SJSU_Event.domain.member.entity.Role;
import com.example.SJSU_Event.domain.member.exception.MemberHandler;
import com.example.SJSU_Event.domain.member.repository.MemberRepository;
import com.example.SJSU_Event.global.exception.code.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long signUp(SignUpDto signUpDto) {
        Member save = memberRepository.save(Member.of(signUpDto));
        return save.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public Member login(LoginDto loginDto) {
        log.info("Attempting login for username: {}", loginDto.getUsername());
        Member member = memberRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        log.info("Found member: {}", member);
        validatePassword(loginDto, member);
        return member;
    }


    private static void validatePassword(LoginDto loginDto, Member member) {
        if (!member.getPassword().equals(loginDto.getPassword())) {
            throw new MemberHandler(ErrorStatus.MEMBER_WRONG_PASSWORD);
        }
    }

    @Transactional
    @Override
    public Long updateInfo(Long memberId, MemberUpdateInfo memberUpdateInfo) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        //check username use the same
        if (!existingMember.getUsername().equals(memberUpdateInfo.getUsername())) {
            memberRepository.findByUsername(memberUpdateInfo.getUsername())
                    .ifPresent(m -> {
                        throw new MemberHandler(ErrorStatus.MEMBER_USERNAME_ALREADY_EXISTS);
                    });
        }

        Member updatedMember = memberRepository.update(memberId, memberUpdateInfo);
        return updatedMember.getId();
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
