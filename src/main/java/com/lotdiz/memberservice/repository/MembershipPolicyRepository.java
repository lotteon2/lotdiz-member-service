package com.lotdiz.memberservice.repository;

import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.entity.MembershipPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipPolicyRepository extends JpaRepository<MembershipPolicy, Long> {

    MembershipPolicy findByMembershipPolicyId(Long membershipPolicyId);
}
