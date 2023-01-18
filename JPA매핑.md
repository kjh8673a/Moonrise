## 객체와 테이블 매핑

- 객체와 테이블 매핑
    - `@Entitiy` : JPA가 관리하는 클래스이다. 기본생성자 필수. final 클래스, enum, interface, inner클래스 사용 X
    - `@Table` : 엔티티와 매핑할 테이블 지정. name에 매핑할 테이블 이름
    - `@Column` : 필드와 컬럼 매핑
    - `@ID` 기본 키 매핑
    - `@ManyToOne`, `JoinColumn`
- 데이터베이스 스키마 자동 생성
    - DDL을 애플리케이션 실행 시점에 자동 생성.
    - 테이블중심에서 객체중심으로 변화.
    - 데이터베이스 방언을 활용해서 DB에 맞는 적절한 DDL생성
    - 이렇게 생성된 DDL은 개발 장비에서만 사용한다.
    - 생성된 DDL은 운영서버에서는 사용하지 않거나, 적절히 다듬은 후 사용한다.
- DDL생성기능
    - 제약조건 추가, 유니크 제약조건 추가
    - DDL생성 기능은 DDL을 자동생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다.
- 매핑 어노테이션
    - `@Column` : 컬럼 매핑.
        - name: 필드와 매핑할 테이블의 컬럼 이름
        - insertable, updatable : 등록, 변경 가능 여부
        - nullable : null 값의 허용 여부를 설정한다. false로 설정하면 DDL 생성 시 not null제약조건이 붙는다.
        - unique :  `@Table` 의 uniqueConstarints와 같지만 한 컬럼에 간단히 유니크 제약조건을 걸 때 사용한다.
        - columnDefinition : 데이터베이스 컬럼 정보를 직접 줄 수 있다.
        - length : 문자 길이 제약조건. String 타입에만 사용한다.
        - precision sclae : BigDecimal타입에서 사용한다.(+Biginteger)
    - `@Temporal` : 날짜 타입 매핑
        - TemporalType.DATE: 날짜, 데이터베이스 date 타입과 매핑
        (예: 2013–10–11)
        - TemporalType.TIME: 시간, 데이터베이스 time 타입과 매핑
        (예: 11:11:11)
        - TemporalType.TIMESTAMP: 날짜와 시간, 데이터베이 스
        timestamp 타입과 매핑(예: 2013–10–11 11:11:11)
    - `@Enumerated` : enum 타입 매핑
        - EnumType.ORDINAL: enum 순서를 데이터베이스에 저장
        - EnumType.STRING: enum 이름을 데이터베이스에 저장.
        - 주의: ORDINAL 사용 금지
    - `@Lob` : BLOB, CLOB 매핑
        - @Lob에는 지정할 수 있는 속성이 없다.
        - 매핑하는 필드 타입이 문자면 CLOB 매핑, 나머지는 BLOB 매핑
        • CLOB: String, char[], java.sql.CLOB
        • BLOB: byte[], java.sql. BLOB
    - `@Transient` : 특정 필드를 컬럼에 매핑하지 않음(매핑무시). 주로 메모리상에서만 임시로 어떤 값을 보관하고 싶을 때 사용
- 기본 키 매핑 어노테이션
    - 직접 할당: `@Id` 만 사용
    - 자동 생성(`@GeneratedValue`)
        - IDENTITY : MYSQL. 기본 키 생성을 데이터베이스에 위힘. JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행. AUTO_INCREMENT는 DB에 INSERT를 실행한 이후에 ID값을 알 수 있다. em.persist() 시점에 즉시 INSERT 실행
        하고 DB에서 식별자를 조회
        - SEQUENCE :  데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트(예: 오라클 시퀀스). `@SequenceGenerator` 필요.
            - initialValue : DDL 생성 시에만 사용됨, 시퀀스 DDL을 생성할 때 처음 1 시작하는 수를 지정한다.
            - allocatioinSize : 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨. 데이터베이스 시퀀스 값이 하나씩 증가하도록 설정되어 있으면 이 값을 반드시 1로 설정해야 한다
        - TABLE : 키 생성용 테이블 사용, 모든 DB에서 사용. `@TableGenerator` 필요.
        - AUTO : 방언에 따라 자동 지정, 기본값
- 권장하는 식별자 전략
    - 기본 키 제약 조건: null 아님, 유일, 변하면 안된다.
    - 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대리키(대
    체키)를 사용하자.
    - 권장: Long형 + 대체키 + 키 생성전략 사용