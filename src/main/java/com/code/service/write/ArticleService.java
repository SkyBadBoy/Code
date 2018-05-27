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

import com.code.domain.Article;
import com.code.dao.read.ReadArticleMapper;
import com.code.dao.write.ArticleMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="ArticleCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class ArticleService {

   
    @Autowired
	private ArticleMapper WriteMapper;

    @Autowired
	private ReadArticleMapper ReadMapper;
 

	@CachePut(key="'Article_'+#p0.ID")
	@CacheEvict(value = "ReadArticleCache",allEntries = true)
	public Article insert(Article obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Article_'+#p0.ID")
	@CacheEvict(value = "ReadArticleCache",allEntries = true)
	public Article update(Article obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Article_'+#p0")
	@CacheEvict(value = "ReadArticleCache",allEntries = true)
	public Article deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Article_'+#p0")
	@CacheEvict(value = "ReadArticleCache",allEntries = true)
	public Article recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadArticleCache","ArticleCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadArticleCache","ArticleCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
