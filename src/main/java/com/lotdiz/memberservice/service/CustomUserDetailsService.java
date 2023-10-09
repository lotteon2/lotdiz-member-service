package com.lotdiz.memberservice.service;

import com.lotdiz.memberservice.entity.Member;
import com.lotdiz.memberservice.entity.MemberRole;
import com.lotdiz.memberservice.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AUTH;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * 로그인 시 DB에서 유저정보를 가져와 해당 정보를 기반으로 usedetails.User 객체 생성
     * @param username the username identifying the user whose data is required.
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return memberRepository.findByMemberEmail(username)
            .map(user -> createUser(username, user))
            .orElseThrow(() -> new UsernameNotFoundException(username + "-> 데이터베이스에서 찾을 수 없습니다."));
    }
    private org.springframework.security.core.userdetails.User createUser(String username,
        Member user) {
        List<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                    .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getMemberEmail(),
            user.getMemberPassword(), authorities);
    }

}
