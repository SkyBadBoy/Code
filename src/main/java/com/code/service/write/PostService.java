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

import com.code.domain.Post;
import com.code.dao.read.ReadPostMapper;
import com.code.dao.write.PostMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="PostCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class PostService {

   
    @Autowired
	private PostMapper WriteMapper;

    @Autowired
	private ReadPostMapper ReadMapper;
 

	@CachePut(key="'Post_'+#p0.ID")  
	@CacheEvict(value = "ReadPostCache",allEntries = true)
	public Post insert(Post obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Post_'+#p0.ID")  
	@CacheEvict(value = "ReadPostCache",allEntries = true)
	public Post update(Post obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Post_'+#p0")  
	@CacheEvict(value = "ReadPostCache",allEntries = true)
	public Post deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Post_'+#p0")  
	@CacheEvict(value = "ReadPostCache",allEntries = true)
	public Post recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadPostCache","PostCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadPostCache","PostCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
