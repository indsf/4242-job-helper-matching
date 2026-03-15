# 4242 도움받는 사이 도움주는 사이 | 장애인 일자리 추천 및 도우미 매칭 시스템

> 장애인의 일상 지원과 취업 지원을 하나의 흐름으로 연결한 도우미 매칭 및 맞춤형 일자리 추천 웹 서비스

## 프로젝트 소개

장애인은 일상 속 이동과 생활 지원, 그리고 취업 과정에서 다양한 불편을 겪습니다.  
도움이 필요한 상황에서도 실시간으로 지원자를 찾기 어렵고, 구직 과정에서도 접근성이나 근무 환경 같은 실질적인 정보를 반영한 맞춤형 채용공고를 찾기 쉽지 않습니다.

**4242 도움받는 사이 도움주는 사이**는 이러한 문제를 해결하기 위해  
**도움 요청/제공 게시판**, **댓글 기반 매칭**, **실시간 채팅**, **마이페이지**, **장애인 맞춤 일자리 추천** 기능을 하나의 서비스로 통합한 프로젝트입니다.

- **개발 기간**: 1개월
- **팀원**: 이인호, 김두한

---
## 발표 및 시연
🎥 [발표 시연 영상 보기](https://www.youtube.com/watch?v=rORTRVqNBpA)

---
## 기술 스택

| 구분 | 기술 |
|------|------|
| Frontend | React |
| Backend | Spring Boot, Spring Security, Spring Data JPA |
| Database | MySQL |
| Realtime | WebSocket |
| API Documentation | Swagger / OpenAPI |
| Authentication | Session, Cookie, HTTPS |
| AI / Recommendation | FastAPI 연동 |
| External API | 공공데이터 OpenAPI, Naver OAuth |
| Collaboration | GitHub |

---

## 담당 역할

- 프로젝트 전체 구조와 데이터베이스를 설계하고, 주요 백엔드 기능을 직접 구현했습니다.
- Spring Boot 기반으로 회원, 게시글, 마이페이지 등 핵심 기능의 CRUD를 구현했습니다.
- Controller-Service-Repository 계층 구조를 설계하고, DTO / Entity 분리를 적용해 API 흐름을 구성했습니다.
- Spring Data JPA를 활용해 게시글 CRUD, 조건별 조회, 작성자 기준 조회, 페이징 기능을 구현했습니다.
- Spring Security와 세션/쿠키 기반 인증을 적용해 로그인 상태 유지 기능을 구현했습니다.
- Swagger를 활용해 API 명세를 문서화하고, 공통 응답 포맷을 적용해 응답 구조를 일관되게 구성했습니다.

> 실시간 1:1 채팅 기능은 프로젝트 기능에 포함되지만 해당 기능 구현은 담당하지 않았습니다.

---

## 시스템 구조
<img width="612" height="245" alt="Image" src="https://github.com/user-attachments/assets/15e01e3e-b520-4248-8e6a-dc1e0a1d9897" />

---
## 구현 포인트

- Controller-Service-Repository 구조를 적용해 요청 처리, 비즈니스 로직, 데이터 접근 책임을 분리했습니다.
- DTO / Entity 분리를 통해 화면에 필요한 데이터만 응답하도록 API 구조를 설계했습니다.
- Spring Data JPA 기반으로 CRUD, 조건별 조회, 페이징 기능을 구현했습니다.
- ApiResponse, ApiResponseGenerator를 활용해 공통 응답 포맷을 구성했습니다.
- Spring Security와 세션/쿠키 기반 인증으로 로그인 상태를 안정적으로 유지하도록 구현했습니다.
- Swagger 기반 문서화를 통해 API 테스트와 협업 효율을 높였습니다.
  
## 트러블슈팅
- React와 Spring 분리 환경에서 세션 유지 문제가 있어 HTTPS와 쿠키 설정을 적용해 해결했습니다.
- 외부 API 및 FastAPI 연동 과정에서 응답 지연과 실패 가능성이 있어 재시도와 타임아웃 처리를 적용했습니다.
- API 수가 많아 테스트와 협업이 비효율적이어서 Swagger 기반 문서화를 적용해 명세를 통일했습니다.
---

## 데이터베이스 ERD
<img width="1312" height="698" alt="ERD" src="https://github.com/user-attachments/assets/0b6a3749-cd9c-4dd6-9875-b39a4466a00d" />

---
## Swagger를 활용한 API 명세서 작성
<img width="2048" height="1776" alt="Swagger" src="https://github.com/user-attachments/assets/ee5d64ec-ce06-453c-9c68-c8d2bd5ae7bc" />

---

## 주요 기능

| 기능 | 설명 |
|------|------|
| 회원 인증 | 일반 로그인, 세션 기반 로그인 유지, 네이버 OAuth 로그인 연동 |
| 게시글 작성 | 도움 요청자와 지원자가 각각 게시글을 작성하고 일정, 장소, 상세 내용을 등록 |
| 매칭 기능 | 댓글을 통해 요청자와 지원자를 연결하고 수락 시 매칭 성립 |
| 실시간 채팅 | 매칭된 사용자 간 일정 및 지원 내용 조율 |
| 마이페이지 | 사용자 정보, 작성 게시글, 매칭 진행 현황 및 완료 내역 관리 |
| 일자리 추천 | 사용자 조건을 반영한 장애인 맞춤형 채용공고 추천 |

---

## Git Flow
<img width="3250" height="897" alt="Git Flow" src="https://github.com/user-attachments/assets/a52c79d0-26c0-4501-b14e-073e1a36cc18" />
- GitHub를 활용해 브랜치를 분리하고 기능 단위로 작업을 관리했습니다.
- Git Flow 기반으로 개발 및 병합 전략을 운영해 협업 충돌을 줄이고 작업 이력을 명확하게 관리했습니다.
