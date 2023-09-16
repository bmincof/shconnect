package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.dto.request.ScheduleAddRequest;
import com.shinhan.connector.dto.request.ScheduleUpdateRequest;
import com.shinhan.connector.dto.request.SearchCondition;
import com.shinhan.connector.dto.response.*;
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
            MySchedule mySchedule = myScheduleRepository.saveAndFlush(addRequest.toMyScheduleEntity());

            mySchedule.setRootNo(mySchedule.getNo());

            // 반복 주기에 맞춰 엔티티 생성 후 저장
            return myScheduleRepository.saveAll(MySchedule.generateMySchedules(mySchedule)).stream()
                    .map(ScheduleAddResponse::fromMyScheduleEntity)
                    .collect(Collectors.toList());
        } else {
            // 요청에 친구 정보 추가
            addRequest.setFriend(friendRepository.findById(addRequest.getFriendNo())
                    .orElseThrow(NoSuchElementException::new));
            Schedule schedule = scheduleRepository.saveAndFlush(addRequest.toScheduleEntity());

            schedule.setRootNo(schedule.getNo());

            return scheduleRepository.saveAll(Schedule.generateSchedules(schedule)).stream()
                    .map(ScheduleAddResponse::fromScheduleEntity)
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    public ResponseMessage deleteSchedule(Integer scheduleNo, String option, UserDetailsImpl user) {
        if (option == null) {
            myScheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId());
            myScheduleRepository.deleteById(scheduleNo);
        } else if (option.equals("mine")) {
            scheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId());
            scheduleRepository.deleteById(scheduleNo);
        } else {
            throw new NoSuchElementException();
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

    // 일정의 모든 선물 목록을 조회하는 메서드
    public List<GiftResponse> selectAllGiftsBySchedule(Integer scheduleNo, String option, UserDetailsImpl user) {
        if (option == null) {
            return scheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId())
                    .getGiftSends().stream()
                    .map(GiftSendResponse::new)
                    .collect(Collectors.toList());
        } else if (option.equals("mine")) {
            return myScheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId())
                    .getGiftReceives().stream()
                    .map(GiftReceiveResponse::new)
                    .collect(Collectors.toList());
        } else {
            throw new NoSuchElementException();
        }
    }

    // 일정의 모든 경조사비를 조회하는 메서드
    public List<TributeResponse> selectAllTributesBySchedule(Integer scheduleNo, String option, UserDetailsImpl user) {
        if (option == null) {
            return scheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId())
                    .getTributeSends().stream()
                    .map(TributeResponse::entityToDto)
                    .collect(Collectors.toList());
        } else if (option.equals("mine")) {
            return myScheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId())
                    .getTributeReceives().stream()
                    .map(TributeResponse::entityToDto)
                    .collect(Collectors.toList());
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
                .sorted(Comparator.comparingLong(ScheduleListResponse::getDate)
                        // 둘 다 널이면 순서 유지, null이 더 앞으로, 관계 순
                        .thenComparing((o1, o2) -> {
                            if (o1 == null && o2 == null) {
                                return 0;
                            }
                            if (o1 == null || o2 == null) {
                                return o1 == null ? -1 : 1;
                            }
                            return (o1.getFriend().getRelation().compareTo(o2.getFriend().getRelation()));
                        }))
                .collect(Collectors.toList()));

        return schedules;
    }

    @Transactional
    public List<ScheduleResponse> updateSchedule(Integer scheduleNo, String option, ScheduleUpdateRequest request, UserDetailsImpl user) {
        log.info("[일정 수정] 일정수정 요청. {}, {}, {}", scheduleNo, option, request.toString());

        if (option == null) {
            // 엔티티 조회해서 수정
            Schedule schedule = scheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId());

            // 기존에 있던 이후 일정을 모두 삭제
            List<Schedule> schedules = scheduleRepository.findByRootNoAndDate(schedule.getRootNo(), schedule.getDate());
            scheduleRepository.deleteAll(schedules);

            // 수정사항 업데이트 후 새로운 반복 주기에 맞춰 일정 추가
            schedule.update(request);
            return scheduleRepository.saveAll(Schedule.generateSchedules(schedule)).stream()
                    .map(ScheduleResponse::fromScheduleEntity)
                    .collect(Collectors.toList());
        } else if (option.equals("mine")) {
            // 엔티티 조회해서 수정
            MySchedule mySchedule = myScheduleRepository.findById(scheduleNo)
                    .orElseThrow(NoSuchElementException::new)
                    .isAllowed(user.getId());

            // 기존에 있던 이후 일정을 모우 삭제
            List<MySchedule> mySchedules = myScheduleRepository.findByRootNoAndDate(mySchedule.getRootNo(), mySchedule.getDate());
            myScheduleRepository.deleteAll(mySchedules);

            // 수정사항 업데이트 후 새로운 반복 주기에 맞춰 일정 추가
            mySchedule.update(request);
            return myScheduleRepository.saveAll(MySchedule.generateMySchedules(mySchedule)).stream()
                    .map(ScheduleResponse::fromMyScheduleEntity)
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException();
        }
    }
}
