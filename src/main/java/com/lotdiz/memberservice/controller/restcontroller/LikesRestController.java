package com.lotdiz.memberservice.controller.restcontroller;

import com.lotdiz.memberservice.dto.response.ResultDataResponse;
import com.lotdiz.memberservice.service.LikesService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

  @DeleteMapping("/likes")
  public ResultDataResponse<Object> removeMultiLikes(@RequestBody List<Long> projectIds) {
    Long loginedId = 1L; // hard coding.
    likesService.removeMulti(loginedId, projectIds);
    return new ResultDataResponse<>(
        "200",
        HttpStatus.OK.name(),
        "다중 찜 삭제 성공",
        null
    );
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