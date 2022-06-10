<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>title</title>
</head>

<body>
    <!--
        * Spring Framework의 특징
        1. IOC(Inversion Of Control)
        제어의 역전=>   객체들을 관리할 권한이 개발자가 아닌 Spring에게 있다.
                        내가 직접적으로 new 구문을 이용하여 객체를 생성할 일이 없다.
        2. DI(Dependency Injection)
        의존성 주입=>   객체간의 의존관계를 알아서 설정해준다.
                        예)Controller=>Service=>Dao 객체 생성
        3. POJO(Plain Old Java Object)
        내가 직접 만든 클래스를 지칭=>POJO Class
        다른 프레임워크들은 대부분 내가 직접 만든 클래스를 혼용해서 쓸 수 없게 막혀있는데
        Spring이나 MyBatis는 내가 직접만든 클래스를 혼용해서 쓸 수 있다 라는 특징

        4. Spring AOP(Aspect Oriented Programming)
        관점지향 프로그래밍=>객체지향프로그래밍을 보완한 개념
                            객체지향에서도 부족했던 재활용성, 코드의 중복을 더 줄여서 모듈화하게 도와주는 기법
        
        5. Spring JDBC
        영속성 작업과 관련된 프레임워크들과의 연동을 지원함(MyBatis와 결합해서 사용 가능)

        6. Spring MVC
        MVC 패턴으로 코딩하기 위한 Model, View, Controller 간의 관계를 알아서 IOC, DI를 통해서 관리해주겠다

        7. PSA(Portable Service Abstraction)
        수많은 모듈들을 갖다 붙여 쓸 수 있는데 일일이 연동해주는 것이 아니라 나중에 모듈만 바꿔치기 할 수 있게끔 도와주겠다.
        
    -->
    여긴 index.jsp야
    <jsp:forward page="WEB-INF/views/main.jsp"/>
</body>

</html>