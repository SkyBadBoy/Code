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

import com.code.domain.Role;
import com.code.dao.read.ReadRoleMapper;
import com.code.dao.write.RoleMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="RoleCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class RoleService {

   
    @Autowired
	private RoleMapper WriteMapper;

    @Autowired
	private ReadRoleMapper ReadMapper;
 

	@CachePut(key="'Role_'+#p0.ID")
	@CacheEvict(value = "ReadRoleCache",allEntries = true)
	public Role insert(Role obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Role_'+#p0.ID")
	@CacheEvict(value = "ReadRoleCache",allEntries = true)
	public Role update(Role obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Role_'+#p0")
	@CacheEvict(value = "ReadRoleCache",allEntries = true)
	public Role deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Role_'+#p0")
	@CacheEvict(value = "ReadRoleCache",allEntries = true)
	public Role recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadRoleCache","RoleCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadRoleCache","RoleCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
