package com.fullstack.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fullstack.dto.MyUserDTO;

// Mapper 로 사용하기 위해 등록
@Mapper
public interface MyUserDao {

	List<MyUserDTO> list();
	
}
