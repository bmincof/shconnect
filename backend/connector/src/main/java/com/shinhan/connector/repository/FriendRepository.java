package com.shinhan.connector.repository;

import com.shinhan.connector.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Integer> {

    @Query(value = "select * from friend where member_no = :memberNo", nativeQuery = true)
    List<Friend> findByMember(@Param("memberNo") Integer memberNo);
}
