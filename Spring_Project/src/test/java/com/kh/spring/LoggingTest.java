package com.kh.spring;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j//자동으로 Logger 객체가 생성되는 효과(객체명이 log)
public class LoggingTest {
    //테스트용 메소드 생성
    @Test//main 메소드를 만든 효과를 줌(junit)
    public void test() {
        //JUnit:자바 프로그래밍 언어용 유닛 테스트 프레임워크
        //System.out.println("잘 실행되나...?");

        //로깅 수행 도구 생성(Logger 타입의 객체 생성)
        //=>LoggerFactory에서 제공하는 getLogger 메소드 호출하여 생성
        //=>import시 slf4j 관련 패키지의 것으로 추가
        //Logger logger = LoggerFactory.getLogger(LoggingTest.class);

        //Logger의 출력문 종류
        //logger.debug("난 debug야");
        //logger.info("난 info야");
        //logger.warn("난 warn이야");
        //logger.error("난 error야");
        //debug 메소드 호출 내용이 콘솔에 찍히지 않음
        //=>log4j 설정파일에서 확인

        //어노테이션을 이용하여 Logger 객체를 만들경우 
        log.debug("난 debug");
        log.info("난 info");
        log.warn("난 warn");
        log.error("난 error");
    }
}
