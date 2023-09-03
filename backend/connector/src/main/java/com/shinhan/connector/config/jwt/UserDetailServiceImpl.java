package com.shinhan.connector.config.jwt;

import com.shinhan.connector.entity.Member;
import com.shinhan.connector.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다. : " + memberId));

        return UserDetailsImpl.buildFromMember(member);
    }
}