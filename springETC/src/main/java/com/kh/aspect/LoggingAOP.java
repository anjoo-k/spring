package com.kh.aspect;


import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


/*
 *  - @Aspect : 해당 클래스가 Aspect라는 것을 선언한다.
 *  - @Componenet : Spring 해당 빈을 서칭할 수 있도록 선언함
 *  - @EnableAspectJAutoProxy : aop를 활성화시켜주는 선언
 *  
 */
@Aspect
@Component
@Slf4j
@EnableAspectJAutoProxy
public class LoggingAOP {
	
	/*
	 * <시점>
	 * @Before : 대상메서드 실행 전에 Advice(추가기능)이 실행된다.
	 * 
	 * @After : 대상 메서드 실행 후에 Advice(추가기능)이 실행된다.
	 * 
	 * @AfterReturning : 대상 메서드가 정상적으로 반환된 후에 Advice가 실행됩니다.
	 * 
	 * @AfterThrowing : 대상 메서드가 예외를 던진 후에 Advice가 실행된다.
	 * 
	 * @Around : 대상메서드를 감싸서 메서드 호출 전후에 Advice를 실행할 수 있다.
	 * 
	 */
	
	
	/*
	 * <대상>
	 * target : 특정 인터페이스와 그 자식클래스
	 * within : 특정 패키지 또는 클래스
	 * execution : 표현식으로 형태지정
	 * 
	 */
	
	//리턴타입, 파라미터, 예외
	//@Pointcut - 내가 기능을 사용할 지점을 정의 (..)은 리턴타입은 무관하다. 그 앞은 적용되는 부분 패키지 안의 클래스
	//com.kh.spring 패키지 하위 패키지 중 controller 내에 있는 모든 클래스의 모든 메서드
	@Pointcut("execution(* com.kh.spring..controller.*.*(..) )")
	private void cut() {}
	
//	@Before("target(com.kh.spring.board.controller)").
	@Before("cut()") // cut 메서드가 실행되는 지금 이전에 before() 메서드를 실행
	public void before(JoinPoint joinPoint) {
	//JoinPoint는 프로그램 실행 중 특정 지점을 나타내며, 메서드 실행이 가장 일반적인 JoinPoint이다.: 어디를 자르고 들어간건지
		
		//실행되는 메서드의 이름을 가져오기
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		
		//메서드에 들어가는 매개변수 배열을 읽어온다.
		Object[] args = joinPoint.getArgs();
		
		log.info("====================================START==============================");
		log.info("-----------------------------------API Controller------------------------------");
		log.info("Information      :     " + methodSignature);
		log.info("Method Name      :     " + method);
		log.info("Parameter        :     " + Arrays.toString(args));
		
	}
	
	//지피티의 추천을 받아서 만들기

	@AfterReturning(value= "cut()", returning = "obj")
	public void afterReturn(JoinPoint joinPoint, Object obj) {
		log.info("====================================END==============================");
		log.info("Object      :     " + obj);
		
	}
	
	//api 시간 측정
	@Around("cut()")
	public Object displayLogInfo(ProceedingJoinPoint pJoinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		
		Object result = pJoinPoint.proceed(); // 원래 해야되는 기능을 실행해준다.
		
		long end = System.currentTimeMillis();
		
		long pTime = end - start;
		log.info("-----------------------------------------------------------------------");
		log.info("Information      :     " + pJoinPoint.getSignature());
		log.info("Processing Time      :     " + pTime + "ms");
		
		return result;
	}
	
}
