package ru.clevertec.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LogImpl {

    @Before("@annotation(ru.clevertec.aspect.Log)")
    public void beforeLog(JoinPoint jp) {
//        String signature = jp.getSignature().toString();
//        System.out.println("@Before " + signature);
//        System.out.println("111111111111111111111");
    }
}
