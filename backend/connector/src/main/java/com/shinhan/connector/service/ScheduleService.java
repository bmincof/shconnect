package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.dto.request.ScheduleAddRequest;
import com.shinhan.connector.dto.request.ScheduleUpdateRequest;
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
    public List<ScheduleListResponse> selectAllSchedule(String startDate, String endDate, UserDetailsImpl user) {
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
