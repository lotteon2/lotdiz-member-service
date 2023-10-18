package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.dto.response.MembershipPolicyInfoForShowResponseDto;
import com.lotdiz.memberservice.entity.MembershipPolicy;
import com.lotdiz.memberservice.mapper.MessageMapper;
import com.lotdiz.memberservice.repository.MembershipPolicyRepository;
import com.lotdiz.memberservice.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembershipPolicyService {
    private final MembershipPolicyRepository membershipPolicyRepository;
    public MembershipPolicyInfoForShowResponseDto find(Long membershipPolicyId) {
        return MessageMapper.INSTANCE.toMembershipPolicyInfoDto(membershipPolicyRepository.findByMembershipPolicyId(membershipPolicyId));
    }
}
