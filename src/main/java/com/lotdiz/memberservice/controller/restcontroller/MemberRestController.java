package com.lotdiz.memberservice.controller.restcontroller;

import com.lotdiz.memberservice.dto.MemberInfoForSignInRequestDto;
import com.lotdiz.memberservice.dto.MemberInfoForSignUpRequestDto;
import com.lotdiz.memberservice.dto.ResultDataResponseBody;
import com.lotdiz.memberservice.dto.TokenForAuthenticationResponseDto;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

//  @PostMapping("/sign-in")
//  public ResponseEntity<ResultDataResponseBody> signin(
//      @Valid @RequestBody MemberInfoForSignInRequestDto memberInfoForSignInRequestDto) {
//    logger.info("sign-in Controller Logic..");
//    memberService.signin(memberInfoForSignInRequestDto);
//    return ResponseEntity.ok(
//        new ResultDataResponseBody(
//            HttpStatus.OK.name(),
//            HttpStatus.OK.getReasonPhrase(),
//            "로그인 성공",
//            new TokenForAuthenticationResponseDto()));
//  }

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
