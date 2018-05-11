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

import com.code.domain.Feedback;
import com.code.dao.read.ReadFeedbackMapper;
import com.code.dao.write.FeedbackMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="FeedbackCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class FeedbackService {

   
    @Autowired
	private FeedbackMapper WriteMapper;

    @Autowired
	private ReadFeedbackMapper ReadMapper;
 

	@CachePut(key="#p0.ID")  
	@CacheEvict(value = "ReadFeedbackCache",allEntries = true)
	public Feedback insert(Feedback obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="#p0.ID")  
	@CacheEvict(value = "ReadFeedbackCache",allEntries = true)
	public Feedback update(Feedback obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="#p0")  
	@CacheEvict(value = "ReadFeedbackCache",allEntries = true)
	public Feedback deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="#p0")  
	@CacheEvict(value = "ReadFeedbackCache",allEntries = true)
	public Feedback recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadFeedbackCache","FeedbackCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadFeedbackCache","FeedbackCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
