package com.lotdiz.memberservice.entity;

import com.lotdiz.memberservice.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MemberRole memberRole = MemberRole.USER;

    @Column(nullable = false, unique = true)
    private String MemberEmail;

    @Column(nullable = false, length = 5)
    @Size(min = 8, max = 16)
    private String memberPassword;

    @Column(nullable = false, length = 10)
    @Size(max = 10)
    private String memberName;

    @Column(nullable = false)
    private Long memberPhoneNumber;

    @Column(nullable = false)
    @Builder.Default
    private Long memberPoint = 0L;

    @Column(nullable = false)
    @Builder.Default
    private String memberProfileImageUrl = "https://picsum.photos/200";

    @Column(nullable = false)
    private Boolean memberPrivacyAgreement;
}
