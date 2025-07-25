package com.fullstack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Board extends BaseEntity{

	// 글 번호, 제목, 내용, 작성자를 필드로 선언
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	private String title;
	private String content;
	
	public void changeTitle(String title)
	{
		this.title = title;
	}
	
	public void changeContent(String content)
	{
		this.content = content;
	}
	
	// 의존 관계인 Member 는 나중에 추가 예정
	// 아래 연관관계 선언이 fetch 속성을 지정할 수 있음
	// 값에 따라서 조회시 다른 형태를 취함
	// eager : 연관 테이블을 동시 조회
	// lazy : 연관 테이블을 조회 대상에서 제외
	@ManyToOne(fetch = FetchType.LAZY)
	private Member writer;

}




