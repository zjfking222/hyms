package com.hy.mapper.ms;

import com.hy.model.QzgzRecruitLinkman;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/21 9:50
 * @Description:衢州招聘联系人mapper
 */
@Repository
public interface QzgzRecruitLinkmanMapper {
    //查询招聘联系人信息
    List<QzgzRecruitLinkman> selectLinkman(@Param("value") String value, @Param("sort") String sort, @Param("dir") String dir);
    //查询招聘联系人数据总条数
    Integer selectLinkmanTotal(@Param("value") String value);
    //新增招聘联系人信息
    Integer insertLinkman(QzgzRecruitLinkman qzgzRecruitLinkman);
    //更新招聘联系人信息
    Integer updateLinkman(QzgzRecruitLinkman qzgzRecruitLinkman);
    //删除招聘联系人信息
    Integer deleteLinkman(@Param("id") int id);
    //移动端查询招聘联系人信息
    List<QzgzRecruitLinkman> selectLinkmanAll();
}
