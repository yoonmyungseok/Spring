package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Lombok(롬복) 
 * 자동 코드 완성 라이브러리
 * 반복되는 getter, setter, toString, 생성자 등의 메소드 작성 코드를 줄여주는 라이브러리
 * 
 * Lombok 설치 방법
 * 1. 라이브러리 다운 후 적용(Maven pom.xml)
 * 2. 다운로드된 jar 파일을 찾아서 실행(작업 할 IDE를 선택)
 * 3. IDE 재실행
 * 명명법 때문에 팀원이 전부 사용할건지 안할건지 룰을 맞춰줘야함
 * 명명법 때문에 필드명의 가장 앞 단어가 외자가 되면 안됨(ex: userNo⇒O, uNo⇒X)
 */

@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 갖는 생성자
@Setter // setter 메소드들
@Getter // getter 메소드들
@ToString // toString 메소드

public class Member {
    private String userId;
    private String userPwd;
    private String userName;
    private String email;
    private String gender;
    // private int age;
    private String age; // 400에러 해결방법
    private String phone;
    private String address;
    private Date enrollDate;
    private Date modifyDate;
    private String status;

}
