package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.response.GiftRecommendResponse;
import com.shinhan.connector.dto.response.TributeRecommendResponse;
import com.shinhan.connector.entity.GiftLog;
import com.shinhan.connector.entity.Member;
import com.shinhan.connector.entity.TributeLog;
import com.shinhan.connector.repository.GiftLogRepository;
import com.shinhan.connector.repository.MemberRepository;
import com.shinhan.connector.repository.TributeLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RecommendService {
    private final MemberRepository memberRepository;
    private final GiftLogRepository giftLogRepository;
    private final TributeLogRepository tributeLogRepository;

    public GiftRecommendResponse recommendGift(String category, UserDetailsImpl user) {
        Member member = memberRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."));

        GiftLog recommended = giftLogRepository.findByCondition(member.getAge() / 10 * 10, member.getGender().getValue(), category);

        return GiftRecommendResponse.builder()
                .category(recommended.getGiftCategory())
                .price(recommended.getAvgPrice())
                .build();
    }

    public TributeRecommendResponse recommendTribute(String category, UserDetailsImpl user) {
        Member member = memberRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."));

        TributeLog recommended = tributeLogRepository.findByCondition(member.getAge() / 10 * 10, member.getGender().getValue(), category);

        return TributeRecommendResponse.builder()
                .price(recommended.getAvgPrice())
                .build();
    }
}
