package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.GiftAddRequest;
import com.shinhan.connector.dto.GiftAddResponse;
import com.shinhan.connector.entity.GiftReceive;
import com.shinhan.connector.entity.GiftSend;
import com.shinhan.connector.repository.GiftReceiveRepository;
import com.shinhan.connector.repository.GiftSendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GiftService {
    private final GiftSendRepository giftSendRepository;
    private final GiftReceiveRepository giftReceiveRepository;

    public GiftAddResponse createGift(GiftAddRequest giftAddRequest, UserDetailsImpl user) {
        log.info("[선물 등록] 선물등록 요청. {}, {}", giftAddRequest.toString(), user.getUserId());

        // 보낸 선물이면
        if (giftAddRequest.getFriendNo() == null) {
            GiftSend gift = new GiftSend();
            giftSendRepository.save(gift);
            giftSendRepository.flush();
//            return new GiftAddResponse();
        } else {
            GiftReceive gift = new GiftReceive();
            giftReceiveRepository.save(gift);
            giftReceiveRepository.flush();
//            return new GiftAddResponse();
        }
        return null;
    }
}
