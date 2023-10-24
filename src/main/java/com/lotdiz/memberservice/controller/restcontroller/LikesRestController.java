package com.lotdiz.memberservice.controller.restcontroller;

import com.lotdiz.memberservice.dto.response.LikesDetailsForShowResponseDto;
import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.service.LikesService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikesRestController {
  private final LikesService likesService;

  @PostMapping("/projects/{projectId}/likes")
  public ResponseEntity<ResultDataResponse<Object>> addLikes(
      @RequestHeader Long memberId, @PathVariable Long projectId) {
    likesService.add(memberId, projectId);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.CREATED.value()),
                HttpStatus.CREATED.name(),
                "단일 찜 추가 성공",
                null));
  }

  @DeleteMapping("/projects/{projectId}/likes")
  public ResponseEntity<ResultDataResponse<Object>> removeSingleLikes(
      @RequestHeader Long memberId, @PathVariable Long projectId) {
    likesService.remove(memberId, projectId);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.name(), "단일 찜 삭제 성공", null));
  }

  @PutMapping("/likes")
  public ResponseEntity<ResultDataResponse<Object>> removeMultiLikes(
      @RequestHeader Long memberId, @RequestBody List<Long> projectIds) {
    likesService.removeMulti(memberId, projectIds);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()), HttpStatus.OK.name(), "다중 찜 삭제 성공", null));
  }

  @GetMapping("/members/likes")
  public ResponseEntity<ResultDataResponse<List<LikesDetailsForShowResponseDto>>> showLikes(
      @RequestHeader Long memberId) {
    List<LikesDetailsForShowResponseDto> projectDetails = likesService.showProjectDetails(memberId);
    return ResponseEntity.ok()
        .body(
            new ResultDataResponse<>(
                String.valueOf(HttpStatus.OK.value()),
                HttpStatus.OK.name(),
                "찜 목록 조회 성공",
                projectDetails));
  }

  @GetMapping("/projects/{projectId}/like-count")
  public ResultDataResponse<Map<String, Long>> calProjecLikes(@PathVariable("projectId") String projectId) {
    long count = likesService.calCount(projectId);
    Map<String, Long> map = new HashMap<String, Long>();
    map.put(projectId.toString(), count);
    return new ResultDataResponse<>(
        "200",
        HttpStatus.OK.name(),
        "성공",
        map
    );
  }

  @GetMapping("/projects/islike")
  public ResultDataResponse<Map<String, Boolean>> isLikes(
      @RequestHeader Long memberId, @RequestParam List<Long> projectIds) {
    List<Boolean> likesStatus = likesService.queryIsLikes(memberId, projectIds);
    Map<String, Boolean> map = new HashMap<>();
    for(int i = 0; i < projectIds.size(); i++) {
      map.put(projectIds.get(i).toString(), likesStatus.get(i));
    }
    return new ResultDataResponse<>(
        "200",
        HttpStatus.OK.name(),
        "성공",
        map
    );
  }
}
