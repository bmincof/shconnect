package com.shinhan.connector.entity;

import com.shinhan.connector.enums.Gender;
import com.sun.istack.NotNull;
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
    @Column(length = 1, nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(length = 50, nullable = false)
    private String contact;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_no")
    private Account account;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<SavingsLetter> savingsLetters;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Friend> friends;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<MySchedule> mySchedules;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Schedule> schedules;
}
