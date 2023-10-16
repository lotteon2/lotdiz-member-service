package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.config.auth.PrincipalDetails;
import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.repository.MemberRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
  private final Logger logger = LoggerFactory.getLogger(PrincipalDetailsService.class);

  private final MemberRepository memberRepository;

  /**
   * 로그인 시 DB에서 유저정보를 가져와 해당 정보를 기반으로 usedetails.User 객체 생성
   *
   * @param username the username identifying the user whose data is required.
   * @return UserDetails
   * @throws UsernameNotFoundException
   */
  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    logger.info("loadUserByUsername here");
    Optional<Member> om = memberRepository.findByMemberEmail(username);
    if (om.isPresent()) {
      return new PrincipalDetails(om.get());
    }
    return null;
  }
}
