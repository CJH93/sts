package com.fullstack.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

// MappedSuperClass 는 해당 엔티티에 대한 테이블을 생성하지 말라는 의미
// 이것을 상속 받으면 테이블이 생성 되어짐
// 방명록에서 작성일과 수정일을 자동으로 엔티티에 밀어 넣도록 하는 기능을 함
// 하위 엔티티는 테이블화 되어짐
// @EntityListeners 에 등록 후, 메인 클래스에도 알려줘야함
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
public abstract class BaseEntity {
	
	@CreatedDate
	@Column(name = "regdate", updatable = false)
	private LocalDateTime regDate; // 작성일
	
	@LastModifiedDate
	@Column(name = "moddate")
	private LocalDateTime modDate; // 수정일
	

}








