package com.shinhan.connector.entity;

import com.shinhan.connector.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_no")
    private Integer no;
    @Column(length = 50, unique = true)
    private String id;
    @Column(length = 200, nullable = false)
    private String password;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(length = 50)
    private String contact;


    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    List<SavingsLetter> savingsLetters;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    List<Friend> friends;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    List<MySchedule> mySchedules;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    List<Schedule> schedules;
}
