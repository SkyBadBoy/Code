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

import com.code.domain.Admin;
import com.code.dao.read.ReadAdminMapper;
import com.code.dao.write.AdminMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="AdminCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class AdminService {

   
    @Autowired
	private AdminMapper WriteMapper;

    @Autowired
	private ReadAdminMapper ReadMapper;
 

	@CachePut(key="#p0.ID")  
	@CacheEvict(value = "ReadAdminCache",allEntries = true)
	public Admin insert(Admin obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="#p0.ID")  
	@CacheEvict(value = "ReadAdminCache",allEntries = true)
	public Admin update(Admin obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="#p0")  
	@CacheEvict(value = "ReadAdminCache",allEntries = true)
	public Admin deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="#p0")  
	@CacheEvict(value = "ReadAdminCache",allEntries = true)
	public Admin recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadAdminCache","AdminCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadAdminCache","AdminCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
