package com.hy.service.ms;

import com.hy.dto.CanteenDto;
import com.hy.dto.CanteenWithTotalPageDto;
import com.hy.model.QzgzCanteen;

import java.util.List;

public interface CanteenService {
    /**
     * 获取已上架的菜单
     * @param page 第几页
     * @param number 每页显示数目
     * @param state 状态码
     * @return CanteenWithTotalPageDto
     */
    CanteenWithTotalPageDto getCanteen(int page, int number, String state);
    /**
     * 获取已上架的菜单总数
     * @param number 每页显示的数目
     * @param state 状态码
     * @return Integer
     */
    Integer getTotalPageOfCanteen(int number, String state);

    /**
     * 添加菜单
     * @param name 菜名
     * @param price 价格
     * @return boolean
     */
    boolean addCanteen(String name, double price);

    /**
     * 通过id修改菜单
     * @param name 菜名
     * @param price 价格
     * @param id 菜id
     * @return boolean
     */
    boolean updateCanteen(String name, double price, int id);

    /**
     * 更新菜状态 是否上架
     * @param state 状态
     * @param id 菜id
     * @return boolean
     */
    boolean updateCanteenState(String state, int id);

    /**
     * 通过菜名模糊查询符合的菜
     * @param name 关键词
     * @return List<CanteenDto>
     */
    List<CanteenDto> getCanteenBySearchName(String name);
}
