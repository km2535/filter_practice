package com.example.filter.aop;

import com.example.filter.model.UserRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Component
public class TimerAop {

    @Pointcut(value = "within(com.example.filter.controller.UserApiController)")
    public void timerPointCut(){

    }

    @Before(value = "timerPointCut()")
    public void before(JoinPoint joinPoint){
        System.out.println("before");
    }

    @After(value = "timerPointCut()")
    public void after(JoinPoint joinPoint){
        System.out.println("after");
    }

    @AfterReturning(value = "timerPointCut()", returning = "returnValue")
    public void afterReturn(JoinPoint joinPoint, Object returnValue){
        System.out.println("after returning");
    }

    @AfterThrowing(value = "timerPointCut()", throwing = "returnValue")
    public void afterThrowing(JoinPoint joinPoint, Throwable returnValue){
        System.out.println("after throwing");
    }

    @Around(value = "timerPointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("메소드 실행 이전");
        Arrays.stream(joinPoint.getArgs()).forEach(
                it -> {
                    if(it instanceof UserRequest){
                        UserRequest tempUser = (UserRequest) it;
                        var phoneNumber = tempUser.getPhoneNumber().replace("-","");
                        tempUser.setPhoneNumber(phoneNumber);
                    }
                }
        );
        var newObject = Arrays.asList(
                new UserRequest()
        );
        var stopWatch = new StopWatch();
        stopWatch.start();
        joinPoint.proceed(newObject.toArray());
        stopWatch.stop();
        System.out.println("소요 시간 : "+stopWatch.getTotalTimeMillis());

        joinPoint.proceed();
        System.out.println("메소드 실행 이후");
    }
}
