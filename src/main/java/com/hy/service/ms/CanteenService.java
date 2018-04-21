package com.hy.service.ms;

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
     * 后台管理调试接口
     */
    List<QzgzCanteen> getAllCanteen();
}
