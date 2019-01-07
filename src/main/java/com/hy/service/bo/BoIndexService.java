package com.hy.service.bo;

import com.hy.dto.BOInfoDto;
import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/25 10:17
 * @Description:BO报表登录及其主页相关方法接口
 */
public interface BoIndexService {

    /**
     * @Author 钱敏杰
     * @Description 检查当前用户是否具有登录BO报表的权限
     * @Date 2018/12/25 11:09
     * @Param [empnum]
     * @return boolean true 有权限；false 无权限
     **/
    boolean checkPermission(String empnum);

    /**
     * @Author 钱敏杰
     * @Description 查询当前员工在当前目录下所拥有的报表
     * @Date 2018/12/26 13:50
     * @Param [directoryid, empnum]
     * @return java.util.List<com.hy.dto.BOInfoDto>
     **/
    List<BOInfoDto> getDisplayReport(String directoryid, String empnum);

    /**
     * @Author 钱敏杰
     * @Description 获取当前BO报表查看的url地址
     * @Date 2019/1/3 8:11
     * @Param [reportid]
     * @return java.lang.String
     **/
    String getRealReport(String reportid);
}
