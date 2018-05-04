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

import com.code.domain.Keyword;
import com.code.dao.read.ReadKeywordMapper;
import com.code.dao.write.KeywordMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="KeywordCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class KeywordService {

   
    @Autowired
	private KeywordMapper WriteMapper;

    @Autowired
	private ReadKeywordMapper ReadMapper;
 

	@CachePut(key="#p0.ID")  
	@CacheEvict(value = "ReadKeywordCache",allEntries = true)
	public Keyword insert(Keyword obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="#p0.ID")  
	@CacheEvict(value = "ReadKeywordCache",allEntries = true)
	public Keyword update(Keyword obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="#p0")  
	@CacheEvict(value = "ReadKeywordCache",allEntries = true)
	public Keyword deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="#p0")  
	@CacheEvict(value = "ReadKeywordCache",allEntries = true)
	public Keyword recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadKeywordCache","KeywordCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadKeywordCache","KeywordCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
