package com.lotdiz.memberservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Likes {

  @EmbeddedId private LikesId id;

  @Column(name = "created_at")
  @CreatedDate
  private LocalDateTime createdAt;

  public static Likes create(Member member, Long projectId) {
    LikesId likesId = LikesId.builder().member(member).projectId(projectId).build();

    return Likes.builder().id(likesId).build();
  }
}
