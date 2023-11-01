package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.response.FundingDetailsForShowResponseDto;
import com.lotdiz.memberservice.dto.response.LikesDetailsForShowResponseDto;
import com.lotdiz.memberservice.dto.response.MemberLikesInfoResponseDto;
import com.lotdiz.memberservice.dto.response.ProjectDetailsForShowResponseDto;
import com.lotdiz.memberservice.entity.Likes;
import com.lotdiz.memberservice.entity.LikesId;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.exception.common.EntityNotFoundException;
import com.lotdiz.memberservice.mapper.CustomMapper;
import com.lotdiz.memberservice.repository.LikesRepository;
import com.lotdiz.memberservice.service.client.FundingClientService;
import com.lotdiz.memberservice.service.client.ProjectClientService;
import com.lotdiz.memberservice.utils.CustomErrorMessage;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesService {
  private final LikesRepository likesRepository;
  private final MemberService memberService;
  private final ProjectClientService projectClientService;
  private final FundingClientService fundingClientService;

  /**
   * 찜 추가
   *
   * @param memberId
   * @param projectId
   */
  @Transactional
  public void addLikes(Long memberId, Long projectId) {
    Member member = memberService.findMemberByMemberId(memberId);
    likesRepository.save(Likes.create(member, projectId));
  }

  /**
   * 찜 단일 삭제
   *
   * @param memberId
   * @param projectId
   */
  @Transactional
  public void removeSingleLikes(Long memberId, Long projectId) {
    Member member = memberService.findMemberByMemberId(memberId);
    LikesId likesId = Likes.createId(member, projectId);
    Likes likes = likesRepository.findById(likesId).orElseThrow();
    likesRepository.delete(likes);
  }

  /**
   * 찜 다중 삭제
   *
   * @param memberId
   * @param projectIds
   */
  @Transactional
  public void removeMulti(Long memberId, List<Long> projectIds) {
    Member member = memberService.findMemberByMemberId(memberId);
    for (Long projectId : projectIds) {
      LikesId likesId = Likes.createId(member, projectId);
      Likes likes =
          likesRepository
              .findById(likesId)
              .orElseThrow(() -> new EntityNotFoundException(CustomErrorMessage.NOT_FOUND_LIKES));
      likesRepository.delete(likes);
    }
  }

  public Long calCount(String projectId) {
    return likesRepository.countByProjectId(Long.parseLong(projectId));
  }

  /**
   * 찜 목록에 포함되어있는 프로젝트 세부 정보 조회
   *
   * @param memberId
   * @return List<LikesDetailsForShowResponseDto>
   */
  @Transactional
  public List<LikesDetailsForShowResponseDto> showProjectDetails(Long memberId) {
    Member member = memberService.findMemberByMemberId(memberId);
    List<Likes> likes = likesRepository.findLikesByMember(member);
    List<Long> projectIds = new ArrayList<>();
    for (Likes like : likes) {
      projectIds.add(like.getId().getProjectId());
    }

    // project 정보 가져오기
    List<ProjectDetailsForShowResponseDto> projectDetails =
        projectClientService.getProjectDetails(projectIds);

    for (ProjectDetailsForShowResponseDto dto : projectDetails) {
      log.info(String.valueOf(dto.getProjectThumbnailImageUrl()));
      log.info(dto.getProjectName());
      log.info(dto.getRemainingDays().toString());
      log.info(dto.getMakerName());
    }

    // funding 정보 가져오기
    List<FundingDetailsForShowResponseDto> fundingDetails =
        fundingClientService.getFundigDetails(projectIds);

    List<LikesDetailsForShowResponseDto> likesDetails = new ArrayList<>();
    for (int i = 0; i < likes.size(); i++) {
      LikesDetailsForShowResponseDto likesDetailsDto =
          CustomMapper.toLikesDetailsForShowResponseDto(
              projectDetails.get(i), fundingDetails.get(i));
      likesDetails.add(likesDetailsDto);
    }
    return likesDetails;
  }

  /**
   * 해당 project Info
   *
   * @param projectId, memberId
   * @return
   */
  public MemberLikesInfoResponseDto getLikesInfo(Long memberId, Long projectId) {
    Boolean isLike = null;
    if (memberId != null) {
      isLike = likesRepository.existsByMemberAndProjectId(memberId, projectId);
    }
    return MemberLikesInfoResponseDto.builder()
        .likeCount(likesRepository.countByProjectId(projectId))
        .isLikes(isLike)
        .build();
  }

  /**
   * 넘겨 받은 projectId 각각이 찜 목록에 있는지 없는지 조회
   *
   * @param memberId
   * @param projectIds
   * @return
   */
  public List<Boolean> queryIsLikes(Long memberId, List<Long> projectIds) {
    Member member = memberService.findMemberByMemberId(memberId);
    List<Boolean> results = new ArrayList<>();
    for (Long projectId : projectIds) {
      Likes likes = likesRepository.findByMemberIdAndProjectId(member, projectId);
      if (likes == null) {
        results.add(false);
      } else {
        results.add(true);
      }
    }
    return results;
  }
}
