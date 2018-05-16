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

import com.code.domain.Menu;
import com.code.dao.read.ReadMenuMapper;
import com.code.dao.write.MenuMapper;

/**
 * <p>Service class。</p>
 *
 * @author majian 自动生成器
 * @version 1.00
 */
@Service
@CacheConfig(cacheNames="MenuCache") 
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class MenuService {

   
    @Autowired
	private MenuMapper WriteMapper;

    @Autowired
	private ReadMenuMapper ReadMapper;
 

	@CachePut(key="'Menu_'+#p0.ID")
	@CacheEvict(value = "ReadMenuCache",allEntries = true)
	public Menu insert(Menu obj){
		WriteMapper.insert(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Menu_'+#p0.ID")
	@CacheEvict(value = "ReadMenuCache",allEntries = true)
	public Menu update(Menu obj){
		WriteMapper.update(obj);
		return ReadMapper.findById(obj.getID());
	}

	@CachePut(key="'Menu_'+#p0")
	@CacheEvict(value = "ReadMenuCache",allEntries = true)
	public Menu deleteById(String id){
		WriteMapper.deleteById(id);
		return ReadMapper.findById(id);
	}

	@CachePut(key="'Menu_'+#p0")
	@CacheEvict(value = "ReadMenuCache",allEntries = true)
	public Menu recoverByID(String id){
		WriteMapper.recoverByID(id);
		return ReadMapper.findById(id);
	}

	@CacheEvict(value = {"ReadMenuCache","MenuCache"},allEntries = true)
	public int deleteByCondition(Map<String,Object> queryMap){
		return WriteMapper.deleteByCondition(queryMap);
	}

	@CacheEvict(value = {"ReadMenuCache","MenuCache"},allEntries = true)
	public int recoverByCondition(Map<String,Object> queryMap){
		return WriteMapper.recoverByCondition(queryMap);
	}

}
