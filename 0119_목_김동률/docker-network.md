# docker network


EC2에 올려놓은 MySQL 컨테이너와 SpringBoot 컨테이너를 연결하기 위해 아래와 같이 설정을 하였다.
<img src="./Untitled (6).png">


찾아보니 docker container간 통신을 하려면 같은 network로 묶어줘야 한다는 것이다.

## docker network

Docker 컨테이너는 격리된 환경에서 돌아가기 때문에 기본적으로 다른 컨테이너와의 통신이 불가능하다. 하지만 여러 개의 컨테이너를 하나의 Docker 네트워크에 연결시키면 서로 통신이 가능해진다.

### 네트워크 조회

```bash
ubuntu@ip-172-31-46-131:~$ docker network ls

NETWORK ID     NAME           DRIVER    SCOPE
1d0b2b108e88   bridge         bridge    local
ad7a2408912a   host           host      local
7d4c00834e1a   none           null      local
c8bfa08d590b   redis-net      bridge    local
b706212365c1   test-network   bridge    local
```

### 네트워크 생성

```jsx
docker network create {네트워크명}
```

이미 동작중인 컨테이너에 network 연결

- mysql-container에 연결해준다.

```jsx
docker network connect test-network mysql-container
```

Jenkins 배포 스크립트를 수정한다. 

```bash
docker run -p 9000:9000 --name spring --network test-network -d test:1.0
```

Jenkins 빌드 실행

<img src="./Untitled (7).png">

### 도커 컨테이너 네트워크 목록 확인

```bash
docker container inspect {컨테이너명}
```

**mysql-container 의 네트워크 목록**

<img src="./Untitled (8).png">

**spring 컨테이너의 네트워크 목록**

<img src="./Untitled (9).png">

### 컨테이너 로그 확인

잘 실행됬는지 docker logs 명령어를 실행해 내부 로그를 확인한다.

<img src="./Untitled (10).png">