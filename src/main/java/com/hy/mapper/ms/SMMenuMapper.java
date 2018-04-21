package com.hy.mapper.ms;

import com.hy.model.SMMenu;

public interface SMMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SMMenu record);

    int insertSelective(SMMenu record);

    SMMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SMMenu record);

    int updateByPrimaryKey(SMMenu record);
}