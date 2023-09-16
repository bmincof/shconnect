package com.shinhan.connector.repository;

import com.shinhan.connector.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query(value = "select * from schedule where root_schedule_no = :rootNo and date > :date", nativeQuery = true)
    List<Schedule> findByRootNoAndDate(@Param("rootNo") Integer rootNo, @Param("date") Long date);
}
