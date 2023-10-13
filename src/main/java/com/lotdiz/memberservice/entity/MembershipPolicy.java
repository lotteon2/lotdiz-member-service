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
public class MembershipPolicy extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long membershipPolicyId;

  @Column(nullable = false)
  private String membershipPolicyGrade;

  @Column(nullable = false)
  private Long membershipPolicySubscriptionFee;

  @Column(nullable = false)
  private Integer membershipPolicyDiscountRate;

  @Column(nullable = false)
  private Integer membershipPolicyPointAccumulationRate;
}
