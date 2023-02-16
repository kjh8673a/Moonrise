![타이틀이미지](./docs/img/moonrise.png)

<br>
달뜨다[달:뜨다] <br>
1.가라앉지 않고 들썽거리다. <br>
2.열기가 올라서 진정하지 못하다.

---
## 🎵 프로젝트 진행 기간
---
2023.01.3(화) ~ 2023.02.17(금)  
SSAFY 8기 2학기 공통프로젝트 - 달뜸
</br>

---
## 🎵 달뜸 - 배경
---
안녕하시지~~ 인생영화를 보고 달뜨신 경험 있으시죠? 그때 같은 영화를 본 친구가 없다거나, 감정을 공유할 마땅한 곳이 없지 않나요? 영화를 보고 타인과 공감할 서비스 "달뜸"을 만들게 되었습니다.
달뜸과 함께 달뜬 감정을 공유해보세요.

</br>

---
## 💜 달뜸 - 개요
---
*- 영화를 보고 달뜬 당신, 
그 마음 그대로 달뜸으로 가져오세요
 -*  

영화를 보고 달뜬 감정을 공유할 수 있는 웹 서비스입니다. 간단한 생각을 주고 받을 수 있는 채팅 기능, 복잡한 생각을 정리할 수 있는 게시글 기능, 영화와 관련된 활동을 함께할 수 있는 소모임 기능으로 이루어진 영화 커뮤니티 서비스입니다.

</br>

---
## ✔ 주요 기능
---

영화 리뷰 게시글

- 영화를 보고, 달 뜬 나의 생각을 게시글로 작성해요.
- 영화에 대한 다른 사람들의 생각들을 공유해요.
- 사용자들은 게시글에 댓글로 자신의 의견을 낼 수 있어요.

소모임 개설 및 참여

- 영화 주제에 맞는 소모임을 개설하고 참여해요.
- 마음이 맞는 사람들끼리 온/오프라인 소모임을 생성해요.
- 

토론방 소통

- 같은 생각을 가진 사람들끼리 실시간으로 채팅을 통해 토론을 해요
- 토론에 늦게 참여해도 이전 채팅방 내역을 확인할 수 있어요

영화 평점

- 달뜸만의 영화 평점 기능으로 평점을 제공해요.
- 영화를 보고 온 어느 사용자든 영화 평점을 남길 수 있습니다.


---
## ✔ 주요 기술
---
**FrontEnd**
- React

**Backend**
- Springboot 2.7.8
- Spring Data JPA
- WebSocket
- MySQL 8.0
- Redis
- Redis Sentinel

**CI/CD**
- AWS EC2
- Jenkins
- NGINX
- SSL
- Docker
- Docker Compose

**협업툴**
- Git
- Jira
- Notion

---
## ✔ 프로젝트 파일 구조
---

```
BackEnd
 ┣ pjt1 (Bussiness)
 ┃  ┣ commons
 ┃  ┣ configuration
 ┃  ┣ util
 ┃  ┣ redis
 ┃  ┣ image
 ┃  ┣ board
 ┃  ┣ debate
 ┃  ┣ movie
 ┃  ┣ party
 ┃  ┗ rating
 ┣ pjt2 (Auth)
 ┃  ┣ configuration
 ┃  ┣ util
 ┃  ┗ member
 ┗ pjt3 (Chatting)
    ┣ commons
    ┣ configuration
    ┣ util
    ┣ redis
    ┣ member
    ┣ movie
    ┗ debate

FrontEnd
 ┣ api
 ┣ assets
 ┃ ┗ img
 ┣ components
 ┃ ┣ common
 ┃ ┣ community
 ┃ ┃ ┣ board
 ┃ ┃ ┣ movie
 ┃ ┃ ┣ party
 ┃ ┃ ┣ talk
 ┃ ┣ main
 ┃ ┣ profile
 ┃ ┃ ┣ board
 ┃ ┃ ┣ log
 ┃ ┃ ┣ party
 ┃ ┃ ┣ profile
 ┃ ┣ search
 ┃ ┗ user
 ┣ feature
 ┃ ┣ reducer
 ┃ ┗ UI
 ┣ pages
 ┣ App.css
 ┣ App.js
 ┣ App.test.js
 ┣ index.css
 ┣ index.js
 ┣ reportWebVitals.js
 ┣ setupTests.js
 ┗ store.js
 ```

---
## ✔ 팀원 역할 분배
---


---
## ✔ 프로젝트 산출물
---
### 결과물

-  [달뜸](http://3.35.149.202/)

### Docs

-  [API 명세서 - Notion](https://www.notion.so/API-68b0b8dfecb34b4aaafe785eada86e2f)

<br>

---
## 🎵 달뜸 서비스 화면
---

### 회원가입
- 첫 로그인 시 회원정보(닉네임, 선호 장르)을 선택

### 로그인
- 카카오 소셜 로그인
- 회원정보 수정
<img src="./docs/gif/Login.gif">

### 영화 검색
- 달뜸의 시작페이지
- 보고 온 영화를 검색할 수 있다.
<img src="./docs/gif/MovieSearch.gif">

### 게시글
- 생성
- 수정
- 상세보기
- 댓글
    - 댓글 및 대댓글 작성, 수정, 삭제 기능
- 게시글 좋아요 및 북마크
<img src="./docs/gif/BoardComment.gif">

### 토론방
- 생성
- 상세보기
- 채팅방
    - 영화에 대해 다른 사용자들과 채팅을 주고 받을 수 있다.
<img src="./docs/gif/Chat.gif">

### 소모임
- 생성
    - 이미지, 제목, 주의사항, 모임날짜, 모임장소, 인원 수  값을 지정하여 소모임 모집글을 생성할 수 있다.
- 상세보기
    - 문의, 참가신청, 신청관리를 할 수 있는 상세보기 페이지
<img src="./docs/gif/PartyCreate.gif">

- 문의
    - 호스트에게 문의를 남길 수 있다. 비공개 문의 가능
- 참가신청
    - 호스트에게 간단한 메시지와 함께 소모임 신청을 할 수 있다.
<img src="./docs/gif/PartyJoinComment.gif">

- 참가관리
    - 호스트는 참가신청을 승인, 거절할 수 있다.
    - 승인, 승인대기, 거절 3가지 상태에 따라 구분된다.
<img src="./docs/gif/PartyJoinAccept.gif">


### 평점
- 영화에 5가지 분야로 평점을 매길 수 있다.
- 모든 유저들의 평점을 종합하여 보름달 지수로 활용한다.
- 평점 수정 또한 가능하다.
<img src="./docs/gif/Rating.gif">