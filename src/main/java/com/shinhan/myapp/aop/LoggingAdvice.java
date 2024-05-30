package com.shinhan.myapp.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//보조업무(cross cutting concern)
@Component
//@Aspect // AOP를 Anotation으로 이용하기! @Aspect, advisor = 보조업무(advice) + pointcut
public class LoggingAdvice {

	@Pointcut("execution(* selectAll())")
	public void targetMethod2() {
	}
	
	@Pointcut("within(com.shinhan.myapp.model.BoardService)")
	public void targetMethod3() {
		
	
	}

	@Before("targetMethod3()")
	public void f_before() {
		System.out.println("------@Before-----");
	}

	@After("targetMethod3()")
	public void f_after() {
		System.out.println("------@After-----");
	}

	@AfterReturning("targetMethod3()")
	public void f_afterReturn() {
		System.out.println("------@AfterReturning-----");
	}

	// target의 주업무 수행 전후 = around
	@Around("targetMethod2()")
	public Object aroundMethod(ProceedingJoinPoint jp) throws Throwable { // 함수이름이 무엇인지 알 수 있다.

		System.out.println("!!!! [메서드 호출 전 : LogginAdvice around()"); // 보조업무
		System.out.println(jp.getSignature().getName() + "메서드 호출 전"); // 보조업무

		// 주업무를 수행한다. (주의!!!)
		Object object = jp.proceed();

		// 주업무 수행후 돌아와서 수행한다.
		System.out.println("[메서드 호출 후 : loggingAdvice around()"); // 보조업무
		System.out.println(jp.getSignature().getName() + "메서드 호출 후"); // 보조업무
		return object;
	}
}