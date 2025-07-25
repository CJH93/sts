package com.fullstack.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Embeddable : 특정 객체가 특정 객체의 값으로 사용될 때 사용하는 어노테이션
// 특정 상품에는 이미지가 존재, 이 값으로 인스턴스가 사용된다고 선언할 때 사용됨
@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

	private String fileName;
	private int ord; // 순서 관련 필드. 각 이미지마다 이 속성에 값을 부여해서 이미지 순번을 생성할 목적으로 선언
	// 또한, 대표 이미지를 설정해서 해당 이미지가 Front 에서 대표 이미지로 사용되도록 함
	public void setOrd(int ord)
	{
		this.ord = ord;
	}

	
	
	
}






