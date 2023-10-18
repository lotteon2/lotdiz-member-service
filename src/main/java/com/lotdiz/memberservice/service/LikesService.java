package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.response.LikesInfoForShowResponseDto;
import com.lotdiz.memberservice.entity.Likes;
import com.lotdiz.memberservice.entity.LikesId;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.repository.LikesRepository;
import com.lotdiz.memberservice.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {
  private final LikesRepository likesRepository;
  private final MemberRepository memberRepository;

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
    for(Long projectId : projectIds) {
      LikesId likesId = Likes.createId(member, projectId);
      Likes likes = likesRepository.findById(likesId).orElseThrow();
      likesRepository.delete(likes);
    }
  }

  public List<LikesInfoForShowResponseDto> show(Long memberId) {
    Member member = memberRepository.findByMemberId(memberId).orElseThrow();
    List<Likes> likes = likesRepository.findLikesByMemberId(member);
    List<LikesInfoForShowResponseDto> likesInfos = new ArrayList<>();
    for(Likes like : likes) {
      LikesInfoForShowResponseDto likesInfoDto = LikesInfoForShowResponseDto.builder()
          .projectId(like.getId().getProjectId())
          .createdAt(like.getCreatedAt().toString())
          .build();
      likesInfos.add(likesInfoDto);
    }

    return likesInfos;
  }
}
