package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.request.MembershipInfoForAssignRequestDto;
import com.lotdiz.memberservice.dto.request.MembershipInfoForJoinReqeustDto;
import com.lotdiz.memberservice.dto.request.PaymentsInfoForKakaoPayRequestDto;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.entity.Membership;
import com.lotdiz.memberservice.entity.MembershipPolicy;
import com.lotdiz.memberservice.mapper.CustomMapper;
import com.lotdiz.memberservice.repository.MemberRepository;
import com.lotdiz.memberservice.repository.MembershipRepository;
import com.lotdiz.memberservice.service.client.PaymentsClientService;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private Logger logger = LoggerFactory.getLogger(MembershipService.class);

    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final PaymentsClientService paymentsClientService;


    @Transactional
    public void create(Long memberId, MembershipInfoForJoinReqeustDto membershipJoinDto) {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow();
        Membership membership = Membership.builder()
            .membershipPolicyId(membershipJoinDto.getMembershipPolicyId())
            .build();
        Membership saved = membershipRepository.save(membership);
        member.assignMembershipId(saved.getMembershipId());
        memberRepository.save(member);
        PaymentsInfoForKakaoPayRequestDto paymentsDto = CustomMapper.PaymentsInfoForKakoaPayRequestDtoMapper(saved.getMembershipId(), membershipJoinDto);
        Long membershipSubscriptionId = paymentsClientService.getMembershipSubscription(paymentsDto);
        logger.info("membershipSubscriptionId: " + membershipSubscriptionId);
        saved.assignMembershipSubscriptionId(membershipSubscriptionId);
    }

    public void joinComplete(MembershipInfoForAssignRequestDto membershipAssignDto) {
        LocalDateTime membershipSubscriptionCreatedAt = membershipAssignDto.getCreatedAt();
        LocalDateTime membershipSubscriptionExpiredAt = membershipSubscriptionCreatedAt.withYear(membershipSubscriptionCreatedAt.getYear() + 1);
        Membership found = membershipRepository.findByMembershipId(membershipAssignDto.getMembershipId()).orElseThrow();
        Membership membership = Membership.addMore(found, membershipSubscriptionCreatedAt,
            membershipSubscriptionExpiredAt);
        membershipRepository.save(membership);
    }


    public Membership find(Long membershipId) {
        return membershipRepository.findByMembershipId(membershipId).orElseThrow();
    }
}
