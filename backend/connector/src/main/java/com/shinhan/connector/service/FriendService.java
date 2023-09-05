package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.FriendAddRequest;
import com.shinhan.connector.dto.FriendAddResponse;
import com.shinhan.connector.dto.FriendResponse;
import com.shinhan.connector.entity.Friend;
import com.shinhan.connector.entity.Member;
import com.shinhan.connector.repository.FriendRepository;
import com.shinhan.connector.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<FriendResponse> getAll(UserDetailsImpl user) {
        log.info("[지인목록 조회] 지인목록 조회 요청. {}", user.toString());

        return friendRepository.findByMember(user.getId()).stream()
                .map((friend -> FriendResponse.fromEntity(friend)))
                .collect(Collectors.toList());
    }

    public FriendResponse getFriend(Integer friendNo, UserDetailsImpl user) {
        log.info("[지인상세 조회] 지인 상세 조회 시작. {}, {}", friendNo, user.toString());

        return FriendResponse.fromEntity(friendRepository.findById(friendNo).orElseThrow(() -> {
            log.error("[지인상세 조회] 지인 번호 잘못됨");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당하는 친구가 없습니다.");
        }));
    }
}
