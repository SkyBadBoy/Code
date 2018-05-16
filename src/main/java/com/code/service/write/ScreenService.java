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

import com.code.domain.Screen;
import com.code.dao.read.ReadScreenMapper;
import com.code.dao.write.ScreenMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="ScreenCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class ScreenService {

   
    @Autowired
	private ScreenMapper WriteMapper;

    @Autowired
	private ReadScreenMapper ReadMapper;
 

	@CachePut(key="'Screen_'+#p0.ID")
	@CacheEvict(value = "ReadScreenCache",allEntries = true)
	public Screen insert(Screen obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Screen_'+#p0.ID")
	@CacheEvict(value = "ReadScreenCache",allEntries = true)
	public Screen update(Screen obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Screen_'+#p0")
	@CacheEvict(value = "ReadScreenCache",allEntries = true)
	public Screen deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Screen_'+#p0")
	@CacheEvict(value = "ReadScreenCache",allEntries = true)
	public Screen recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadScreenCache","ScreenCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadScreenCache","ScreenCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
