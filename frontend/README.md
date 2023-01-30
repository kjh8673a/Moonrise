# FrontEnd 구현

## 컴포넌트 설계

```
src
├── pages // 페이지..
│	  ├── Main.js
│	  ├── User.js
│	  ├── Search.js
│	  ├── Community.js
│	  └── Profile.js
├── components // 페이지 안 컴포넌트
│	  ├── common
│       ├── ProfileIcon.js
│		├── MainNav.js
│		├── LogCard.js
│		│	├── PartyCard.js
│		│	└── ProfileEdit.js
│	  ├── main
│		│	  ├── MainSearch.js
│		│	  └── MainLog.js
│	  ├── user
│   │	  └── Login.js
│	  ├── search
│		│	  └── MovieCard.js
│	  ├── community
│		│	  ├── movie
│		│		│	  ├── MovieDetail.js
│		│		│	  ├── MovieDetailEval.js
│		│		│	  └── MovieDetailEvalResult.js
│		│	  ├── board
│		│		│	  ├── BoardList.js
│		│		│	  ├── BoardCard.js
│		│		│	  ├── BoardView.js
│		│		│	  └── BoardWrite.js
│		│	  ├── talk
│		│		│	  ├── TalkList.js
│		│		│	  ├── TalkCard.js
│		│		│	  ├── TalkView.js
│		│		│	  ├── TalkRoom.js 
│		│		│	  └── TalkWrite.js
│		│	  ├── party
│		│		│	  ├── PartyList.js
│		│		│	  ├── PartyView.js
│		│		│	  ├── PartyDetailCard.js
│		│		│	  ├── PartyCommentGuest.js
│		│		│	  ├── PartyCommentHost.js
│		│		│	  ├── PartyEnroll.js
│		│		│	  ├── PartyCandidate.js
│		│		│	  └── PartyWrite.js
│		│	  ├── CommunityNav.js
│		│	  └── Pagination.js
│	  ├── profile
│		│	  ├── profile
│		│	  │	  └── ProfileCard.js
│		│	  ├── log
│		│	  │	  └── LogList.js
│		│	  ├── board
│		│	  │	  ├── ProfileBoardList.js
│		│	  │	  ├── ProfileBoardCard.js
│		│	  │	  └── ProfileBoardNav.js
│		│	  ├── party
│		│	  │	  ├── ProfilePartyList.js
│		│	  │	  └── ProfilePartyNav.js
│		│	  └── ProfileNav.js
├── api // axios관련..
├── assets // 이미지 등 자료..
├── feature // 필요 기능들(함수 등)..
├── App.js
└── index.js
```