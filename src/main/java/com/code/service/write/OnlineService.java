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

import com.code.domain.Online;
import com.code.dao.read.ReadOnlineMapper;
import com.code.dao.write.OnlineMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="OnlineCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class OnlineService {

   
    @Autowired
	private OnlineMapper WriteMapper;

    @Autowired
	private ReadOnlineMapper ReadMapper;
 

	@CachePut(key="'Online_'+#p0.ID")
	@CacheEvict(value = "ReadOnlineCache",allEntries = true)
	public Online insert(Online obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Online_'+#p0.ID")
	@CacheEvict(value = "ReadOnlineCache",allEntries = true)
	public Online update(Online obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Online_'+#p0")
	@CacheEvict(value = "ReadOnlineCache",allEntries = true)
	public Online deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Online_'+#p0")
	@CacheEvict(value = "ReadOnlineCache",allEntries = true)
	public Online recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadOnlineCache","OnlineCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadOnlineCache","OnlineCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
