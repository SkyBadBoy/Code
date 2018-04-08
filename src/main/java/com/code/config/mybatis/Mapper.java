package com.code.config.mybatis;

import com.code.domain.Region;

import java.util.List;
import java.util.Map;

/**
 * Created by MaJian on 18/4/8.
 */
public interface Mapper<T> {

    void insert(T t);


    void update(T t);


    T findByID(String id);


    List<T> query(Map<String, Object> queryMap);
}
