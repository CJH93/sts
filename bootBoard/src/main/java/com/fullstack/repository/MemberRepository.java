package com.fullstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullstack.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
