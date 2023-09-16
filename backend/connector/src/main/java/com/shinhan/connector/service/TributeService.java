package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.request.SearchCondition;
import com.shinhan.connector.dto.request.TributeRegistRequest;
import com.shinhan.connector.dto.response.TributeResponse;
import com.shinhan.connector.entity.*;
import com.shinhan.connector.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TributeService {
    private final TributeReceiveRepository tributeReceiveRepository;
    private final TributeSendRepository tributeSendRepository;
    private final MyScheduleRepository myScheduleRepository;
    private final ScheduleRepository scheduleRepository;
    private final FriendRepository friendRepository;
    private final TributeReceiveQueryDslRepository tributeReceiveQueryDslRepository;
    private final TributeSendQueryDslRepository tributeSendQueryDslRepository;

    public TributeResponse regist(TributeRegistRequest tributeRegistRequest, UserDetailsImpl user) {
        Object tribute;
        log.info("[경조사비 등록] 경조사비 등록 요청. {}", tributeRegistRequest, user);

        if (tributeRegistRequest.getFriendNo() == null) {
            Schedule schedule = scheduleRepository.findById(tributeRegistRequest.getScheduleNo()).orElseThrow(() -> {
                log.error("[경조사비 등록] 일정을 찾을 수 없습니다.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "일정을 찾을 수 없습니다.");
            });

            if (!schedule.getMember().getNo().equals(user.getId())) {
                log.error("[경조사비 등록] 자신의 일정이 아닙니다.");
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "자신의 일정에만 등록할 수 있습니다.");
            }

            TributeSend send = TributeSend.builder()
                    .schedule(schedule)
                    .amount(tributeRegistRequest.getAmount())
                    .note(tributeRegistRequest.getNote())
                    .sent(false)
                    .build();

            tributeSendRepository.save(send);
            tributeSendRepository.flush();

            tribute = send;
        } else {
            MySchedule schedule = myScheduleRepository.findById(tributeRegistRequest.getScheduleNo()).orElseThrow(() -> {
                log.error("[경조사비 등록] 일정을 찾을 수 없습니다.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "일정을 찾을 수 없습니다.");
            });

            if (!schedule.getMember().getNo().equals(user.getId())) {
                log.error("[경조사비 등록] 자신의 일정이 아닙니다.");
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "자신의 일정에만 등록할 수 있습니다.");
            }

            Friend friend = friendRepository.findById(tributeRegistRequest.getFriendNo()).orElseThrow(() -> {
                log.error("[경조사비 등록] 친구를 찾을 수 없습니다.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "친구를 찾을 수 없습니다.");
            });

            if (!friend.getMember().getNo().equals(user.getId())) {
                log.error("[경조사비 등록] 사용자의 지인이 아닙니다.");
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "자신의 지인에만 등록할 수 있습니다.");
            }

            TributeReceive receive = TributeReceive.builder()
                    .mySchedule(schedule)
                    .amount(tributeRegistRequest.getAmount())
                    .friend(friend)
                    .note(tributeRegistRequest.getNote())
                    .build();
            tributeReceiveRepository.save(receive);
            tributeReceiveRepository.flush();

            tribute = receive;
        }

        return TributeResponse.entityToDto(tribute);
    }

    public TributeResponse getDetail(String option, Integer tributeNo) {
        log.info("[경조사비 상세조회] 경조사비 상세조회. {}, {}", option, tributeNo);

        Object tribute = null;
        if (option.contains("give")) {
            tribute = tributeSendRepository.findById(tributeNo)
                    .orElseThrow(() -> {
                        log.error("[경조사비 상세조회] 경조사비 번호가 잘못되었습니다.");
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "경조사비 번호가 잘못되었습니다.");
                    });
        } else {
            tribute = tributeReceiveRepository.findById(tributeNo)
                    .orElseThrow(() -> {
                        log.error("[경조사비 상세조회] 경조사비 번호가 잘못되었습니다.");
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "경조사비 번호가 잘못되었습니다.");
                    });
        }
        log.info("[경조사비 상세조회] 상세조회 완료.");
        return TributeResponse.entityToDto(tribute);
    }

    public Map<String, Long> getAmount(SearchCondition searchCondition, UserDetailsImpl user) {
        Map<String, Long> result = new HashMap<>();
        Long amount;

        if (searchCondition.getOption().contains("give")) {
            amount = tributeSendQueryDslRepository.getAmountByCondition(searchCondition, user.getId());
        } else {
            amount = tributeReceiveQueryDslRepository.getAmountByCondition(searchCondition, user.getId());
        }

        result.put("amount", amount == null ? 0L : amount);
        return result;
    }

    public List<TributeResponse> getList(SearchCondition searchCondition, UserDetailsImpl user) {
        List<TributeResponse> responses;

        if (searchCondition.getOption().contains("give")) {
            responses = tributeSendQueryDslRepository.getListByCondition(searchCondition, user.getId()).stream()
                    .map(tributeSend -> TributeResponse.entityToDto(tributeSend))
                    .collect(Collectors.toList());
        } else {
            responses = tributeReceiveQueryDslRepository.getListByCondition(searchCondition, user.getId()).stream()
                    .map(tributeReceive -> TributeResponse.entityToDto(tributeReceive))
                    .collect(Collectors.toList());
        }

        return responses;
    }
}
