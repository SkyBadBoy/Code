package com.code.config.mybatis;


import java.util.Map;

/**
 * Created by MaJian on 18/4/8.
 * @author majian
 * @详情 封装基础的增删改查功能
 */
public interface MyMapper<T> {

    /**
     * 插入
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 更新
     * @param t
     * @return
     */
    int update(T t);

    /**
     * 更具ID删除
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 根据ID恢复
     * @param id
     * @return
     */
    int recoverByID(String id);

    /**
     * 根据条件删除
     * @param queryMap
     * @return
     */
    int deleteByCondition(Map<String,Object> queryMap);

    /**
     * 根据条件恢复
     * @param queryMap
     * @return
     */
    int recoverByCondition(Map<String,Object> queryMap);

}
