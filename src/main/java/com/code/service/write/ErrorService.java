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

import com.code.domain.Error;
import com.code.dao.read.ReadErrorMapper;
import com.code.dao.write.ErrorMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="ErrorCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class ErrorService {

   
    @Autowired
	private ErrorMapper WriteMapper;

    @Autowired
	private ReadErrorMapper ReadMapper;
 

	@CachePut(key="#p0.ID")  
	@CacheEvict(value = "ReadErrorCache",allEntries = true)
	public Error insert(Error obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="#p0.ID")  
	@CacheEvict(value = "ReadErrorCache",allEntries = true)
	public Error update(Error obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="#p0")  
	@CacheEvict(value = "ReadErrorCache",allEntries = true)
	public Error deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="#p0")  
	@CacheEvict(value = "ReadErrorCache",allEntries = true)
	public Error recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadErrorCache","ErrorCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadErrorCache","ErrorCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
