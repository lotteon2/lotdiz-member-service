package com.lotdiz.memberservice.controller.clientcontroller;

import com.lotdiz.memberservice.dto.response.MemberInfoForProjectResponseDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.service.LikesService;
import com.lotdiz.memberservice.service.MemberService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProjectClientController {
  private final MemberService memberService;
  private final LikesService likesService;

  @GetMapping("/members")
  public ResponseEntity<ResultDataResponse<Map<String, MemberInfoForProjectResponseDto>>>
      inquireMemberInfos(@RequestParam List<Long> memberIds) {
    Map<String, MemberInfoForProjectResponseDto> memberInfos =
        memberService.getNameAndProfileImage(memberIds);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.name(), "성공", memberInfos));
  }

  @GetMapping("/projects/{projectId}/like-count")
  public ResponseEntity<ResultDataResponse<Map<Long, Long>>> calProjectLikesCnt(
      @PathVariable("projectId") Long projectId) {
    long count = likesService.calProjectLikesCnt(projectId);
    Map<Long, Long> likesCnts = new HashMap<>();
    likesCnts.put(projectId, count);
    return ResponseEntity.ok()
        .body(new ResultDataResponse<>("200", HttpStatus.OK.name(), "성공", likesCnts));
  }

  @GetMapping("/projects/islike")
  public ResponseEntity<ResultDataResponse<Map<String, Boolean>>> isLikes(
      @RequestHeader Long memberId, @RequestParam List<Long> projects) {
    List<Boolean> likesStatus = likesService.queryIsLikes(memberId, projects);
    Map<String, Boolean> map = new HashMap<>();
    for (int i = 0; i < projects.size(); i++) {
      map.put(projects.get(i).toString(), likesStatus.get(i));
    }
    return ResponseEntity.ok()
        .body(new ResultDataResponse<>("200", HttpStatus.OK.name(), "성공", map));
  }
}
