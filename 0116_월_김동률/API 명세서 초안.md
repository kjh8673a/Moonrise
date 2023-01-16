# API 명세 초안

## 게시판

- 영화 ID에 따른 게시글 목록
- 회원 ID에 따른 게시글 목록 (token 이용)
- 게시판 ID에 따른 (댓글 목록, 게시글 정보)
- 게시판 내용 create, update, ~~delete~~

## 댓글

- 댓글 create, update
- 게시판 ID에 따른 댓글 목록

## 소모임

- 영화 ID에 따른 소모임 목록
- 소모임 ID에 따른 (회원 목록, 소모임 정보)
- 소모임 정보 create, update, ~~delete~~
- 소모임 신청하기( 회원 추가 ) - 신청 대기 테이블 create
- 소모임 신청완료(confirm) - 신청 대기 테이블 update

## 영화

- 영화 정보 create
- 영화 ID에 따른 영화 세부 정보