package com.code.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.code.dao.AdminMapper;
import com.code.domain.Admin;
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
@CacheConfig(cacheNames="adminCache") // 本类内方法指定使用缓存时，默认的名称就是userCache
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class AdminService {

	@Autowired
	private AdminMapper Mapper;
	
	// 因为必须要有返回值，才能保存到数据库中，如果保存的对象的某些字段是需要数据库生成的，
   //那保存对象进数据库的时候，就没必要放到缓存了
	@CachePut(key="#p0.id")  //#p0表示第一个参数
	//必须要有返回值，否则没数据放到缓存中
	public Admin insert(Admin b){
		this.Mapper.insert(b);
		return this.Mapper.find(b.getId());
	}

	@CachePut(key="#p0.id")
	public Admin update(Admin u){
		this.Mapper.update(u);
		//可能只是更新某几个字段而已，所以查次数据库把数据全部拿出来全部
		return this.Mapper.find(u.getId());
	}
	
	@Cacheable(key="#p0") // @Cacheable 会先查询缓存，如果缓存中存在，则不执行方法
	public Admin findById(String id){
		System.err.println("根据id=" + id +"获取用户对象，从数据库中获取");
		return this.Mapper.find(id);
	}

//	@CacheEvict(key="#p0")  //删除缓存名称为userCache,key等于指定的id对应的缓存
//	public void deleteById(String id){
//		this.Mapper.delete(id);
//	}
//
//	//清空缓存名称为userCache（看类名上的注解)下的所有缓存
//	//如果数据失败了，缓存时不会清除的
//	@CacheEvict(allEntries = true)
//	public void deleteAll(){
//		this.Mapper.deleteAll();
//	}


	public List<Admin> query(Map<String,Object> queryMap){
		return this.Mapper.query(queryMap);
	}



	public PageInfo<Admin> queryPage(Map<String,Object> queryMap, int pageNum, int pageSize){
		Page<Admin> page = PageHelper.startPage(pageNum, pageSize);
		//PageHelper会自动拦截到下面这查询sql
		this.Mapper.query(queryMap);
		return page.toPageInfo();
	}
	
	
	
}
