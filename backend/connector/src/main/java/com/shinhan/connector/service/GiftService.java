package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.dto.request.GiftAddRequest;
import com.shinhan.connector.dto.request.SearchCondition;
import com.shinhan.connector.dto.response.*;
import com.shinhan.connector.entity.GiftReceive;
import com.shinhan.connector.entity.GiftSend;
import com.shinhan.connector.repository.*;
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
    private final ScheduleQueryDslRepository scheduleQueryDslRepository;
    private final MyScheduleRepository myScheduleRepository;
    private final MyScheduleQueryDslRepository myScheduleQueryDslRepository;
    private final FriendRepository friendRepository;

    @Transactional
    public GiftAddResponse createGift(GiftAddRequest giftAddRequest, String options, UserDetailsImpl user) {
        log.info("[선물 등록] 선물등록 요청. {}, {}", giftAddRequest.toString(), user.getUserId());

        // 보낸 선물이면
        if (options.equals("give")) {
            GiftSend gift = giftAddRequest.toGiftSendEntity();
            gift.setSchedule(scheduleRepository.findById(giftAddRequest.getScheduleNo()).orElseThrow(NoSuchElementException::new));

            giftSendRepository.save(gift);
            giftSendRepository.flush();

            System.out.println("new GiftSendAddResponse(gift) = " + new GiftSendAddResponse(gift));
            
            return new GiftSendAddResponse(gift);
        // 받은 선물이면
        } else if (options.equals("receive")){

            GiftReceive gift = giftAddRequest.toGiftReceiveEntity();
            gift.setMySchedule(myScheduleRepository.findById(giftAddRequest.getScheduleNo()).orElseThrow(NoSuchElementException::new));
            gift.setFriend(friendRepository.findById(giftAddRequest.getFriendNo()).orElseThrow(NoSuchElementException::new));

            giftReceiveRepository.save(gift);
            giftReceiveRepository.flush();

            return new GiftReceiveAddResponse(gift);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public ResponseMessage deleteGift(Integer giftNo, String option, UserDetailsImpl user) {
        log.info("[선물 삭제] 선물삭제 요청. {}, {}", giftNo, option);

        if (option.equals("give")) {
            giftSendRepository.findById(giftNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId());

            giftSendRepository.deleteById(giftNo);
            return new ResponseMessage("삭제가 완료되었습니다.");
        } else if (option.equals("receive")) {
            giftReceiveRepository.findById(giftNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId());

            giftReceiveRepository.deleteById(giftNo);
            return new ResponseMessage("삭제가 완료되었습니다.");
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Transactional(readOnly = true)
    public GiftResponse getGift(Integer giftNo, String option, UserDetailsImpl user) {
        log.info("[선물 조회] 선물 상세조회 요청. {}, {}", giftNo, option);

        if (option.equals("give")) {
            return new GiftSendResponse(giftSendRepository.findById(giftNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId()));
        } else if (option.equals("receive")) {
            return new GiftReceiveResponse(giftReceiveRepository.findById(giftNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId()));
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Transactional(readOnly = true)
    public List<GiftResponse> getAllGift(SearchCondition searchCondition, UserDetailsImpl user) {
        log.info("[선물 목록] 선물 목록 조회 요청. {}, {}", searchCondition.toString());

        if (searchCondition.getOption().equals("give")) {
            // 회원의 일정 목록에 있는 모든 보낸선물을 하나의 리스트로 담기
            return scheduleQueryDslRepository.getListByCondition(searchCondition, user.getId()).stream()
                    .flatMap(schedule -> schedule.getGiftSends().stream())
                    .map(GiftSendResponse::new)
                    .collect(Collectors.toList());
        } else if (searchCondition.getOption().equals("receive")) {
            // 회원의 내 일정 목록에 있는 모든 받은 선물을 하나의 리스트로 담기
            return myScheduleQueryDslRepository.getListByCondition(searchCondition, user.getId()).stream()
                    .flatMap(mySchedule -> mySchedule.getGiftReceives().stream())
                    .map(GiftReceiveResponse::new)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException();
        }
    }

    //TODO: 선물 수정 메서드 추가
}
