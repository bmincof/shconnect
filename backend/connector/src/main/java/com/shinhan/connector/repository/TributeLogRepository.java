package com.shinhan.connector.repository;

import com.shinhan.connector.entity.TributeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TributeLogRepository extends JpaRepository<TributeLog, Integer> {
    @Query(value = "select * from tribute_log where age_range = :ageRange and gender = :gender and category = :category",
            nativeQuery = true)
    TributeLog findByCondition(Integer ageRange, String gender, String category);
}
