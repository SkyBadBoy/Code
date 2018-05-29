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

import com.code.domain.Department;
import com.code.dao.read.ReadDepartmentMapper;
import com.code.dao.write.DepartmentMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="DepartmentCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class DepartmentService {

   
    @Autowired
	private DepartmentMapper WriteMapper;

    @Autowired
	private ReadDepartmentMapper ReadMapper;
 

	@CachePut(key="'Department_'+#p0.ID")  
	@CacheEvict(value = "ReadDepartmentCache",allEntries = true)
	public Department insert(Department obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Department_'+#p0.ID")  
	@CacheEvict(value = "ReadDepartmentCache",allEntries = true)
	public Department update(Department obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Department_'+#p0")  
	@CacheEvict(value = "ReadDepartmentCache",allEntries = true)
	public Department deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Department_'+#p0")  
	@CacheEvict(value = "ReadDepartmentCache",allEntries = true)
	public Department recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadDepartmentCache","DepartmentCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadDepartmentCache","DepartmentCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
