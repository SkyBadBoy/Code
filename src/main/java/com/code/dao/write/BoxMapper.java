package com.code.dao.write;

import com.code.config.mybatis.*;
import com.code.domain.Box;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface BoxMapper extends com.code.config.mybatis.Mapper<Box>{

	@Update("update t_box set box_status = 88 ,box_modifyTime = now()  where box_id=#{id} ")
	void delete(@Param("id") String id);

	@Update("update t_box set box_status = 1 ,box_modifyTime = now()  where box_id=#{id} ")
	void recoverById(@Param("id") String id);

	@Select("select sum(box_money) from t_box where box_status=1")
	String getAllMoney();

	@Select("select count(*) from t_box where box_status=1")
	int getAllCount();

	@Select("select count(*) from t_box where box_status=1 and to_days(box_createTime) = to_days(now())")
	int getAllCountDay();

	@Select("SELECT DATE_FORMAT(box_createTime, '%Y-%m-%d') AS createTime, YEAR (box_createTime) AS YEAR, MONTH (box_createTime) AS MONTH,DAY (box_createTime) AS DAY,count(*) AS allCount FROM t_box WHERE box_status = 1 AND DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(box_createTime) GROUP BY DATE_FORMAT(box_createTime, '%Y-%m-%d')")
	List<Box> queryCountDay();

}
