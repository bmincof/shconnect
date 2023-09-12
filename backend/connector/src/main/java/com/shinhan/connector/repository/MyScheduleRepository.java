package com.shinhan.connector.repository;

import com.shinhan.connector.entity.MySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyScheduleRepository extends JpaRepository<MySchedule, Integer> {
    @Query(value = "select * from my_schedule where member_no = :memberNo", nativeQuery = true)
    List<MySchedule> findByMember(@Param("memberNo") Integer memberNo);
}
