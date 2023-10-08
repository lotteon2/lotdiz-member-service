package com.lotdiz.memberservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Membership {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long membershipId;

    @Column(nullable = false)
    private LocalDateTime membershipSubscriptionCreatedAt;

    @Column(nullable = false)
    private LocalDateTime membershipSubscriptionExpiredAt;

    @Column(nullable = false)
    @Builder.Default
    private Boolean membershipStatus = true;

    @Column(nullable = false)
    private Long membershipPolicyId;

    @Column(nullable = false)
    private Long MemberId;
}
