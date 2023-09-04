package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.FriendAddRequest;
import com.shinhan.connector.dto.FriendAddResponse;
import com.shinhan.connector.entity.Friend;
import com.shinhan.connector.entity.Member;
import com.shinhan.connector.repository.FriendRepository;
import com.shinhan.connector.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    public FriendAddResponse createFriend(FriendAddRequest friendAddRequest, UserDetailsImpl user) {
        log.info("[지인 등록] 지인등록 요청. {}, {}", friendAddRequest.toString(), user.getUserId());

        Member member = memberRepository.findMemberById(user.getUserId()).get();
        Friend friend = friendAddRequest.toEntity(member);

        friendRepository.save(friend);
        friendRepository.flush();

        log.info("[지인 등록] 지인 등록 완료");
        return new FriendAddResponse(friend);
    }
}
