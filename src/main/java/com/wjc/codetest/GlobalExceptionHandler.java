package com.wjc.codetest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(value = {"com.wjc.codetest.product.controller"})
/*
    문제 : 적용 범위가 특정 controller로 제한됨
    원인 : 확장성 고려 부족
    개선안 : 전체 controller일 경우 생략 고려
 */
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> runTimeException(Exception e) {
        /*
            문제 : ExceptionHandler는 runTimeException 클래스를 처리하는데 명확하지 않음
            원인 : 예외타입 일관성 부족
            개선안 : RuntimeException으로 명시
         */
        log.error("status :: {}, errorType :: {}, errorCause :: {}",
                HttpStatus.INTERNAL_SERVER_ERROR,
                "runtimeException",
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        /*
            문제 : ResponseEntity 반환이지만 바디가 없음
            원인 : 에러 응답 부재
            개선안 : ErrorResponse 같은 DTO 도입
         */
    }
}
