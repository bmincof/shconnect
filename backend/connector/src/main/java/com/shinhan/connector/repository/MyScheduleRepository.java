package com.shinhan.connector.repository;

import com.shinhan.connector.entity.MySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyScheduleRepository extends JpaRepository<MySchedule, Integer> {
}
