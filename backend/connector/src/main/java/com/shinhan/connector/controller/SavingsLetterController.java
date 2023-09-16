package com.shinhan.connector.controller;

import com.shinhan.connector.config.TimeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/savings-letter")
public class SavingsLetterController {

    @GetMapping("/test")
    public LocalDateTime test(Long timeStamp) {
        return TimeUtils.UNIX_TO_DATE(timeStamp);
    }
}
