package com.shinhan.connector.service;

import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.dto.ScheduleAddRequest;
import com.shinhan.connector.dto.ScheduleAddResponse;
import com.shinhan.connector.entity.Schedule;
import com.shinhan.connector.repository.FriendRepository;
import com.shinhan.connector.repository.MemberRepository;
import com.shinhan.connector.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    // 새로운 일정을 추가하는 메서드
    // TODO: memberId를 JWT 로직으로 변경
    public ScheduleAddResponse addSchedule(ScheduleAddRequest request) {
        // 저장할 엔티티 생성
        Schedule schedule = request.toScheduleEntity();
        schedule.setFriend(friendRepository.findById(request.getFriendNo()).orElseThrow(NoSuchElementException::new));
        schedule.setMember(memberRepository.findMemberById(request.getMemberId()).orElseThrow(NoSuchElementException::new));

        // 생성한 엔티티 저장
        scheduleRepository.save(schedule);
        scheduleRepository.flush();

        // API 응답 생성
        return new ScheduleAddResponse(schedule);
    }

    public ResponseMessage deleteSchedule(Integer scheduleNo) {
        scheduleRepository.deleteById(scheduleNo);

        return new ResponseMessage("삭제가 완료되었습니다.");
    }
}
