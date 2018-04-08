package com.code.dao.write;

import com.code.domain.WeChatInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


public interface WeChatInfoMapper {

	void insert(WeChatInfo u);

	void update(WeChatInfo u);

	WeChatInfo find(String id);


	List<WeChatInfo> query(Map<String, Object> queryMap);

}
