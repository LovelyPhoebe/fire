package com.lindog.fire.Aspect;


import com.lindog.fire.anno.InjectValue;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class InjectAspect {
    @Around("@annotation(injectValue)")
    public Object injectValue(ProceedingJoinPoint proceedingJoinPoint, InjectValue injectValue) throws Throwable {
        // 获取原方法的参数
        Object[] oriArgs = proceedingJoinPoint.getArgs();
        log.info("oriArgs = {}", Arrays.toString(oriArgs));

        // 获取注解中的值
        String injectedValue = injectValue.value();
        log.info("inject value = {}", injectedValue);

        // 执行目标方法并传入修改后的参数
        return proceedingJoinPoint.proceed();
    }


}
