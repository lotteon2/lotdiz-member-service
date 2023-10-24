package com.lotdiz.memberservice.entity;

import static javax.persistence.GenerationType.IDENTITY;

import com.lotdiz.memberservice.entity.common.BaseEntity;
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
public class MembershipPolicy extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "membership_policy_id")
  private Long membershipPolicyId;

  @Column(name = "membership_policy_grade", nullable = false)
  private String membershipPolicyGrade;

  @Column(name = "membership_policy_subscription_fee", nullable = false)
  private Long membershipPolicySubscriptionFee;

  @Column(name = "membership_policy_discount_rate", nullable = false)
  private Integer membershipPolicyDiscountRate;

  @Column(name = "membership_policy_point_accumulation_rate", nullable = false)
  private Integer membershipPolicyPointAccumulationRate;
}
