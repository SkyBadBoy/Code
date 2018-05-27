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

import com.code.domain.Baseinfo;
import com.code.dao.read.ReadBaseinfoMapper;
import com.code.dao.write.BaseinfoMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="BaseinfoCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class BaseinfoService {

   
    @Autowired
	private BaseinfoMapper WriteMapper;

    @Autowired
	private ReadBaseinfoMapper ReadMapper;
 

	@CachePut(key="'Baseinfo_'+#p0.ID")
	@CacheEvict(value = "ReadBaseinfoCache",allEntries = true)
	public Baseinfo insert(Baseinfo obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Baseinfo_'+#p0.ID")
	@CacheEvict(value = "ReadBaseinfoCache",allEntries = true)
	public Baseinfo update(Baseinfo obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Baseinfo_'+#p0")
	@CacheEvict(value = "ReadBaseinfoCache",allEntries = true)
	public Baseinfo deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Baseinfo_'+#p0")
	@CacheEvict(value = "ReadBaseinfoCache",allEntries = true)
	public Baseinfo recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadBaseinfoCache","BaseinfoCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadBaseinfoCache","BaseinfoCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
