package com.shinhan.connector.repository;

import com.shinhan.connector.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findMemberById(String memberId);
    boolean existsMemberById(String memberId);
}
