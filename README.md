# 📆 일정 관리 앱 Develop Project
JPA 를 활용하여 CRUD 기능이 구현된 일정 관리 앱 만들기

## 📌 일정 관리 앱 요구사항

### 📋 일정에 포함되어야 할 데이터
- 일정 테이블: schedules
   1. 작성 유저명: username
   2. 할 일 제목: title
   3. 할 일 내용: contents
   4. 생성일: createdAt
   5. 수정일: modifiedAt

### 👪 유저에 포함되어야 할 데이터
- 유저 테이블: users
  1. 작성 유저명: name
  2. 이메일: email
  3. 생성일: createdAt
  4. 수정일: modifiedAt
  5. (LV3 이후) 비밀번호: password

### 🔍 요구사항
- 일정 <> 유저의 연관 관계 구현
- 작성일 & 수정일은 JPA Auditing 활용
- 통신 데이터 형태(request/response) 는 JSON 형태

## 📜 API 명세서

### HTTP API 설계
- 대부분의 API 는 CRUD 작업을 수행한다.

### 설계 순서
1. HTTP Method
   * POST: CREATE
   * GET: READ
   * PATCH: UPDATE
   * DELETE: DELETE
2. Restful API → URL Mapping
3. 요청 / 응답 데이터 설계
   * HTTP Method + URL 를 통해 어떤 API 인지 구분 가능하다.

### 📬 일정 CRUD
| API          | Method | URL                         | Request  | Request Sample                                                                                                   | Response  | Response Sample                                                                                                                                                                                                                                                                                                                                                                           | HttpStatus Code |
|--------------|--------|-----------------------------|----------|------------------------------------------------------------------------------------------------------------------|-----------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------|
| 일정 생성        | POST   | /api/schedules              | 요청 Body  | 1. 작성 유저명: String(필수) <br> 2. 할 일 제목: String(필수) <br> 3. 할 일 내용: String(필수)                                      | 생성된 일정 정보 | { <br> "id": 1, <br> "username": "Syuare", <br> "title": "Java 공부", <br> "contents": "Java - S.O.L.I.D 에 대해 학습하기", <br> "createAt": "2025-05-19", <br> "modifiedAt": "2025-05-19" <br> }                                                                                                                                                                                                  | 201: 정상 생성      |
| 선택 일정 조회(단건) | GET    | /api/schedules/{scheduleId} | 요청 param | - 일정 ID: Long(필수)                                                                                                | 단건 응답 정보  | { <br> "id": 1, <br> "username": "Syuare", <br> "title": "Java 공부", <br> "contents": "Java - S.O.L.I.D 에 대해 학습하기", <br> "createAt": "2025-05-19", <br> "modifiedAt": "2025-05-19" <br> }                                                                                                                                                                                                  | 200: 정상 조회      |
| 전체 일정 조회     | GET    | /api/schedules              | -        | -                                                                                                                | 다건 응답 정보  | [ <br> { <br> "id": 1, <br> "username": "Syuare", <br> "title": "Java 공부", <br> "contents": "Java - S.O.L.I.D 에 대해 학습하기", <br> "createAt": "2025-05-19", <br> "modifiedAt": "2025-05-19" <br> }, <br> { <br> "id": 2, <br> "username": "Syuare", <br> "title": "저녁 약속", <br> "contents": "친구들과 파전 + 먹걸리 조합", <br> "createAt": "2025-05-19", <br> "modifiedAt": "2025-05-19" <br> } <br> ] | 200: 정상 조회      |
| 선택 일정 수정(단건) | PATCH  | /api/schedules/{scheduleId} | 요청 param | - 요청 ID: Long(필수) <br> 요청 Body: <br> 1. 작성 유저명: String(필수) <br> 2. 할 일 제목: String(필수) <br> 3. 할 일 내용: String(필수) | 수정된 일정 정보 | { <br> "id": 1, <br> "username": "Syuare", <br> "title": "Spring 공부", <br> "contents": "Spring - JPA 에 대해 학습하기", <br> "createAt": "2025-05-19", <br> "modifiedAt": "2025-05-19" <br> }                                                                                                                                                                                                    | 200: 정상 수정      |
| 선택 일정 삭제(단건) | DELETE | /api/schedules/{scheduleId} | 요청 param | - 요청 ID: Long(필수)                                                                                                | X         | X                                                                                                                                                                                                                                                                                                                                                                                         | 200 OK          |

### 🎎 유저 CRUD
| API             | Method | URL                 | Request  | Request Sample                                                                        | Response  | Response Sample                                                                                                                                                                                                                                                                                                       | HttpStatus Code |
|-----------------|--------|---------------------|----------|---------------------------------------------------------------------------------------|-----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------|
| 유저 생성           | POST   | /api/users          | 요청 Body  | 1. 작성 유저명: String(필수) <br> 2. email: String(필수)                                       | 생성된 유저 정보 | { <br> "id": 1, <br> "username": "Syuare", <br> "email": "syuare@email.com", <br> "createAt": "2025-05-19", <br> "modifiedAt": "2025-05-19" <br> }                                                                                                                                                                    | 201: 정상 생성      |
| 선택 유저 조회(단건)    | GET    | /api/users/{userId} | 요청 param | - 일정 ID: Long(필수)                                                                     | 단건 응답 정보  | { <br> "id": 1, <br> "username": "Syuare", <br> "email": "syuare@email.com", <br> "createAt": "2025-05-19", <br> "modifiedAt": "2025-05-19" <br> }                                                                                                                                                                    | 200: 정상 조회      |
| 전체 유저 조회        | GET    | /api/users          | -        | -                                                                                     | 다건 응답 정보  | [ <br> { <br> "id": 1, <br> "username": "Syuare", <br> "email": "syuare@email.com", <br> "createAt": "2025-05-19", <br> "modifiedAt": "2025-05-19" <br> }, <br> { <br> "id": 2, <br> "username": "test", <br> "email": "test@email.com", <br> "createAt": "2025-05-19", <br> "modifiedAt": "2025-05-19" <br> } <br> ] | 200: 정상 조회      |
| 선택 유저 정보 수정(단건) | PATCH  | /api/users/{userId} | 요청 param | - 요청 ID: Long(필수) <br> 요청 Body: <br> 1. 작성 유저명: String(필수) <br> 2. 수정 이메일: String(필수) | 수정된 유저 정보 | { <br> "id": 1, <br> "username": "Syuare", <br> "email": "syuare2@email.net", <br> "createAt": "2025-05-19", <br> "modifiedAt": "2025-05-19" <br> }                                                                                                                                                                   | 200: 정상 수정      |
| 선택 유저 삭제(단건)    | DELETE | /api/users/{userId} | 요청 param | - 요청 ID: Long(필수)                                                                     | X         | X                                                                                                                                                                                                                                                                                                                     | 200 OK          |

## 📚 ERD (Entity Relationship Diagram)
### 1️⃣ schedules 테이블
<img src="src/main/resources/static/ScheduleApp_ERD1.png" alt="" />

### 2️⃣ schedules + users 테이블
<img src="src/main/resources/static/ScheduleApp_ERD2.png" alt="" />

### 3️⃣ (LV3 이후) users 테이블 내 password 컬럼 추가
<img src="src/main/resources/static/ScheduleApp_ERD3.png" alt="" />
