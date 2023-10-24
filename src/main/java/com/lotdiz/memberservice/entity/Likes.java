package com.lotdiz.memberservice.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

  public static LikesId createId(Member member, Long projectId) {
    return LikesId.builder().member(member).projectId(projectId).build();
  }

  public static Likes create(Member member, Long projectId) {
    LikesId likesId = createId(member, projectId);

    return Likes.builder().id(likesId).build();
  }
}
