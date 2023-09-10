package com.shinhan.connector.controller;

import com.shinhan.connector.dto.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ResponseMessage> handleAll(final Exception ex) {
        log.error(ex.getClass().getName());
        return ResponseEntity.internalServerError().body(new ResponseMessage("내부 에러 발생. 관리자에게 문의해주세요."));
    }
}
