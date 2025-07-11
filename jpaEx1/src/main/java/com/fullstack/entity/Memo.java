package com.fullstack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// @Entity : JPA 를 통해서 관리하게 되는 객체(하나의 테이블 개념)
// @Repository : Entity 를 처리하는 기능을 가진 Interface.

@Entity // Entity 클래스로 선언. JPA 의 Repository 에 의해서 관리됨
// Entity 로 선언되면, 프로그램 시작시 테이블이 자동 생성됨
@Table(name = "mymemo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Memo {

	// Entity 로 생성되는 테이블에는 반드시 key 가 존재해야함.
	@Id // 해당 컬럼이 PK 임을 선언함
	// JPA 에서 사용되는 숫자는 객체형이어야 함.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // key 의 자동 증가값 선언
	// 속성으로 사용되는 ENUM 에서 identity 는 DB 가 생성하는 key 값을 뜻함
	// auto : jpa 구현체가 생성해줌 (여기선 하이버네이트)
	// sequence : 오라클처럼 sequence 객체를 사용할때 사용됨
	private Long mno;
	
	@Column(length = 200, nullable = false) // 일반 컬럼을 정의할 때 사용.
	// 속성을 이용해서 컬럼 속성을 정의할 수 있음
	// 기본값(default)을 적용할 경우 columnDefinition 속성 이용
	// ex> varchar(300) default 'hello'
	private String memoText;
	
	
}













