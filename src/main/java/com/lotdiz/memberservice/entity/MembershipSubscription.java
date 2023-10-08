package com.lotdiz.memberservice.entity;

import com.lotdiz.memberservice.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipSubscription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long membershipSubscriptionId;

    @Column(nullable = false)
    private Long membershipId;
}
