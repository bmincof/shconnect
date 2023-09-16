package com.shinhan.connector.repository;

import com.shinhan.connector.entity.GiftLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftLogRepository extends JpaRepository<GiftLog, Integer> {
    @Query(value = "select * from gift_log where age_range = :ageRange and gender = :gender and category = :category order by count desc limit 1",
            nativeQuery = true)
    GiftLog findByCondition(Integer ageRange, String gender, String category);
}
