package com.code.service.write;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

import com.code.domain.Activity;
import com.code.dao.read.ReadActivityMapper;
import com.code.dao.write.ActivityMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="ActivityCache")
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class ActivityService {


	@Autowired
	private ActivityMapper WriteMapper;

	@Autowired
	private ReadActivityMapper ReadMapper;


	@CachePut(key="#p0.ID")
	@CacheEvict(value = "ReadActivityCache",allEntries = true)
	public Activity insert(Activity obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="#p0.ID")
	@CacheEvict(value = "ReadActivityCache",allEntries = true)
	public Activity update(Activity obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="#p0")
	@CacheEvict(value = "ReadActivityCache",allEntries = true)
	public Activity deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="#p0")
	@CacheEvict(value = "ReadActivityCache",allEntries = true)
	public Activity recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadActivityCache","ActivityCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadActivityCache","ActivityCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
