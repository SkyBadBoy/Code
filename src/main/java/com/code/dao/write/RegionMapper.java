package com.code.dao.write;

import com.code.domain.Region;

import java.util.List;
import java.util.Map;

public interface RegionMapper {

	void insert(Region o);


	void update(Region o);


	Region findByID(String id);


	//注：方法名和要UserMapper.xml中的id一致
	List<Region> query(Map<String, Object> queryMap);
	

}
