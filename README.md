## ✨Structure
### 프로젝트 구조
```text
api-server-spring-boot
  > * build
  > gradle
  > src.main.java.com.example.demo
    > common
      > config
        | RestTemplateConfig.java // HTTP get,post 요청을 날릴때 일정한 형식에 맞춰주는 template
        | SwaggerConfig.java // Swagger 관련 설정
        | WebConfig.java // Web 관련 설정(CORS 설정 포함)
      > entity
        | BaseEntity.java // create, update, state 등 Entity에 공통적으로 정의되는 변수를 정의한 BaseEntity
      > exceptions
        | BaseException.java // Controller, Service에서 Response 용으로 공통적으로 사용 될 익셉션 클래스
        | ExceptionAdvice.java // ExceptionHandler를 활용하여 정의해놓은 예외처리를 통합 관리하는 클래스
      > log
        > aop
          | LogAspect // 로그 AOP 기능 클래스 !
          | UserPointcuts // User 관련 Pointcut ! 
        > entity
          | Log
        | LogController
        | LogRepository
        | LogService
      > oauth
        | GoogleOauth.java // Google OAuth 처리 클래스
        | OAuthService.java // OAuth 공통 처리 서비스 클래스
        | SocialOauth.java // OAuth 공통 메소드 정의 인터페이스
      > response
        | BaseResponse.java // Controller 에서 Response 용으로 공통적으로 사용되는 구조를 위한 모델 클래스
        | BaseResponseStatus.java // Controller, Service에서 사용할 Response Status 관리 클래스 
      > secret
        | Secret.java // jwt 암호키 보관 클래스
      | Constant // 상수 보관 클래스
    > src
      > test
        > entity
          | Comment.java // Comment Entity
          | Memo.java // Memo Entity
        > model
          | GetMemoDto.java
          | MemoDto.java
          | PostCommentDto.java
        | TestController.java // Memo API Controller
        | TestService.java // Memo API Service
        | MemoRepository.java // Memo Spring Data JPA
        | CommentRepository.java // Comment Spring Data JPA
      > user
        > entity
          | User.java // User Entity
        > model
          | GetSocialOAuthRes.java // OAuth 인증 관련 DTO(토튼 정보)
          | GetUserRes.java    
          | GoogleUser.java // OAuth 인증 관련 DTO(유저 정보)
          | PatchUserReq.java
          | PostLoginReq.java
          | PostLoginRes.java 
          | PostUserReq.java 
          | PostUserRes.java 
        > scheduler
          | ConsentScheduler // 동의 처리 스케줄러  
        | UserController.java
        | UserService.java
        | UserRepository.java
    > utils
      | JwtService.java // JWT 관련 클래스
      | SHA256.java // 암호화 알고리즘 클래스
      | ValidateRegex.java // 정규표현식 관련 클래스
    | DemoApplication // SpringBootApplication 서버 시작 지점
  > resources
    | application.yml // Database 연동을 위한 설정 값 세팅 및 Port 정의 파일
    | logback-spring.xml // logback 설정 xml 파일
build.gradle // gradle 빌드시에 필요한 dependency 설정하는 곳
.gitignore // git 에 포함되지 않아야 하는 폴더, 파일들을 작성 해놓는 곳

```

### ERD
![Gridge-Test](https://github.com/user-attachments/assets/1899ad73-cccc-4681-9bc2-bd0bd677d947)


## ✨Usage
### 실행 방법
1. 터미널에서 **git clone https://github.com/qlqlrh/Gridge-Test.git** 명령어 입력
2. 인텔리제이에서 받은 폴더 열고, **Run > Edit Configuration > Add VM options**에 **-Dspring.profiles.active=dev** 추가
3. `DemoApplicaiton.java` 파일 실행
4. http://localhost:9000/swagger-ui.html 에 접속해서 api 확인 가능


### 참고 사항
- 인증 성공 이후 서버로 보내는 모든 HTTP 요청들에 Authorization 헤더 값을 입력해줘야 하는 번거로움을 줄이기 위해 Swagger UI 페이지의 상단에 Authorize 버튼을 추가했다.
- 보통 Authorization: “Bearer <token>” 형식을 사용하지만, 제공된 템플릿 jwtService 코드에서 X-ACCESS-TOKEN을 사용하고 있어서 그대로 유지하였다.
- 로그인 후 발급받은 jwt를 swagger 페이지 오른쪽 상단에 있는 Authorize에 넣으면 된다.

- 구현 항목 : User 관련 기능과 Logging 관련 기능
- 미구현 항목 : 게시글 기능, 결제 기능, 소셜 로그인 기능


## ✨License
- 본 템플릿의 소유권은 소프트스퀘어드에 있습니다. 본 자료에 대한 상업적 이용 및 무단 복제, 배포 및 변경을 원칙적으로 금지하며 이를 위반할 때에는 형사처벌을 받을 수 있습니다.