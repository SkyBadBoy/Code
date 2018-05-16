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

import com.code.domain.Operation;
import com.code.dao.read.ReadOperationMapper;
import com.code.dao.write.OperationMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="OperationCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class OperationService {

   
    @Autowired
	private OperationMapper WriteMapper;

    @Autowired
	private ReadOperationMapper ReadMapper;
 

//	@CachePut(key="'Operation_'+#p0.ID")
	@CacheEvict(value = "ReadOperationCache",allEntries = true)
	public Operation insert(Operation obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

//	@CachePut(key="'Operation_'+#p0.ID")
	@CacheEvict(value = "ReadOperationCache",allEntries = true)
	public Operation update(Operation obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

//	@CachePut(key="'Operation_'+#p0")
	@CacheEvict(value = "ReadOperationCache",allEntries = true)
	public Operation deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

//	@CachePut(key="'Operation_'+#p0")
	@CacheEvict(value = "ReadOperationCache",allEntries = true)
	public Operation recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

//	@CacheEvict(value = {"ReadOperationCache","OperationCache"},allEntries = true)
	@CacheEvict(value = {"ReadOperationCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

//	@CacheEvict(value = {"ReadOperationCache","OperationCache"},allEntries = true)
	@CacheEvict(value = {"ReadOperationCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
