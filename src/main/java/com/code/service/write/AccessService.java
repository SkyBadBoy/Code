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

import com.code.domain.Access;
import com.code.dao.read.ReadAccessMapper;
import com.code.dao.write.AccessMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="AccessCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class AccessService {

   
    @Autowired
	private AccessMapper WriteMapper;

    @Autowired
	private ReadAccessMapper ReadMapper;
 

	//操作太平凡不合适用redis

	@CachePut(key="'Access_'+#p0.ID")
	@CacheEvict(value = "ReadAccessCache",allEntries = true)
	public Access insert(Access obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Access_'+#p0.ID")
	@CacheEvict(value = "ReadAccessCache",allEntries = true)
	public Access update(Access obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Access_'+#p0")
	@CacheEvict(value = "ReadAccessCache",allEntries = true)
	public Access deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Access_'+#p0")
	@CacheEvict(value = "ReadAccessCache",allEntries = true)
	public Access recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadAccessCache","AccessCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadAccessCache","AccessCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
