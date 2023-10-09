package com.lotdiz.memberservice.controller.restcontroller;

import com.lotdiz.memberservice.dto.MemberInfoForSignUpRequestDto;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member-service")
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<Member> signup(@Valid @RequestBody MemberInfoForSignUpRequestDto memberInfoForSignUpRequestDto) {

        return ResponseEntity.ok(memberService.signup(memberInfoForSignUpRequestDto));

    }

    @GetMapping("/member")
    @PreAuthorize("hasAnyRole('ROLE_SUPPORTER', 'ROLE_MAKER')")
    public ResponseEntity<Member> getMyUserInfo() {
        return ResponseEntity.ok(memberService.getMyMemberWithAuthorities().get());
    }

    @GetMapping("/member/{memberEmail}")
    @PreAuthorize("hasAnyRole('ROLE_MAKER')")
    public ResponseEntity<Member> getUserInfo(@PathVariable String memberEmail) {
        return ResponseEntity.ok(memberService.getMemberWithAuthorities(memberEmail).get());
    }

}
