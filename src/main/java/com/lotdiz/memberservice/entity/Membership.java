package com.lotdiz.memberservice.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Membership {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "membership_id")
  private Long membershipId;

  @Column(name = "membership_subscription_created_at")
  private LocalDateTime membershipSubscriptionCreatedAt;

  @Column(name = "membership_subscription_expired_at")
  private LocalDateTime membershipSubscriptionExpiredAt;

  @Column(name = "membership_status")
  private Boolean membershipStatus;

  @Column(nullable = false)
  private Long membershipPolicyId;

  @Column(name = "membership_subscription_id")
  private Long membershipSubscriptionId;

  @OneToOne(mappedBy = "membership")
  @JoinColumn(name = "member_id")
  private Member member;

  public void assignMembershipSubscriptionId(Long membershipSubscriptionId) {
    this.membershipSubscriptionId = membershipSubscriptionId;
  }

  public void assignMembershipSubscriptionCreatedAt(LocalDateTime membershipSubscriptionCreatedAt) {
    this.membershipSubscriptionCreatedAt = membershipSubscriptionCreatedAt;
  }

  public void assignMembershipSubscriptionExpiredAt(LocalDateTime membershipSubscriptionExpiredAt) {
    this.membershipSubscriptionExpiredAt = membershipSubscriptionExpiredAt;
  }

  public void assignMembershipStatus(Boolean membershipStatus) {
    this.membershipStatus = membershipStatus;
  }

  public static Membership addMore(Membership membership, LocalDateTime membershipSubscriptionCreatedAt, LocalDateTime membershipSubscriptionExpiredAt) {
    membership.assignMembershipSubscriptionCreatedAt(membershipSubscriptionCreatedAt);
    membership.assignMembershipSubscriptionExpiredAt(membershipSubscriptionExpiredAt);
    membership.assignMembershipStatus(true);
    return membership;
  }


}
