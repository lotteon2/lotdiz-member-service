package com.lotdiz.memberservice.controller.clientcontroller;

import com.lotdiz.memberservice.dto.response.MemberInfoForProjectResponseDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.service.MemberService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProjectClientController {
  private final MemberService memberService;

  @GetMapping("/members")
  public ResultDataResponse<Map<String, MemberInfoForProjectResponseDto>> inquireMemberInfos(
      @RequestParam List<Long> memberIds) {
    Map<String, MemberInfoForProjectResponseDto> memberInfos =
        memberService.inquireNameAndProfileImage(memberIds);
    return new ResultDataResponse<>(String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.name(), "성공", memberInfos);
  }
}
