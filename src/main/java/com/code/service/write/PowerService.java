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

import com.code.domain.Power;
import com.code.dao.read.ReadPowerMapper;
import com.code.dao.write.PowerMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="PowerCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class PowerService {

   
    @Autowired
	private PowerMapper WriteMapper;

    @Autowired
	private ReadPowerMapper ReadMapper;
 

	@CachePut(key="'Power_'+#p0.ID")  
	@CacheEvict(value = "ReadPowerCache",allEntries = true)
	public Power insert(Power obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Power_'+#p0.ID")  
	@CacheEvict(value = "ReadPowerCache",allEntries = true)
	public Power update(Power obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Power_'+#p0")  
	@CacheEvict(value = "ReadPowerCache",allEntries = true)
	public Power deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Power_'+#p0")  
	@CacheEvict(value = "ReadPowerCache",allEntries = true)
	public Power recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadPowerCache","PowerCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadPowerCache","PowerCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}


}
