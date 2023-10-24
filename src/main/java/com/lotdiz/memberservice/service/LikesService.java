package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.response.FundingDetailsForShowResponseDto;
import com.lotdiz.memberservice.dto.response.LikesDetailsForShowResponseDto;
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
import org.springframework.stereotype.Service;

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

  /**
   * 찜 목록에 포함되어있는 프로젝트 세부 정보 조회
   *
   * @param memberId
   * @return
   */
  public List<LikesDetailsForShowResponseDto> showProjectDetails(Long memberId) {
    Member member = memberService.findMemberByMemberId(memberId);
    List<Likes> likes = likesRepository.findLikesByMember(member);
    List<Long> projectIds = new ArrayList<>();
    for (Likes like : likes) {
      projectIds.add(like.getId().getProjectId());
    }
    List<ProjectDetailsForShowResponseDto> projectDetails =
        projectClientService.getProjectDetails(projectIds);

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
}
