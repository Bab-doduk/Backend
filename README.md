# 밥도둑🍚: AI 검증 비즈니스 프로젝트
- 음식점들의 배달 및 포장 주문 관리, 결제, 그리고 주문 내역 관리 기능을 제공하는 **음식 주문 관리 플랫폼**


## 목차
1. [프로젝트 소개](#프로젝트-소개)
2. [기술 스택](#기술-스택)
3. [구현 기능](#구현-기능)
4. [프로젝트 실행 방법](#프로젝트-실행-방법)
5. [아키텍쳐 구조](#아키텍쳐-구조)
6. [API 명세서](#api-명세서)
7. [테이블 명세서 및 ERD](#테이블-명세서-및-erd)
8. [프로젝트 기록](#프로젝트-기록)


## 프로젝트 소개
- **프로젝트 기간** 
  - 2024.08.22 ~ 2024.09.02 (12일) / `4인` 팀 프로젝트
    
- **개발 환경** 
  - <img src="https://img.shields.io/badge/Framework-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/3.3.3-515151?style=for-the-badge">
  - <img src="https://img.shields.io/badge/Build-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white">
  - <img src="https://img.shields.io/badge/Language-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/java-%23ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"><img src="https://img.shields.io/badge/17-515151?style=for-the-badge">


- **팀원 역할 분배**

| **Backend** | [이경진](https://github.com/kyungjinleelee)                         | [이성원](https://github.com/lsw71311)                       | [김수봉](https://github.com/bongbongbon)               | [송기찬](https://github.com/gichan-song)                        |
|-------------|------------------------------------|----------------------------------|--------------------------|-----------------------------------|
| **도메인**  | 가게 도메인, 결제 도메인, 리뷰 및 평점, 신고 도메인           | 주문 도메인, 공지사항 및 고객센터 도메인 | 상품 도메인, AI API, 배포          | 인증/인가 도메인, 유저 도메인      |

<br>

## 기술 스택
<div align=center> 
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">
  <br>
  <img src="https://img.shields.io/badge/PostgreSQL-4479A1?style=for-the-badge&logo=PostgreSQL&logoColor=white">
  <img src="https://img.shields.io/badge/springDataJPA-90E59A?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/Amazon%20S3-F36D00?style=for-the-badge&logo=Amazon%20S3&logoColor=white">
  <br>
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
  <img src="https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">
  
  <br>
</div>

## 구현 기능
- **유저 관리**
   - [x] 유저 중복검사를 통한 회원가입
   - [x] 고객/가게주인/매니저/관리자로 구분
   - [x] 회원 전체 조회는 MANAGER, MASTER 만 가능
   - [x] 회원 정보 조회, 수정, 삭제는 로그인한 유저만 가능
   - [x] 회원 정보 삭제는 deleted_at 필드를 두어 soft delete로 구현


- **가게 및 주문, 결제 기능**
   - [x] 인증/인가가 필요한 로직에서는 접근 권한 및 로그인 체크 진행
   - [x] 가게 주인의 경우 본인의 가게만 수정/삭제할 수 있고, 관리자의 경우 모두 가능
   - [x] 가게, 결제 검색의 경우 여러 개의 조건을 동적으로 검색하기 위해 ***QueryDSL***을 사용해 구현
   - [x] 주문 취소는 생성 후 5분 이내에만 가능하도록 제한하여 구현


- **상품(메뉴) 및 옵션, AI 기능**
   - [x] 모든 기능은 인증/인가된 사용자만 접근 가능(OWNER/MASTER)
   - [x] 상품 등록 시 멀티미디어 파일은 ***AWS S3***에 저장
   - [x] 상품 검색의 경우 검색 조건이 많지 않아 JPQL을 사용해 빠르게 구현
   - [x] `AI API` 연동으로 사장님이 상품 설명을 쉽게 작성할 수 있도록 상품 설명을 자동 생성
   - [x] 실제 요청 텍스트 마지막에 “답변을 최대한 간결하게 50자 이하로” 라는 텍스트를 요청시 삽입하여 사용량 줄임
 
- **상품 리뷰 및 평점, 신고 기능**
   - [x] 주문을 완료한 고객만이 가게 상품 리뷰 및 평점 등록 가능
   - [x] 고객의 경우, 검색 시 음식점 고유값을 통해 호출하면 리뷰 리스트 조회 가능
   - [x] 관리자 권한의 사용자는 모든 검색 조건을 사용하여 검색 및 조회 가능
   - [x] 신고된 리뷰는 목록에 노출되지 않도록 구현
   - [x] ***N+1*** 문제를 피하기 위해, 가게 목록 조회 시 상품 평점을 계산하지 않고, 가게 리뷰가 추가되거나 수정될 때 마다 평균 평점을 사전에 계산하여 가게 엔티티에 저장되도록 구현

 - **공지사항 및 고객센터 기능**
   - [x] 공지사항 생성, 수정, 삭제는 관리자 권한으로만, 조회는 모든 사용자가 접근 가능
   - [x] 문의/답변 기능은 인증/인가된 사용자만 접근 가능
   - [x] 각 사용자는 자신의 신고 내역만 확인할 수 있고 관리자는 모든 신고 내역에 접근 가능

<br>

## 프로젝트 실행 방법
- 프로젝트 클론 및 `docker` 설치 후 로컬 환경에서 순차적으로 실행
1. 프로젝트 클론

   ```
    git clone https://github.com/neul-poom/backend.git
    ```


2. 도커 컴포즈 명령어 실행

   ```
    docker-compose up -d
   ```

3. application.yml 파일 작성
   <details>
   <summary>(작성 예시)</summary>

   ```yaml
   spring:
      application:
        name: Bob-doduk

    datasource:
    url: 
    username: 
    password: 
    driver-class-name: 

    jpa:
      hibernate:
        ddl-auto:                                   
        dialect: 
      show-sql:                                    

    sql:
      init:
        mode:                                       
    jwt:
      secret:
        key: 
  
   
4. 애플리케이션 실행
<br>

## 아키텍쳐 구조
![image](https://github.com/user-attachments/assets/7b7bfb81-769c-4fc7-9a05-fc98affff6de)


## API 명세서 
- [[삽입예정...]()
<br>


## 테이블 명세서 및 ERD 
- [[테이블 명세서 보러가기]](https://www.notion.so/teamsparta/9800a5471fda430184fc312cfb223518)
- **ERD**
![image](https://github.com/user-attachments/assets/f7665dbe-5fab-4191-b8d0-61784279aadb)



## 프로젝트 기록 
- 프로젝트 설계 SA [[블로그 이동]](https://developer-jinnie.tistory.com/87)

