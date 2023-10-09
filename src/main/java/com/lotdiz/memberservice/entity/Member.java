package com.lotdiz.memberservice.entity;

import com.lotdiz.memberservice.entity.common.BaseEntity;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

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
    private MemberRole memberRole = MemberRole.USER;

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

    @Column(name = "member_is_activate", nullable = false)
    @Builder.Default
    private Boolean memberIsActivate = true;

    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<Authority> authorities;
}
