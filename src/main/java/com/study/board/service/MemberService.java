package com.study.board.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.board.entity.Member;
import com.study.board.repository.MemberRepository;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Member login(Member member) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회를 함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<Member> byMemberEmail = memberRepository.findByMemberEmail(member.getMemberEmail());

        if (byMemberEmail.isPresent()) {
            Member findMember = byMemberEmail.get();
            if (findMember.getMemberPassword().equals(member.getMemberPassword())) {
                return findMember;
            }
        }
        return null;
        
    }

    public String emailCheck(String memberEmail) {
        Optional<Member> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if (byMemberEmail.isPresent()) {
            // 조회결과가 있다 -> 사용할 수 없다.
            return null;
        } else {
            // 조회결과가 없다 -> 사용할 수 있다.
            return "ok";
        }
    }

    public void save(Member member) {
        
        memberRepository.save(member);
        // repository의 save메서드 호출 (조건. entity객체를 넘겨줘야 함)
    }
}
