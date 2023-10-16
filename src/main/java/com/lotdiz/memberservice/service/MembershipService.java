package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.request.MembershipInfoForAssignRequestDto;
import com.lotdiz.memberservice.dto.request.MembershipInfoForJoinReqeustDto;
import com.lotdiz.memberservice.dto.request.PaymentsInfoForKakoaPayRequestDto;
import com.lotdiz.memberservice.entity.Membership;
import com.lotdiz.memberservice.mapper.CustomMapper;
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

    private final MembershipRepository membershipRepository;
    private final PaymentsClientService paymentsClientService;


    @Transactional
    public void create(MembershipInfoForJoinReqeustDto membershipJoinDto) {
        Membership membership = Membership.builder()
            .membershipPolicyId(membershipJoinDto.getMembershipPolicyId())
            .build();
        Membership saved = membershipRepository.save(membership);
        PaymentsInfoForKakoaPayRequestDto paymentsDto = CustomMapper.PaymentsInfoForKakoaPayRequestDtoMapper(saved.getMembershipId(), membershipJoinDto);
        Long membershipSubscriptionId = paymentsClientService.getMembershipSubscription(paymentsDto);
        logger.info("membershipSubscriptionId: " + membershipSubscriptionId);
        saved.assignMembershipSubscriptionId(membershipSubscriptionId);
//        map.put("membership_id", saved.getMembershipId());

    }

    public void joinComplete(MembershipInfoForAssignRequestDto membershipAssignDto) {
        LocalDateTime membershipSubscriptionCreatedAt = membershipAssignDto.getCreatedAt();
        LocalDateTime membershipSubscriptionExpiredAt = membershipSubscriptionCreatedAt.withYear(membershipSubscriptionCreatedAt.getYear() + 1);
        Membership found = membershipRepository.findByMembershipId(membershipAssignDto.getMembershipId()).orElseThrow();
        Membership membership = Membership.addMore(found, membershipSubscriptionCreatedAt,
            membershipSubscriptionExpiredAt);
        membershipRepository.save(membership);
    }


}
