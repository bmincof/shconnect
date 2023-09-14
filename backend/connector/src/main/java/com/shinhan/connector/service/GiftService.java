package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.GiftAddRequest;
import com.shinhan.connector.dto.GiftAddResponse;
import com.shinhan.connector.dto.GiftSendResponse;
import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.entity.GiftSend;
import com.shinhan.connector.repository.GiftReceiveRepository;
import com.shinhan.connector.repository.GiftSendRepository;
import com.shinhan.connector.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GiftService {
    private final GiftSendRepository giftSendRepository;
    private final GiftReceiveRepository giftReceiveRepository;
    private final ScheduleRepository scheduleRepository;

    //TODO: 받은 선물 로직 추가

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

    @Transactional
    public ResponseMessage deleteGift(Integer giftNo, String option) {
        if (option.equals("give")) {
            giftSendRepository.deleteById(giftNo);
            return new ResponseMessage("삭제가 완료되었습니다.");
        } else if (option.equals("receive")) {
            return null;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Transactional(readOnly = true)
    public GiftSendResponse getGift(Integer giftNo, String option) {
        if (option.equals("give")) {
            return GiftSendResponse.fromEntity(giftSendRepository.findById(giftNo).orElseThrow(NoSuchElementException::new));
        } else if (option.equals("receive")) {
            return null;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Transactional(readOnly = true)
    public List<GiftSendResponse> getAllGift(String option, Integer friendNo, UserDetailsImpl user) {
        if (option.equals("give")) {
            // 회원의 일정 목록에 있는 모든 보낸선물을 하나의 리스트로 담기
            return scheduleRepository.findByMember(user.getId()).stream()
                    .flatMap(schedule -> schedule.getGiftSends().stream())
                    .map(GiftSendResponse::fromEntity)
                    .collect(Collectors.toList());
        } else if (option.equals("receive")) {
            return null;
        } else {
            throw new IllegalArgumentException();
        }
    }

    //TODO: 선물 수정 메서드 추가
}
