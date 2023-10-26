package com.lotdiz.memberservice.entity;

import static javax.persistence.GenerationType.IDENTITY;

import com.lotdiz.memberservice.dto.request.MemberInfoForChangeRequestDto;
import com.lotdiz.memberservice.dto.request.MemberInfoForSignUpRequestDto;
import com.lotdiz.memberservice.entity.common.BaseEntity;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "member_id")
  private Long memberId;

  @Column(name = "member_role", nullable = false)
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private MemberRole memberRole = MemberRole.ROLE_USER;

  @Column(name = "member_email", nullable = false, unique = true)
  private String memberEmail;

  @Column(name = "member_password", nullable = false)
  private String memberPassword;

  @Column(name = "member_name", nullable = false, length = 10)
  @Size(max = 10)
  private String memberName;

  @Column(name = "member_phone_number", nullable = false)
  private String memberPhoneNumber;

  @Column(name = "member_point", nullable = false)
  @Builder.Default
  private Long memberPoint = 0L;

  @Column(name = "member_profile_image_url", nullable = false)
  @Builder.Default
  private String memberProfileImageUrl = "https://picsum.photos/200";

  @Column(name = "member_privacy_agreement", nullable = false)
  private Boolean memberPrivacyAgreement;

  @OneToOne
  @JoinColumn(name = "membership_id")
  private Membership membership;

  @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
  private List<DeliveryAddress> deliveryAddresses;

  public void assignMemberPassword(String memberPassword) {
    this.memberPassword = memberPassword;
  }

  public void assignMemberName(String memberName) {
    this.memberName = memberName;
  }

  public void assignMemberPhoneNumber(String memberPhoneNumber) {
    this.memberPhoneNumber = memberPhoneNumber;
  }

  public void assignMemberPoint(Long memberPoint) {
    this.memberPoint = memberPoint;
  }

  public void assignMemberProfileImageUrl(String memberProfileImageUrl) {
    this.memberProfileImageUrl = memberProfileImageUrl;
  }

  public static Member signup(MemberInfoForSignUpRequestDto memberSignUpDto) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    Member member =
        Member.builder()
            .memberEmail(memberSignUpDto.getUsername())
            .memberPassword(passwordEncoder.encode(memberSignUpDto.getMemberPassword()))
            .memberName(memberSignUpDto.getMemberName())
            .memberPhoneNumber(memberSignUpDto.getMemberPhoneNumber())
            .memberPrivacyAgreement(memberSignUpDto.getMemberPrivacyAgreement())
            .build();
    return member;
  }

  public static Member renew(Member member, MemberInfoForChangeRequestDto memberChangeDto) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    member.assignMemberName(memberChangeDto.getMemberName());
    member.assignMemberPhoneNumber(memberChangeDto.getMemberPhoneNumber());
    member.assignMemberProfileImageUrl(memberChangeDto.getMemberProfileImageUrl());

    // 비밀번호 변경시
    if (memberChangeDto.getNewPassword() != null) {
      // 입력한 비밀번호가 기존 비밀번호와 일치한다면
      if (passwordEncoder.matches(
          memberChangeDto.getOriginPassword(), member.getMemberPassword())) {
        // 새로운 비밀번호로 변경
        member.assignMemberPassword(passwordEncoder.encode(memberChangeDto.getNewPassword()));
      } else {
        throw new RuntimeException("비밀번호가 일치하지 않습니다.");
      }
    }
    return member;
  }

  public void assignMembership(Membership membership) {
    this.membership = membership;
  }
}
