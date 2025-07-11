package com.fullstack.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fullstack.dto.BBSDto;

@Repository
public class BBSCommandDao implements BBSDaoInter {

	@Autowired
	private JdbcTemplate template;
	
	@Override
	public List<BBSDto> listDao() {
		System.out.println("listDao() 실행됨");
		String sql = "Select * from bbs order by id desc";
		List<BBSDto> list = template.query(sql, new BeanPropertyRowMapper<BBSDto>(BBSDto.class));
		
		return list;
	}
	
	@Override
	public BBSDto viewDao(String id) {
		System.out.println("viewDao() 실행됨");
		String sql = "Select * from bbs where id = " + id;
		return template.queryForObject(sql, new BeanPropertyRowMapper<BBSDto>(BBSDto.class));
		
	}

	@Override
	public int writeDao(String writer, String title, String content) {
		System.out.println("viewDao() 실행됨");
		String sql = "Insert into bbs (id, writer, title, content) values (1, ?, ?, ?)";
		return template.update(sql, writer, title, content);
		
	}
	
	
	
}
