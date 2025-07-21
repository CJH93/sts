package com.fullstack.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Entity
// 내부에는 ROLE 이 가진 값을 참조하도록 설정. 값을 List 로 갖도록 하는
// @ElementCollection 으로 필드 처리
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "memberRoleList")
public class ReactMember {

	@Id
	private String email;
	private String pw;
	private String nickName;
	private boolean social; // 소셜 계정 회원가입 여부
	
	// 컬렉션 객체임을 JPA 가 알 수 있도록 선언
	// 엔티티가 아닌 값 타입, 임베디드 타입에 대한 테이블 생성.
	// 자동으로 1 : M 관례로 다루게 하는 어노테이션
	@ElementCollection(fetch = FetchType.LAZY)
	@Builder.Default
	private List<ReactMemberRole> memberRoleList = new ArrayList<>();
	
	// 권한 추가 메서드
	public void addRole(ReactMemberRole memberRole)
	{
		memberRoleList.add(memberRole);
	}
	
	// 권한 삭제
	public void clearRole()
	{
		memberRoleList.clear();
	}
	
	public void changeNickName(String nickname)
	{
		this.nickName = nickname;
	}
	
	public void changePw(String pw)
	{
		this.pw = pw;
	}
	
	public void changeSocial(boolean social)
	{
		this.social = social;
	}

	
}













