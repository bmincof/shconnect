package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.request.RecommendCondition;
import com.shinhan.connector.dto.response.GiftRecommendResponse;
import com.shinhan.connector.dto.response.TributeRecommendResponse;
import com.shinhan.connector.repository.LogQueryDslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RecommendService {
    private final LogQueryDslRepository logQueryDslRepository;

    public GiftRecommendResponse recommendGift(RecommendCondition condition, UserDetailsImpl user) {
        return null;
    }

    public TributeRecommendResponse recommendTribute(RecommendCondition condition, UserDetailsImpl user) {
        return null;
    }
}
