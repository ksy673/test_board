package com.study.board.service;

import java.util.Optional;

import com.study.board.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.board.entity.Member;
import com.study.board.repository.MemberRepository;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    // 로그인 처리
    public MemberDTO login(MemberDTO memberDTO) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회를 함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<Member> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());

        if (byMemberEmail.isPresent()) {
            Member member = byMemberEmail.get();
            if (member.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                // 비밀번호 일치
                // entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(member);
                return dto;
            }  else {
            // 비밀번호 불일치(로그인실패)
            return null;
             }
        } else {
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }
    }

    // 회원가입 이메일 기능 체크 처리
    public String emailCheck(String memberEmail) {
        Optional<Member> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if (byMemberEmail.isPresent()) {
            // 조회결과가 있다 -> 사용할 수 없다.
            return "duplicate";
        } else {
            // 조회결과가 없다 -> 사용할 수 있다.
            return "ok";
        }
    }

    //회원가입 기능처리
    public void save(MemberDTO memberDTO) {

        // 1. dto -> entity 변환
        // 2. repository의 save 메서드 호출
        Member member = Member.toMemberEntity(memberDTO);
        memberRepository.save(member);
        // repository의 save메서드 호출 (조건. entity객체를 넘겨줘야 함)
    }
}
