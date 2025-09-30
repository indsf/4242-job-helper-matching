package com.test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* com.test..*(..))") // 대상 메서드 실행 전후 , 예외 발생 시점까지 전부 개입 할 어드바이스
    // 하위 템플릿 다 실행함
    public Object execute(ProceedingJoinPoint joinPoint) throws  Throwable {
        long start = System.currentTimeMillis();
        try {
            //다음 메소드로 진입

            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("End: " +   joinPoint.toLongString()+ " " + timeMs + "ms");
        }
    }
}
