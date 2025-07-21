package com.fullstack.repository.secure;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fullstack.domain.ReactMember;

public interface MemberRepository extends JpaRepository<ReactMember, String> {
	
	// 멤버를 조회하는 권한까지 같이 나오도록 쿼리 설정
	// EntityGraph 를 사용하면 lazy 설정의 연결 엔티티까지 자동 조인됨
	@EntityGraph(attributePaths = {"memberRoleList"})
	@Query("Select m from ReactMember m where m.email =:email")
	ReactMember getWithRoles(@Param("email") String email);
	
	
}
