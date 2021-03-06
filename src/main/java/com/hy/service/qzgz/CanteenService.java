package com.hy.service.qzgz;

import com.hy.dto.CanteenDto;
import com.hy.dto.CanteenWithTotalPageDto;
import com.hy.model.QzgzCanteen;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface CanteenService {

    List<CanteenDto> getCanteenToday(int meal);

    boolean updateCanteenZan(int zan, int id);

    List<CanteenDto> getCanteen(String filters, int pageNum, int pageSize, String value, String date, Integer meal, String sort, String dir) throws ParseException;

    int getCanteenTotal(String filters, String value, String date, Integer meal) throws ParseException;

    boolean deleteCanteen(int id);

    Integer insertCanteenList(String filepath);
//    /**
//     * 获取已上架的菜单
//     * @param page 第几页
//     * @param number 每页显示数目
//     * @param state 状态码
//     * @return CanteenWithTotalPageDto
//     */
//    CanteenWithTotalPageDto getCanteen(int page, int number, String state);
//    /**
//     * 获取已上架的菜单总数
//     * @param number 每页显示的数目
//     * @param state 状态码
//     * @return Integer
//     */
//    Integer getTotalPageOfCanteen(int number, String state);
//
//    /**
//     * 添加菜单
//     * @param name 菜名
//     * @param type 菜类型
//     * @return boolean
//     */
//    boolean addCanteen(String name, String type);
//
//    /**
//     * 通过id修改菜单
//     * @param name 菜名
//     * @param type 菜类型
//     * @param id 菜id
//     * @return boolean
//     */
//    boolean updateCanteen(String name, String type, int id);
//
//    /**
//     * 更新菜状态 是否上架
//     * @param state 状态
//     * @param id 菜id
//     * @return boolean
//     */
//    boolean updateCanteenState(String state, int id);
//
//    /**
//     * 通过菜名模糊查询符合的菜
//     * @param name 关键词
//     * @return List<CanteenDto>
//     */
//    List<CanteenDto> getCanteenBySearchName(String name, String state);
//
//    CanteenDto getCanteenById(int id);

}
