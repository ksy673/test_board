package com.study.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.board.entity.Member;
import com.study.board.service.MemberService;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/login")
    public String openLogin(){

        return "login";
    }

    @PostMapping("/loginCheck")
    public String login(@ModelAttribute Member member, HttpSession session){
       Member loginResult = memberService.login(member);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "redirect:/board/list";
        } else {
            // login 실패
            return "login";
        }
    }

    @GetMapping("/signup")
    public String openSignup(){

        return "signup";
    }
     @PostMapping("/member/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
//        if (checkResult != null) {
//            return "ok";
//        } else {
//            return "no";
//        }
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute Member member) {
        System.out.println("MemberController.save");
        System.out.println("member = " + member);

        memberService.save(member);
        return "login";
    }

}
