package com.code.service.read;
import java.util.*;



import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.code.domain.Admin;
import com.code.dao.read.ReadAdminMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>Service class。</p>
 *
 * @author majian
 * @version 1.00
 */
 @Service
 @CacheConfig(cacheNames="ReadAdminCache") 
 @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class ReadAdminService {

    @Autowired
	private ReadAdminMapper ReadMapper;

	@Cacheable(value = "AdminCache",key="#p0") 
	public Admin findById(String id){
		return ReadMapper.findById(id);
	}

	@Cacheable(keyGenerator = "keyGenerator")
	public List<Admin> query(Map<String,Object> queryMap){
		return ReadMapper.query(queryMap);
	}

	@Cacheable(keyGenerator = "keyGenerator")
	public int queryCount(Map<String,Object> queryMap){
		return ReadMapper.queryCount(queryMap);
	}

	@Cacheable(keyGenerator = "keyGenerator")
	public PageInfo<Admin> queryPage(Map<String,Object> queryMap, int pageNum, int pageSize){
		Page<Admin> page = PageHelper.startPage(pageNum, pageSize);
		page.setOrderBy("Admin_CreateTime desc");
		ReadMapper.query(queryMap);
		return page.toPageInfo();
	}

}
