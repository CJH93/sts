package com.fullstack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Member extends BaseEntity {
	
	// 사용자 정보는 이메일(key), 암호, 이름 정도만 설정
	@Id
	private String email;
	private String password;
	private String name;
}
