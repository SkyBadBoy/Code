package com.code.dao.write;

import com.code.domain.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

public interface AdminMapper {

	void insert(Admin u);
	

	void update(Admin u);


	Admin find(String id);

	
	//注：方法名和要UserMapper.xml中的id一致
	List<Admin> query(Map<String, Object> queryMap);
	

}
