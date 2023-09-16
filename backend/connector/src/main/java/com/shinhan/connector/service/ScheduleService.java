package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.dto.request.ScheduleAddRequest;
import com.shinhan.connector.dto.request.ScheduleUpdateRequest;
import com.shinhan.connector.dto.request.SearchCondition;
import com.shinhan.connector.dto.response.ScheduleAddResponse;
import com.shinhan.connector.dto.response.ScheduleListResponse;
import com.shinhan.connector.dto.response.ScheduleResponse;
import com.shinhan.connector.entity.MySchedule;
import com.shinhan.connector.entity.Schedule;
import com.shinhan.connector.repository.*;
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
    private final ScheduleQueryDslRepository scheduleQueryDslRepository;
    private final MyScheduleRepository myScheduleRepository;
    private final MyScheduleQueryDslRepository myScheduleQueryDslRepository;
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    // 새로운 일정을 추가하는 메서드
    @Transactional
    public List<ScheduleAddResponse> addSchedule(ScheduleAddRequest addRequest, UserDetailsImpl user) {
        log.info("[일정 등록] 일정등록 요청. {}, {}", addRequest.toString(), user.getUserId());

        // 요청에 회원 정보 추가
        addRequest.setMember(memberRepository.findById(user.getId())
                .orElseThrow(NoSuchElementException::new));

        if (addRequest.getFriendNo() == null) {
            MySchedule mySchedule = addRequest.toMyScheduleEntity();

            // 반복 주기에 맞춰 엔티티 생성 후 저장
            return myScheduleRepository.saveAll(MySchedule.generateMySchedules(mySchedule)).stream()
                    .map(ScheduleAddResponse::fromMyScheduleEntity)
                    .collect(Collectors.toList());
        } else {
            // 요청에 친구 정보 추가
            addRequest.setFriend(friendRepository.findById(addRequest.getFriendNo())
                    .orElseThrow(NoSuchElementException::new));
            Schedule schedule = addRequest.toScheduleEntity();

            return scheduleRepository.saveAll(Schedule.generateSchedules(schedule)).stream()
                    .map(ScheduleAddResponse::fromScheduleEntity)
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    public ResponseMessage deleteSchedule(Integer scheduleNo, String option, UserDetailsImpl user) {
        if (option.equals("mine")) {
            myScheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId());
            myScheduleRepository.deleteById(scheduleNo);
        } else {
            scheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId());
            scheduleRepository.deleteById(scheduleNo);
        }

        return new ResponseMessage("삭제가 완료되었습니다.");
    }

    @Transactional(readOnly = true)
    // 일정을 상세조회하는 메서드
    public ScheduleResponse selectSchedule(Integer scheduleNo, String option, UserDetailsImpl user) {
        if (option == null) {
            return ScheduleResponse.fromScheduleEntity(scheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId()));
        } else if (option.equals("mine")){
            return ScheduleResponse.fromMyScheduleEntity(myScheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId()));
        } else {
            throw new NoSuchElementException();
        }
    }

    // 일정 목록을 조회하는 메서드
    @Transactional(readOnly = true)
    public List<ScheduleListResponse> selectAllSchedule(SearchCondition searchCondition, UserDetailsImpl user) {
        // 일정 목록 모두 불러오기
        List<ScheduleListResponse> schedules =
                scheduleQueryDslRepository.getListByCondition(searchCondition, user.getId()).stream()
                .map(ScheduleListResponse::fromScheduleEntity)
                .collect(Collectors.toList());
        // 내 일정 목록 모두 불러와서 추가하기
        schedules.addAll(myScheduleQueryDslRepository.getListByCondition(searchCondition, user.getId()).stream()
                .map(ScheduleListResponse::fromMyScheduleEntity)
                .collect(Collectors.toList()));

        // 오래된 날짜부터 조회
        schedules.sort(Comparator.comparingLong(ScheduleListResponse::getDate));

        return schedules;
    }

    @Transactional
    public ScheduleResponse updateSchedule(Integer scheduleNo, String option, ScheduleUpdateRequest request, UserDetailsImpl user) {
        log.info("[일정 수정] 일정수정 요청. {}, {}, {}", scheduleNo, option, request.toString());

        if (option == null) {
            // 엔티티 조회해서 수정
            Schedule schedule = scheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId());
            schedule.update(request);

            // 수정사항 업데이트
            scheduleRepository.save(schedule);
            scheduleRepository.flush();

            // API 응답 생성
            return ScheduleResponse.fromScheduleEntity(schedule);
        } else if (option.equals("mine")) {
            // 엔티티 조회해서 수정
            MySchedule mySchedule = myScheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId());
            mySchedule.update(request);

            // 수정사항 업데이트
            myScheduleRepository.save(mySchedule);
            myScheduleRepository.flush();

            // API 응답 생성
            return ScheduleResponse.fromMyScheduleEntity(mySchedule);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
