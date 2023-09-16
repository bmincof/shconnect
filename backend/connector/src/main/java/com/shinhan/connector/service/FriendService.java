package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.request.FriendAddRequest;
import com.shinhan.connector.dto.response.FriendAddResponse;
import com.shinhan.connector.dto.response.FriendResponse;
import com.shinhan.connector.dto.request.FriendUpdateRequest;
import com.shinhan.connector.entity.Friend;
import com.shinhan.connector.entity.Member;
import com.shinhan.connector.repository.FriendRepository;
import com.shinhan.connector.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    @Transactional
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
                .sorted((f1, f2) -> {
                    int relationCompare = f1.getRelation().compareTo(f2.getRelation());
                    if (relationCompare == 0) {
                        return f1.getName().compareTo(f2.getName());
                    } else {
                        return relationCompare;
                    }
                })
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

    @Transactional
    public void removeFriend(Integer friendNo, UserDetailsImpl user) {
        log.info("[지인 삭제] 지인 삭제요청. {}, {}", friendNo, user.toString());

        Friend friend = friendRepository.findById(friendNo)
                .orElseThrow(() -> {
                    log.error("[지인 삭제] 지인 번호 잘못됨");
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "지인 번호가 잘못되었습니다.");
                });

        if (!friend.getMember().getNo().equals(user.getId())) {
            log.error("[지인 삭제] 요청한 사용자가 잘못되었습니다. {}, {}", friend.getMember().getNo(), user.getId());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 지인 삭제 권한이 없습니다.");
        }

        friendRepository.delete(friend);
    }

    @Transactional
    public FriendResponse updateFriend(Integer friendNo, FriendUpdateRequest friendUpdateRequest, UserDetailsImpl user) {
        log.info("[지인 수정] 지인 수정요청. {}, {}, {}", friendNo, friendUpdateRequest, user);

        Friend friend = friendRepository.findById(friendNo).orElseThrow(() -> {
            log.error("[지인 수정] 해당 지인을 찾을 수 없습니다. {}", friendNo);
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "지인을 찾을 수 없습니다.");
        });

        if (!friend.getMember().getNo().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "자신의 지인만 수정할 수 있습니다");
        }

        log.info("[지인 수정] 정보 업데이트");
        friend.update(friendUpdateRequest);
        friendRepository.save(friend);
        friendRepository.flush();

        log.info("[지인 수정] 업데이트 완료");
        return FriendResponse.fromEntity(friend);
    }
}
