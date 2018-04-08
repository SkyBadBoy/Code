package com.code.service.write;

import com.code.dao.write.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.code.domain.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.Map;
import java.util.List;

@Service
@CacheConfig(cacheNames="userCache")
@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@CachePut(key="#p0.id")
	public User insertUser(User u){
		this.userMapper.insert(u);
		return this.userMapper.find(u.getId());
	}

	@CachePut(key="#p0.id")
	public User updateUser(User u){
		this.userMapper.update(u);
		return this.userMapper.find(u.getId());
	}
	
	@Cacheable(key="#p0")
	public User findById(String id){
		System.err.println("根据id=" + id +"获取用户对象，从数据库中获取");
		return this.userMapper.find(id);
	}

	@Cacheable(key="#p0")
	public User findByOpenid(String openid){
		System.err.println("根据id=" + openid +"获取用户对象，从数据库中获取");
		return this.userMapper.findByOpenid(openid);
	}
//
	@CachePut(key="#p0")
	public User deleteById(String id){
		this.userMapper.delete(id);
		return this.userMapper.find(id);
	}

	@CachePut(key="#p0")
	public User recoverById(String id){
		this.userMapper.recoverById(id);
		return this.userMapper.find(id);
	}

	public List<User> query(Map<String,Object> queryMap){
		return this.userMapper.query(queryMap);
	}


	public PageInfo<User> queryPage(Map<String,Object> queryMap, int pageNum, int pageSize){
		Page<User> page = PageHelper.startPage(pageNum, pageSize);
		this.userMapper.query(queryMap);
		return page.toPageInfo();
	}

	public int getAllCount(){
		return this.userMapper.getAllCount();
	}
	public List<User> queryCountDay(){
		return this.userMapper.queryCountDay();
	}
	
}
