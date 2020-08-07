package com.exe.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.exe.dto.BoardDTO;

public class BoardDAO {

private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) throws Exception{
		this.sessionTemplate = sessionTemplate;
	}
	
	public int getMaxNum() {
		
		int getMaxNum;
		
		getMaxNum = sessionTemplate.selectOne("boardMapper.getMaxNum");
		
		return getMaxNum;
	}
	
	public void insert(BoardDTO dto) {
		
		sessionTemplate.insert("boardMapper.insertData", dto);
	}
	
	public int getDataCount(String searchKey, String searchValue){
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("searchKey", searchKey);
		params.put("searchValue", searchValue);
		
		int count = sessionTemplate.selectOne("boardMapper.getDataCount", params);
		
		return count;
	}
	
	public List<BoardDTO> list(int start, int end, String searchKey, String searchValue){
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("start", start);
		params.put("end", end);
		params.put("searchValue", searchValue);
		params.put("searchKey", searchKey);
		
		List<BoardDTO> list = sessionTemplate.selectList("boardMapper.list", params);
		
		return list;
	}
	
	public BoardDTO getData(int num){
		
		BoardDTO dto = sessionTemplate.selectOne("boardMapper.getData", num);
		
		return dto;
	}
	
	public void updateHitCount(int num) {
		
		sessionTemplate.update("boardMapper.updateHitCount", num);
	}
	
	public void updateData(BoardDTO dto) {
		sessionTemplate.update("boardMapper.updateData", dto);
	}
	
	public void deleteData(int num) {
		sessionTemplate.delete("boardMapper.deleteData", num);
	}
	
}
