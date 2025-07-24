package com.fullstack.service;

import java.util.stream.Collectors;

import com.fullstack.domain.ReactMember;
import com.fullstack.domain.secure.ReactMemberDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface MemberService {

	ReactMemberDTO getKakaoMember(String accessToken);
	
	default ReactMemberDTO entityToDTO(ReactMember member)
	{
		ReactMemberDTO dto = new ReactMemberDTO(member.getEmail(), 
				member.getPw(), 
				member.getNickName(), 
				member.isSocial(),
				member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList()));
		
		return dto;
	}
}
