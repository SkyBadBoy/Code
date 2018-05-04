package com.code.service.write;

import com.code.dao.read.ReadBoxMapper;
import com.code.dao.write.BoxMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.code.domain.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
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
public class BoxService {

	@Autowired
	private BoxMapper boxMapper;

	@Autowired
	private ReadBoxMapper readMapper;
	
	// 因为必须要有返回值，才能保存到数据库中，如果保存的对象的某些字段是需要数据库生成的，
   //那保存对象进数据库的时候，就没必要放到缓存了
	@CachePut(key="#p0.id")  //#p0表示第一个参数
	@CacheEvict(value = "ReadBoxCache",allEntries = true)
	//必须要有返回值，否则没数据放到缓存中
	public Box insert(Box b){
		this.boxMapper.insert(b);
		return this.readMapper.findByID(b.getId());
	}

	@CachePut(key="#p0.id")
	public Box update(Box u){
		this.boxMapper.update(u);
		//可能只是更新某几个字段而已，所以查次数据库把数据全部拿出来全部
		return this.readMapper.findByID(u.getId());
	}
	
	@Cacheable(key="#p0") // @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法
	public Box findById(String id){
		System.err.println("根据id=" + id +"获取用户对象，从数据库中获取");
		return this.readMapper.findByID(id);
	}

	@CachePut(key="#p0")
	public Box deleteById(String id){
		this.boxMapper.delete(id);
		return this.readMapper.findByID(id);
	}

	@CachePut(key="#p0")
	public Box recoverById(String id){
		this.boxMapper.recoverById(id);
		return this.readMapper.findByID(id);
	}


	public List<Box> query(Map<String,Object> queryMap){
		return this.readMapper.query(queryMap);
	}

	@Cacheable(keyGenerator = "keyGenerator")
	public PageInfo<Box> queryPage(Map<String,Object> queryMap, int pageNum, int pageSize){
		System.err.println("从数据库获取");
		Page<Box> page = PageHelper.startPage(pageNum, pageSize);
		this.readMapper.query(queryMap);
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
