package com.lotdiz.memberservice.entity;

import static lombok.AccessLevel.PROTECTED;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class LikesId implements Serializable {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "project_id", nullable = false)
  private Long projectId;
}
