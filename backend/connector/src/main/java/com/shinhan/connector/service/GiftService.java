package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.GiftAddRequest;
import com.shinhan.connector.dto.GiftAddResponse;
import com.shinhan.connector.entity.GiftSend;
import com.shinhan.connector.repository.GiftReceiveRepository;
import com.shinhan.connector.repository.GiftSendRepository;
import com.shinhan.connector.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class GiftService {
    private final GiftSendRepository giftSendRepository;
    private final GiftReceiveRepository giftReceiveRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public GiftAddResponse createGift(GiftAddRequest giftAddRequest, String options, UserDetailsImpl user) {
        log.info("[선물 등록] 선물등록 요청. {}, {}", giftAddRequest.toString(), user.getUserId());

        // 보낸 선물이면
        if (options.equals("give")) {
            GiftSend gift = giftAddRequest.toGiftSendEntity();
            gift.setSchedule(scheduleRepository.findById(giftAddRequest.getScheduleNo()).orElseThrow(NoSuchElementException::new));

            giftSendRepository.save(gift);
            giftSendRepository.flush();

            return GiftAddResponse.fromGiftSendEntity(gift);
        // 받은 선물이면
        } else if (options.equals("receive")){
            return null;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
