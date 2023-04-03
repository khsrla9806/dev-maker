# 개발자 만들기 프로젝트

### 🛠️ 기술 스택

| Springboot | version 2.5.2 |
| --- | --- |
| java (JDK) | version 11 |
| build | Gradle |
| database | H2 |
| IDE | IntelliJ IDEA |

### 🔗 Dependencies

| org.springframework.boot:spring-boot-starter-data-jpa |
| --- |
| org.springframework.boot:spring-boot-starter-validation |
| org.springframework.boot:spring-boot-starter-web |
| org.projectlombok:lombok |
| com.h2database:h2 |
| org.springframework.boot:spring-boot-starter-test |

### 🖥️ API

1. **개발자 등록하기**
   - `POST`
   - `/create-developer`
   - [Request]

       ```java
       {
           "developerLevel" : ["NEW", "JUNIOR", "JUNGNIOR", "SENIOR" 중 1개],
           "developSkillType" : ["FRONT_END", "BACK_END", "FULL_STACK" 중 1개],
           "experienceYears" : 0 ~ 20 사이의 정수,
           "memberId" : 3 ~ 50자로 이루어진 문자열,
           "name" : 3 ~ 20자로 이루어진 문자열,
           "age" : 최소 18인 정수 
       }
       ```

   - [Response]

       ```java
       {
           "developerLevel" : [request로 들어온 developerLevel],
           "developSkillType" : [request로 들어온 developSkillType],
           "experienceYears" : [request로 들어온 experienceYears],
           "memberId" : [request로 들어온 memberId]
       }
       ```


2. **재직중인 모든 개발자 정보 가져오기**
   - `GET`
   - `/developers`
   - [Response]

       ```java
       [
         {
           "developerLevel": "개발자1의 레벨",
           "developSkillType": "개발자1의 기술",
           "memberId": "개발자1 ID"
         },
           {
           "developerLevel": "개발자2의 레벨",
           "developSkillType": "개발자2의 기술",
           "memberId": "개발자2 ID"
         }
       ]
       ```


3. **특정 개발자 정보 가져오기**
   - `GET`
   - `/developers/{memberId}`
   - [Response]

       ```java
       {
         "developerLevel": "개발자의 레벨",
         "developSkillType": "개발자의 기술",
         "experienceYears": 개발자 경력,
         "stateCode": "개발자 재직 상태",
         "memberId": "개발자 Id",
         "name": "개발자 이름",
         "age": 개발자 나이
       }
       ```

       ```java
       // 요청한 Id를 갖는 개발자가 없을 때
       {
         "errorCode": "NO_DEVELOPER",
         "errorMessage": "해당되는 개발자가 없습니다."
       }
       ```


4. **개발자 정보 수정하기**
   - `PUT`
   - `/developer/{memberId}`
   - [Request]

       ```java
       {
           "developerLevel" : "수정하고자 하는 개발자의 레벨",
           "developSkillType" : "수정하고자 하는 개발자의 기술",
           "experienceYears" : 수정하고자 하는 개발자의 경력
       }
       ```

   - [Response]

       ```java
       {
         "developerLevel": "수정한 개발자 레벨",
         "developSkillType": "수정한 개발자 기술",
         "experienceYears": 수정한 개발자 경력,
         "stateCode": "개발자의 재직상태",
         "memberId": "개발자의 Id",
         "name": "개발자의 이름",
         "age": 개발자의 나이
       }
       ```

       ```java
       // 수정하고자하는 개발자가 없는경우
       {
           "errorCode": "NO_DEVELOPER",
         "errorMessage": "해당되는 개발자가 없습니다."
       }
       ```

5. **개발자 삭제하기**
   - `Delete`
   - `/developer/{memberId}`
   - [Response]

       ```java
       {
         "developerLevel": "삭제된 개발자의 레벨",
         "developSkillType": "삭제된 개발자의 기술",
         "experienceYears": 삭제된 개발자의 경력,
         "stateCode": "RETIRED", // 삭제 시 은퇴로 처리
         "memberId": "삭제된 개발자의 Id",
         "name": "삭제된 개발자의 이름",
         "age": 삭제된 개발자의 나이
       }
       ```

       ```java
       // 삭제하고자하는 개발자가 없는경우
       {
         "errorCode": "NO_DEVELOPER",
         "errorMessage": "해당되는 개발자가 없습니다."
       }
       ```