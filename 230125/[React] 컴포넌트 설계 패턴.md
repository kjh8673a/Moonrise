# React 컴포넌트 설계

## 원칙
    - 확장성있고 재사용성있는 코드를 만든다.
    - 관심사에 따라서 코드를 분리하고 단일 책임을 가지는 컴포넌트를 만들어야 한다.
    - 외부에 제어를 위임시키는 것을 고려해야한다.

## 프로젝트 구조 설계 패턴

<br/>

### 파일의 기능이나 라우트에 따라서 분류하기
    - 기능이나 경로로 폴더를 나누고 필요한 js, css, text코드를 모은다.
```
common /
    Avatar.js;
    Avatar.css;
    APIUtils.js;
    APIUtils.test.js;
feed /
    index.js;
    Feed.js;
    Feed.css;
    FeedStory.js;
    FeedStory.test.js;
    FeedAPI.js;
profile /
    index.js;
    Profile.js;
    ProfileHeader.js;
    ProfileHeader.css;
    ProfileAPI.js;
```

<br/>

### 파일 유형에 따라 분리
    - 아토믹 디자인: 페이지를 나눌 수 없을떄까지 쪼개서 재사용성을 극대화한다.
```
api /
    APIUtils.js;
    APIUtils.test.js;
    ProfileAPI.js;
    UserAPI.js;
components /
    Avatar.js;
    Avatar.css;
    Feed.js;
    Feed.css;
    FeedStory.js;
    FeedStory.test.js;
    Profile.js;
    ProfileHeader.js;
    ProfileHeader.css;
```

<br/>

### 도메인 의존성, 기능, 재사용성, 비지니스 로직 모두 고려하여 분리

```
-src /
  ---domain / // 특정 도메인에 의존적인 컴포넌트
  -----User /
  -------Profile /
  -------Avatar /
  -----Message /
  -------MessageItem /
  -------MessageList /
  -----Payment /
  -------PaymentForm /
  -------PaymentWizard /
  -------services /
  ---------Currency /
  -----------index.js;
-----------test.js;
-----Error /
  -------ErrorMessage /
  -------ErrorBoundary /
  -------utils / // Error에서만 쓰이는 유틸은 따로 둘 수도 있다
  ---------ErrorTracking /
  -----------index.js;
-----------test.js;
---components / // 재사용이 가능한 컴포넌트들
  -----App /
  -----List /
  -----Input /
  -----Button /
  -----Checkbox /
  ---hooks /
  ---context /
  ---utils /
  -----Format /
  -------Date /
  ---------index.js;
---------test.js;
```

<br/>

### 관심사에 따른 역할에 따라 분리

```
|── src
│   ├── application // 상태관리, 유틸, 상수
│   │   ├── common
│   │   ├── filters
│   │   ├── logger
│   │   ├── models
│   │   ├── persist
│   │   ├── plugins
│   │   ├── store
│   ├── infrastructure // axios, api handeler, 공통 컴포넌트
│   │   ├── api(services)
│   │   ├── components (common components)
│   ├── presentation // 비즈니스 로직 컴포넌트
│   │   ├── container
│   │   ├── component
├── index.js
```

<br/>

#### 참고
https://www.stevy.dev/react-design-guide/



