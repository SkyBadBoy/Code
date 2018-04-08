package com.code.dao.write;

import java.util.List;
import java.util.Map;

import com.code.domain.User;
import org.apache.ibatis.annotations.*;


public interface UserMapper {

	void insert(User u);

	void update(User u);

	void delete(String id);

	void recoverById(String id);

	User find(String id);

	User findByOpenid(String openid);

	List<User> query(Map<String, Object> queryMap);

	@Select("select count(*) from t_user where user_status=1")
	int getAllCount();

	@Select("SELECT DATE_FORMAT(user_createTime, '%Y-%m-%d') AS createTime, YEAR (user_createTime) AS YEAR, MONTH (user_createTime) AS MONTH,DAY (user_createTime) AS DAY,count(*) AS count FROM t_user WHERE user_status = 1 AND DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(user_createTime) GROUP BY DATE_FORMAT(user_createTime, '%Y-%m-%d')")
	List<User> queryCountDay();



}
