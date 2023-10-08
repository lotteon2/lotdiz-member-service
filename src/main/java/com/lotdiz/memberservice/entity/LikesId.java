package com.lotdiz.memberservice.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

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
