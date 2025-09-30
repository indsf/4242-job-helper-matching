package com.test.Member.service;

import com.test.Member.dto.JoinDto;
import com.test.Member.entity.Member;
import com.test.Member.exception.MemberErrorCode;
import com.test.Member.repository.MemberRepository;
import com.test.common.exception.BussinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /** 회원가입 후 Member 반환 */
    @Transactional
    public Member signup(JoinDto joinDto) {
        if (memberRepository.existsByEmail(joinDto.getEmail())) {
            throw new BussinessException(MemberErrorCode.MEMBER_EMAIL_ALREADY_EXISTS);
        }
        if (memberRepository.existsByNickname(joinDto.getNickname())) {
            throw new BussinessException(MemberErrorCode.MEMBER_NICKNAME_ALREADY_EXISTS);
        }

        Member member = Member.createMember(joinDto);
        return memberRepository.saveAndFlush(member);
    }

    /** 로그인 후 Member 반환 */
    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return member;
    }

    /** ID로 회원 조회, 없으면 예외 */
    @Transactional(readOnly = true)
    public Member findMemberIdOrExe(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BussinessException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public Member updateNickname(String oldNickname, String newNickname) {
        Member member = memberRepository.findByNickname(oldNickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 닉네임의 회원이 존재하지 않습니다."));

        member.setNickname(newNickname);
        return memberRepository.save(member);
    }


    public String findEmailByPassword(String password) {
        return memberRepository.findByPassword(password)
                .map(Member::getEmail)
                .orElse(null);
    }

    public String findPasswordByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(Member::getPassword)
                .orElse(null);
    }
}
