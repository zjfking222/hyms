package com.hy.mapper.ms;

/**
 * MyBatis基础接口
 *
 * @param <K>映射实体Key的类型
 * @param <E>映射实体类型
 */
public interface BaseMapper<K, E> {
    int deleteByPrimaryKey(K id);

    int insert(E record);

    int insertSelective(E record);

    E selectByPrimaryKey(K id);

    int updateByPrimaryKeySelective(E record);

    int updateByPrimaryKey(E record);
}