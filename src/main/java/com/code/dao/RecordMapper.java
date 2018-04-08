package com.code.dao;

import com.code.domain.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


public interface RecordMapper {

	void insert(Record u);
	

	void update(Record u);


	Record find(String id);

	@Update("update t_record set record_status = 88 ,record_modifyTime = now()  where record_id=#{id} ")
	void delete(@Param("id") String id);

	//注：方法名和要UserMapper.xml中的id一致
	List<Record> query(Map<String, Object> queryMap);


	int queryCount(Map<String, Object> queryMap);

	@Select("select count(*) from t_record where record_boxid=#{boxid} and record_status != 88")
	int allCount(@Param("boxid") String boxid);

	@Select("select count(*) from t_record where record_boxid=#{boxid} and record_num = #{num} and record_status != 88")
	int arawCount(@Param("boxid") String boxid, @Param("num") String num);


}
