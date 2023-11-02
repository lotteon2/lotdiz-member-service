package com.lotdiz.memberservice.repository;

import com.lotdiz.memberservice.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByMemberEmail(@Param("memberEmail") String memberEmail);

  Optional<Member> findByMemberId(Long memberId);

  @Query("select m from Member m where m.memberId in :memberIds")
  List<Member> findMembersNameByIds(List<Long> memberIds);
}
