# Tailwind + React 세팅

## Tailwind

- Html을 떠나지 않고도 신속하게 웹 스타일링을 구축할 수 있도록 도와주는 유틸리티 지향 Css Framework

- 장점
    - 간결하고 Html을 떠나지 않기 때문에 개발속도가 빨라진다.
    - 반응형 스타일링에 강력하다.
    - 클래스 이름을 고민할 필요가 없다.

- 단점
    - Html 부분이 지저분해 보일 수 있다.
    - 단순한 편이지만 초기 적응기간이 필요하다.


## 세팅하기

- React 프로젝트 생성
```
npm create-react-app [프로젝트 이름]
```

- Tailwind 설치

    - tailwindcss: Tailwind CSS 프레임워크
    - postcss: CSS 빌드 프레임워크
    - autoprefixer: 브라우저 호환 설정
```
npm install -D tailwindcss@latest postcss@latest autopreficer@latest
```
    
- 설정 파일 생성

```
npx tailwindcss init
```

- CSS 설정 파일 생성

    원하는 경로에 css파일을 하나 생성한다.<br/>
    css파일에 아래 코드를 추가한다.
```
@tailwind base;
@tailwind components;
@tailwind utilities;
```

- 빌드

    css파일을 빌드해서 실제 프로젝트에 쓰일 css파일 생성한다.<br/>
    -i 뒤에 자신이 생성한 css파일의 경로를, <br/>
    -o 뒤에는 결과물을 저장할 경로를 지정한다.
```
npx tailwindcss -i ./[css경로]/[css파일명].css -o ./[저장할경로]/[출력파일명].css
```

- 프로젝트에 적용
    public/index.html을 수정한다.

```
<html>
	<head>
    	...
    	<link rel="stylesheet" href="%PUBLIC_URL%/output.css" />
    </head>
    <body>
    	...
    </body>
</html>
```
