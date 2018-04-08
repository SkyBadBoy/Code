package com.code.config.mybatis;

import java.util.List;
import java.util.Map;

/**
 * Created by MaJian on 18/4/8.
 */
public interface ReadMapper<T> {



    T findByID(String id);


    List<T> query(Map<String, Object> queryMap);
}
