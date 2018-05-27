package com.code.service.read;
import java.util.*;



import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.code.domain.Article;
import com.code.dao.read.ReadArticleMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>Service class。</p>
 *
 * @author majian
 * @version 1.00
 */
 @Service
 @CacheConfig(cacheNames="ReadArticleCache") 
 @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class ReadArticleService {

    @Autowired
	private ReadArticleMapper ReadMapper;

	@Cacheable(value = "ArticleCache",key="'Article_'+#p0") 
	public Article findById(String id){
		return ReadMapper.findById(id);
	}

	@Cacheable(keyGenerator = "keyGenerator")
	public List<Article> query(Map<String,Object> queryMap){
		return ReadMapper.query(queryMap);
	}

	@Cacheable(keyGenerator = "keyGenerator")
	public int queryCount(Map<String,Object> queryMap){
		return ReadMapper.queryCount(queryMap);
	}

	@Cacheable(keyGenerator = "keyGenerator")
	public PageInfo<Article> queryPage(Map<String,Object> queryMap, int pageNum, int pageSize){
		Page<Article> page = PageHelper.startPage(pageNum, pageSize);
		if(pageSize==0){//当pageSize=0时查询全部的东西
			page.setPageSizeZero(true);
		}
		page.setOrderBy("Article_CreateTime desc");
		ReadMapper.query(queryMap);
		return page.toPageInfo();
	}

}
