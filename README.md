# Firebase Analytics - Toy Project Version 2

- ## 개요
  - Firebase Analytics를 사용 하기 위해 Events 관련하여 analytics에 적용 되는 시간, 방식을 기록하기 위해 toy project를 진행했다.  - version 1 은 사람이 직접 이벤트를 발생 시켜야 하기 때문에 폐기

  - 기능적 부분과 UI 적인 요소를 제외하고 Events 만 날려주는 Project

  - Event를 하나하나 시간 별로 날려주는 것은 힘들다고 판단하여 Service 에서 timer를 이용하여 Events를 발생 시킨다. 

  - 해당 기기(공기계 or Emulator)의 Background 에서 일정 시간을 두고 Events를 서버로 전송함

    - Service 에서 timer 를 이용하여 구현

      ```kotlin
      //1second * seconds * minutes * hours
      timerForTutorial = timer(period = Constant.MILLSTIME * 60 * 60 * 1L) {
             FirebaseAnalyticsManager(Constant.TYPE_TUTORIAL, context)
      }
      timerForLogin = timer(period = Constant.MILLSTIME * 60 * 60 * 2L) {
             FirebaseAnalyticsManager(Constant.TYPE_LOGIN, context)
      }
      timerForSignUp = timer(period = Constant.MILLSTIME * 60 * 60 * 3L) {
             FirebaseAnalyticsManager(Constant.TYPE_SIGN_UP, context)
      }
      ```



---

- ## 이벤트 리스트 및 이벤트 전송시간

  - 체험하기 (Tutorial begin, complete) 이벤트 - 1시간
  - 로그인 (Login) - 2시간
  - 회원가입  (Signup) - 3시간



---

- ## Class 구성

  - **Main Activity**
    - 조작 담당 - Button을 통해 해당하는 기능을 수행
      - button
        - start - events service 시작 (service가 하나만 돌아가도록 `isServiceRunning` 함수를 이용하여 판단)
        - stop - events service stop - `stopService` 를 이용해 FirebaseService 정지
        - end - 해당 application 을 `exitProcess` Function 으로 종료
  - **FirebaseService**
    - Background 에서  수행되는 Service Class
    - 해당 events의 시간별로 **[timer](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.concurrent/timer.html)** 를 이용하여 FirebaseAnalyticsManager 호출
    - `onDestroy` 시에 돌아가고 있는 timer 들 종료
  - **FirebaseAnalyticsManager**
    - FirebaseAnalytics 로의 직접적인 접근을 하는 class
    - `logEvent` 를 이용하여 해당하는 Event( FirbaseAnalytics 내부 정의 )를 서버로 전송
      - LOGIN, TUTORIAL_BEGIN, TUTORIAL_COMPLETE, SIGN_UP
  - **Constant** 
    - 상수 값 담당

- **Library**
  - Firebase 연동 - Analytics 기능만 사용 하기에 해당 library만 사용

    ```
    classpath 'com.google.gms:google-services:4.3.3'
    ```



---

- ## 프로그램 동작
  - 프로그램 상태에 따라 가운에 text Status의 글자가 변경
  - Program Ready
    - Program 시작 및 Service 준비 상태
  - `START` Button 클릭
    -  Program Running -> FirebaseService 호출 
    - 실행중인 Service 존재 시에는 새로운 Service 호출 불가
    - background에서 Service 가 FirebaseAnalytics Manager 호출
  - FirebaseAnalytics Manger 에서 시간별로 events 발생
  - `STOP` Button 클릭
    -  Program STOP - Service를 stop
  - `END`
    - Program Shut down - 실행중인 activity 와 service 모두 정리



---

- ## 결론

  - FirebaseAnalytics 의 이벤트 반영은 상당한 시간이 소요된다.

    - 따라서 debugView를 통하여 이벤트가 정상 발생하고 있는지 check 가 필요하다.

  - Background 로 Activity를 넘기면 2분이 되기 전 Service가 죽는 현상이 서비스 창에서 보였기에 foreground 에서 app을 실행 시켜 둔 상태로 두어야 하는 불편함이 발생함

  - FirebaseAnalytics 에 미리 내제 된 Event 와 Params 를 이용하여 전송도 가능하지만

    사용자가 직접적으로 정의한 Events도 전송이 가능하다.

  - 정말 간단한 Toy Project 여서 [Funnels (유입경로)](https://support.google.com/firebase/answer/6317523?hl=ko) 판단이 힘들다.