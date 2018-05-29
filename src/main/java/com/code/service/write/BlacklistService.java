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

import com.code.domain.Blacklist;
import com.code.dao.read.ReadBlacklistMapper;
import com.code.dao.write.BlacklistMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="BlacklistCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class BlacklistService {

   
    @Autowired
	private BlacklistMapper WriteMapper;

    @Autowired
	private ReadBlacklistMapper ReadMapper;
 

	@CachePut(key="'Blacklist_'+#p0.ID")  
	@CacheEvict(value = "ReadBlacklistCache",allEntries = true)
	public Blacklist insert(Blacklist obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Blacklist_'+#p0.ID")  
	@CacheEvict(value = "ReadBlacklistCache",allEntries = true)
	public Blacklist update(Blacklist obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Blacklist_'+#p0")  
	@CacheEvict(value = "ReadBlacklistCache",allEntries = true)
	public Blacklist deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Blacklist_'+#p0")  
	@CacheEvict(value = "ReadBlacklistCache",allEntries = true)
	public Blacklist recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadBlacklistCache","BlacklistCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadBlacklistCache","BlacklistCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
