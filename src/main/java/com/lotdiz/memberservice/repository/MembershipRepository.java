package com.lotdiz.memberservice.repository;

import com.lotdiz.memberservice.entity.Membership;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

  Optional<Membership> findByMembershipId(Long membershipId);

}
