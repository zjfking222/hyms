package com.hy.service.ms;

import com.hy.dto.CanteenDto;
import com.hy.dto.CanteenWithTotalPageDto;

import java.util.List;

public interface CanteenService {
    /**
     * 获取已上架的菜单
     * @param page 第几页
     * @param number 每页显示数目
     * @return CanteenWithTotalPageDto
     */
    CanteenWithTotalPageDto getCanteen(int page, int number);
    /**
     * 获取已上架的菜单总数
     * @param number 每页显示的数目
     * @return Integer
     */
    Integer getTotalPageOfCanteen(int number);
}
