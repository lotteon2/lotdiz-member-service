package com.lotdiz.memberservice.controller.restcontroller;

import com.lotdiz.memberservice.dto.request.MemberInfoForSignUpRequestDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.dto.request.MemberInfoForChangeRequestDto;
import com.lotdiz.memberservice.dto.response.MemberInfoForQueryResponseDto;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.service.MemberService;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberRestController {
  private final Logger logger = LoggerFactory.getLogger(MemberRestController.class);

  private final MemberService memberService;

  @PostMapping("/sign-up")
  public ResponseEntity<Member> signup(
      @Valid @RequestBody MemberInfoForSignUpRequestDto memberInfoForSignUpRequestDto) {
    logger.info("signup here!!");
    return ResponseEntity.ok(memberService.signup(memberInfoForSignUpRequestDto));
  }

  @GetMapping("/members")
  public ResultDataResponse<MemberInfoForQueryResponseDto> showMember(@RequestHeader Long memberId) {
    return new ResultDataResponse<>(
        String.valueOf(HttpServletResponse.SC_OK),
        HttpStatus.OK.name(),
        "회원 정보 조회 성공",
        memberService.showMember(memberId)
    );
  }

  @PutMapping("/members")
  public ResultDataResponse<Object> renewMember(@RequestHeader Long memberId, @Valid @RequestBody MemberInfoForChangeRequestDto memberInfoForChangeRequestDto) {
    Member member = memberService.findMemberByMemberId(memberId);
    memberService.renew(member.getMemberEmail(), memberInfoForChangeRequestDto);
    return new ResultDataResponse<>(
        String.valueOf(HttpStatus.OK.value()),
        HttpStatus.OK.toString(),
        "회원 정보 수정 성공",
        null
    );
  }

  @GetMapping("/tests")
  public String members() {
    return "<h1>members</h1>";
  }

  @PostMapping("/token")
  public String token() {
    return "<h1>token</h1>";
  }

  // user, manager, admin 다 접근가능
  @GetMapping("/v1/user")
  public String user() {
    return "user";
  }

  // manager, admin만 접근가능
  @GetMapping("/v1/manager")
  public String manager() {
    return "manager";
  }

  // admin만 접근가능
  @GetMapping("/v1/adimin")
  public String admin() {
    return "admin";
  }
}
