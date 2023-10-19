package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.response.LikesDetailsForShowResponseDto;
import com.lotdiz.memberservice.dto.response.ProjectDetailsForShowResponseDto;
import com.lotdiz.memberservice.dto.response.FundingDetailsForShowResponseDto;
import com.lotdiz.memberservice.entity.Likes;
import com.lotdiz.memberservice.entity.LikesId;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.mapper.CustomMapper;
import com.lotdiz.memberservice.repository.LikesRepository;
import com.lotdiz.memberservice.repository.MemberRepository;
import com.lotdiz.memberservice.service.client.FundingClientService;
import com.lotdiz.memberservice.service.client.ProjectClientService;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {
  private final LikesRepository likesRepository;
  private final MemberRepository memberRepository;
  private final ProjectClientService projectClientService;
  private final FundingClientService fundingClientService;

  @Transactional
  public void add(Long memberId, Long projectId) {
    Member member = memberRepository.findByMemberId(memberId).orElseThrow();
    likesRepository.save(Likes.create(member, projectId));
  }

  @Transactional
  public void remove(Long memberId, Long projectId) {
    Member member = memberRepository.findByMemberId(memberId).orElseThrow();
    LikesId likesId = Likes.createId(member, projectId);
    Likes likes = likesRepository.findById(likesId).orElseThrow();
    likesRepository.delete(likes);
  }

  @Transactional
  public void removeMulti(Long memberId, List<Long> projectIds) {
    Member member = memberRepository.findByMemberId(memberId).orElseThrow();
    for (Long projectId : projectIds) {
      LikesId likesId = Likes.createId(member, projectId);
      Likes likes = likesRepository.findById(likesId).orElseThrow();
      likesRepository.delete(likes);
    }
  }

  @Transactional
  public List<LikesDetailsForShowResponseDto> showProjectDetails(Long memberId) {
    Member member = memberRepository.findByMemberId(memberId).orElseThrow();
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
