package com.code.service.read;

import com.code.dao.read.ReadBoxMapper;
import com.code.domain.Box;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames="boxCache") // 本类内方法指定使用缓存时，默认的名称就是userCache
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class ReadBoxService {

	@Autowired
	private ReadBoxMapper boxMapper;

	
	@Cacheable(key="#p0") // @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法
	public Box findById(String id){
		System.err.println("根据id=" + id +"获取用户对象，从数据库中获取");
		return this.boxMapper.findByID(id);
	}

	@CachePut(key="#p0")
	public Box deleteById(String id){
		this.boxMapper.delete(id);
		return this.boxMapper.findByID(id);
	}

	@CachePut(key="#p0")
	public Box recoverById(String id){
		this.boxMapper.recoverById(id);
		return this.boxMapper.findByID(id);
	}


	public List<Box> query(Map<String,Object> queryMap){
		return this.boxMapper.query(queryMap);
	}

	@Cacheable(keyGenerator = "keyGenerator")
	public PageInfo<Box> queryPage(Map<String,Object> queryMap, int pageNum, int pageSize){
		System.err.println("从数据库获取");
		Page<Box> page = PageHelper.startPage(pageNum, pageSize);
		this.boxMapper.query(queryMap);
		return page.toPageInfo();
	}

	public String getAllMoney(){
		return this.boxMapper.getAllMoney();
	}
	public int getAllCount(){
		return this.boxMapper.getAllCount();
	}

	public int getAllCountDay(){
		return this.boxMapper.getAllCountDay();
	}

	public List<Box> queryCountDay(){
		return this.boxMapper.queryCountDay();
	}



}
