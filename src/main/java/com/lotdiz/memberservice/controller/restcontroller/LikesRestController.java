package com.lotdiz.memberservice.controller.restcontroller;

import com.lotdiz.memberservice.dto.ResultDataResponse;
import com.lotdiz.memberservice.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikesRestController {
  private final LikesService likesService;

  @PostMapping("/projects/{projectId}/likes")
  public ResultDataResponse<Object> addLikes(@PathVariable Long projectId) {
    Long loginedId = 1L; // hard coding.
    likesService.add(loginedId, projectId);
    return new ResultDataResponse<>("201", HttpStatus.CREATED.name(), "단일 찜 추가 성공", null);
  }

  @DeleteMapping("/projects/{projectId}/likes")
  public ResultDataResponse<Object> removeSingleLikes(@PathVariable Long projectId) {
    Long loginedId = 1L; // hard coding.
    likesService.remove(loginedId, projectId);
    return new ResultDataResponse<>("200", HttpStatus.OK.name(), "단일 찜 삭제 성공", null);
  }
}
