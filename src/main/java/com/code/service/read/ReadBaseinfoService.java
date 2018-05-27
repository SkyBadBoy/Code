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
import com.code.domain.Baseinfo;
import com.code.dao.read.ReadBaseinfoMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>Service class。</p>
 *
 * @author majian
 * @version 1.00
 */
 @Service
 @CacheConfig(cacheNames="ReadBaseinfoCache") 
 @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class ReadBaseinfoService {

    @Autowired
	private ReadBaseinfoMapper ReadMapper;

	@Cacheable(value = "BaseinfoCache",key="'Baseinfo_'+#p0") 
	public Baseinfo findById(String id){
		return ReadMapper.findById(id);
	}

	@Cacheable(keyGenerator = "keyGenerator")
	public List<Baseinfo> query(Map<String,Object> queryMap){
		return ReadMapper.query(queryMap);
	}

	@Cacheable(keyGenerator = "keyGenerator")
	public int queryCount(Map<String,Object> queryMap){
		return ReadMapper.queryCount(queryMap);
	}

	@Cacheable(keyGenerator = "keyGenerator")
	public PageInfo<Baseinfo> queryPage(Map<String,Object> queryMap, int pageNum, int pageSize){
		Page<Baseinfo> page = PageHelper.startPage(pageNum, pageSize);
		if(pageSize==0){//当pageSize=0时查询全部的东西
			page.setPageSizeZero(true);
		}
		page.setOrderBy("Baseinfo_CreateTime desc");
		ReadMapper.query(queryMap);
		return page.toPageInfo();
	}

}
