package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.*;
import com.shinhan.connector.dto.request.ScheduleAddRequest;
import com.shinhan.connector.dto.response.ScheduleAddResponse;
import com.shinhan.connector.dto.response.ScheduleListResponse;
import com.shinhan.connector.dto.response.ScheduleResponse;
import com.shinhan.connector.entity.MySchedule;
import com.shinhan.connector.entity.Schedule;
import com.shinhan.connector.repository.FriendRepository;
import com.shinhan.connector.repository.MemberRepository;
import com.shinhan.connector.repository.MyScheduleRepository;
import com.shinhan.connector.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MyScheduleRepository myScheduleRepository;
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    // 새로운 일정을 추가하는 메서드
    @Transactional
    public ScheduleAddResponse addSchedule(ScheduleAddRequest request, UserDetailsImpl user) {
        log.info("[일정 등록] 일정등록 요청. {}, {}", request.toString(), user.getUserId());

        if (request.getFriendNo() == null) {
            // 저장할 엔티티 생성
            MySchedule mySchedule = request.toMyScheduleEntity();
            mySchedule.setMember(memberRepository.findById(user.getId()).orElseThrow(NoSuchElementException::new));

            // 생성한 엔티티 저장
            myScheduleRepository.save(mySchedule);
            myScheduleRepository.flush();

            // API 응답 생성
            return ScheduleAddResponse.fromMyScheduleEntity(mySchedule);
        } else {
            // 저장할 엔티티 생성
            Schedule schedule = request.toScheduleEntity();
            schedule.setFriend(friendRepository.findById(request.getFriendNo()).orElseThrow(NoSuchElementException::new));
            schedule.setMember(memberRepository.findById(user.getId()).orElseThrow(NoSuchElementException::new));

            // 생성한 엔티티 저장
            scheduleRepository.save(schedule);
            scheduleRepository.flush();

            // API 응답 생성
            return ScheduleAddResponse.fromScheduleEntity(schedule);
        }
    }

    @Transactional
    public ResponseMessage deleteSchedule(Integer scheduleNo, String option) {
        if (option.equals("mine")) {
            myScheduleRepository.deleteById(scheduleNo);
        } else {
            scheduleRepository.deleteById(scheduleNo);
        }

        return new ResponseMessage("삭제가 완료되었습니다.");
    }

    @Transactional(readOnly = true)
    // 일정을 상세조회하는 메서드
    public ScheduleResponse selectSchedule(Integer scheduleNo, String option) {
        if (option.equals("mine")) {
            return ScheduleResponse.fromMyScheduleEntity(myScheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new));
        } else {
            return new ScheduleResponse(scheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new));
        }
    }

    // 일정 목록을 조회하는 메서드
    @Transactional(readOnly = true)
    public List<ScheduleListResponse> selectAllSchedule(UserDetailsImpl user) {
        // 일정 목록 모두 불러오기
        List<ScheduleListResponse> schedules = scheduleRepository.findByMember(user.getId()).stream()
                .map(ScheduleListResponse::fromScheduleEntity)
                .collect(Collectors.toList());
        // 내 일정 목록 모두 불러와서 추가하기
        schedules.addAll(myScheduleRepository.findByMember(user.getId()).stream()
                .map(ScheduleListResponse::fromMyScheduleEntity)
                .collect(Collectors.toList()));

        // 최근 날짜부터 조회
        schedules.sort(Comparator.comparing(ScheduleListResponse::getDate, Comparator.reverseOrder()));

        return schedules;
    }

    //TODO: 일정 수정 메서드 추가
}
