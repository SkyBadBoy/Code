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

import com.code.domain.User;
import com.code.dao.read.ReadUserMapper;
import com.code.dao.write.UserMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="UserCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class UserService {

   
    @Autowired
	private UserMapper WriteMapper;

    @Autowired
	private ReadUserMapper ReadMapper;
 

	@CachePut(key="'User_'+#p0.ID")
	@CacheEvict(value = "ReadUserCache",allEntries = true)
	public User insert(User obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'User_'+#p0.ID")
	@CacheEvict(value = "ReadUserCache",allEntries = true)
	public User update(User obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'User_'+#p0")
	@CacheEvict(value = "ReadUserCache",allEntries = true)
	public User deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'User_'+#p0")
	@CacheEvict(value = "ReadUserCache",allEntries = true)
	public User recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadUserCache","UserCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadUserCache","UserCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
