package com.shinhan.connector.repository;

import com.shinhan.connector.entity.MySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MyScheduleRepository extends JpaRepository<MySchedule, Integer> {
    @Query(value = "select * from schedule where root_schedule_no = :rootNo and date > :date", nativeQuery = true)
    List<MySchedule> findByRootNoAndDate(@Param("rootNo") Integer rootNo, @Param("date") Long date);
}
