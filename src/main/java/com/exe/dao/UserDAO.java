package com.exe.dao;

import org.mybatis.spring.SqlSessionTemplate;

import com.exe.dto.UserDTO;

public class UserDAO {
	
	private SqlSessionTemplate sessionTemplate;

	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	
	public void registUser(UserDTO dto) {
		sessionTemplate.insert("userMapper.registUser",dto);
	}
	
	public UserDTO userOne(String UserID) {
		
		UserDTO dto = sessionTemplate.selectOne("userMapper.userOne", UserID);
		
		return dto;
	}

}
