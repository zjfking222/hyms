package com.hy.service.qzgz;

import com.hy.dto.QzgzRecruitLinkmanDto;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/21 10:00
 * @Description:衢州招聘联系人service
 */
public interface RecruitLinkmanService {
    //查询招聘联系人信息
    List<QzgzRecruitLinkmanDto> getLinkman(String filters,int page, int pageSize, String value, String sort, String dir);
    //查询招聘联系人数据总条数
    Integer getLinkmanTotal(String filters,String value);
    //新增招聘联系人信息
    boolean addLinkman(QzgzRecruitLinkmanDto linkmanDto);
    //更新招聘联系人信息
    boolean setLinkman(QzgzRecruitLinkmanDto linkmanDto);
    //删除招聘联系人信息
    boolean delLinkman(int id);
    //移动端查询招聘联系人信息
    List<QzgzRecruitLinkmanDto> getLinkmanAll();
}
