# :moon: 달뜸

## 서비스 개발 기록

### :: Docs

#### API 명세서 - Notion

#### API 명세서 - Swagger

#### ERD

#### React 컴포넌트 설계

``` bash
src
├── pages // 페이지..
│   ├── Main.js
│   ├── User.js
│   ├── Search.js
│   ├── Community.js
│   └── Profile.js
├── components // 페이지 안 컴포넌트
│   ├── common
│   |   ├── ProfileIcon.js
│   |   ├── MainNav.js
│   |   ├── LogCard.js
│   │   ├── PartyCard.js
│   │   └── ProfileEdit.js
│   ├── main
│   │   ├── MainSearch.js
│   │   └── MainLog.js
│   ├── user
│   │   └── Login.js
│   ├── search
│   │   └── MovieCard.js
│   ├── community
│   │    ├── movie
│   │    │   ├── MovieDetail.js
│   │    │   ├── MovieDetailEval.js
│   │    │   └── MovieDetailEvalResult.js
│   │    ├── board
│   │    │   ├── BoardList.js
│   │    │   ├── BoardCard.js
│   │    │   ├── BoardDetail.js
│   │    │   ├── BoardComment.js
│   │    │   ├── BoardCommentCard.js
│   │    │   ├── BoardSubCommentCard.js
│   │    │   ├── BoardSubCommentInput.js
│   │    │   ├── BoardDetail.js
│   │    │   └── BoardWrite.js
│   │    ├── talk
│   │    │   ├── TalkList.js
│   │    │   ├── TalkCard.js
│   │    │   ├── TalkDetail.js
│   │    │   ├── TalkRoom.js 
│   │    │   └── TalkWrite.js
│   │    ├── party
│   │    │   ├── PartyCandidate.js v
│   │    │   ├── PartyCandidateCard.js
│   │    │   ├── PartyCard.js v
│   │    │   ├── PartyComment.js
│   │    │   ├── PartyDetail.js
│   │    │   ├── PartyDetailCard.js
│   │    │   ├── PartyEnroll.js
│   │    │   ├── PartyWrite.js
│   │    │   └── PartyList.js
│   │    ├── CommunityDetail.js v
│   │    ├── CommunityList.js v
│   │    ├── CommunityNav.js v
│   │    ├── CommunityHeader.js v
│   │    ├── CommunityWrite.js v
│   │    └── CommunityPagination.js v
│   └── profile
│        ├── profile
│        │    └── ProfileCard.js
│        ├── log
│        │    └── LogList.js
│        ├── board
│        │    ├── ProfileBoardList.js
│        │    ├── ProfileBoardCard.js
│        │    └── ProfileBoardNav.js
│        ├── party
│        │    ├── ProfilePartyList.js
│        │    └── ProfilePartyNav.js
│        └── ProfileNav.js
├── api // axios관련..
├── assets // 이미지 등 자료..
├── feature
|    └── reducer
|        ├── MovieReducer.js
|        └── PartyReducer.js
├── App.js
├── index.js
└── store.js
```

### 개인별 수행 내용

#### [BackEnd]

##### 김동률

##### 박윤지

##### 정상민

##### 조원희

#### [FrontEnd]

##### 권지훈

##### 최현인

- 뒷풀이(목록, 생성, 상세) 페이지 제작
  - rest api 연동 완료
  - Redux 활용하여 뒷풀이 state 관리
- 메인 페이지 제작
  - 영화 검색 기능 구현
    - tmdb rest api 사용
- 검색 목록 페이지 제작
  - Redux 활용하여 영화 관련 state 관리
- Redux 및 axios 환경 세팅
- 프론트 1차 배포 빌드

### 결과물

#### ver 1.0 배포 완료

##### [달뜸 ver 1.0](http://3.35.149.202/)

### 향후 계획

#### BackEnd

#### FrontEnd

- 유저 관련 페이지 제작
  - 로그인/회원가입
  - 마이 페이지
- 생성 페이지 에디터 적용
  - 날짜 입력 라이브러리 서칭
- 디자인 퀄업 및 다이나믹 디자인 적용
- 코드 리팩토링
  - 컴포넌트 최적화
  - Redux 최적화
