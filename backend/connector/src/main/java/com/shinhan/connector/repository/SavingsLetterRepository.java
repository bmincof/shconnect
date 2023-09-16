package com.shinhan.connector.repository;

import com.shinhan.connector.entity.SavingsLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SavingsLetterRepository extends JpaRepository<SavingsLetter, Integer> {

    @Query(value = "SELECT * FROM savings_letter WHERE member_no = :memberNo", nativeQuery = true)
    List<SavingsLetter> findSavingsLettersByMemberNo(Integer memberNo);
}
