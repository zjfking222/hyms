package com.hy.service.qzgz.impl;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.dto.QzgzRecruitLinkmanDto;
import com.hy.mapper.ms.QzgzRecruitLinkmanMapper;
import com.hy.model.QzgzRecruitLinkman;
import com.hy.service.qzgz.RecruitLinkmanService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/21 10:15
 * @Description:衢州招聘联系人serviceImpl
 */
@Service
public class RecruitLinkmanServiceImpl implements RecruitLinkmanService{
    @Autowired
    private QzgzRecruitLinkmanMapper linkmanMapper;

    @Override
    //查询招聘联系人信息
    public List<QzgzRecruitLinkmanDto> getLinkman(String filters,int page, int pageSize, String value, String sort, String dir){
        PageHelper.startPage(page, pageSize);
        return DTOUtil.populateList(linkmanMapper.selectLinkman(filters, value, sort, dir), QzgzRecruitLinkmanDto.class);
    }

    @Override
    //查询招聘联系人数据总条数
    public Integer getLinkmanTotal(String filters,String value){
        return linkmanMapper.selectLinkmanTotal(filters, value);
    }

    @Override
    //新增招聘联系人信息
    public boolean addLinkman(QzgzRecruitLinkmanDto linkmanDto){
        QzgzRecruitLinkman linkman = DTOUtil.populate(linkmanDto, QzgzRecruitLinkman.class);
        linkman.setCreater(SecurityUtil.getLoginid());
        linkman.setModifier(SecurityUtil.getLoginid());
        return linkmanMapper.insertLinkman(linkman) == 1;
    }

    @Override
    //更新招聘联系人信息
    public boolean setLinkman(QzgzRecruitLinkmanDto linkmanDto){
        QzgzRecruitLinkman linkman = DTOUtil.populate(linkmanDto, QzgzRecruitLinkman.class);
        linkman.setModifier(SecurityUtil.getLoginid());
        return linkmanMapper.updateLinkman(linkman) == 1;
    }

    @Override
    //删除招聘联系人信息
    public boolean delLinkman(int id){
        return linkmanMapper.deleteLinkman(id) == 1;
    }

    @Override
    //移动端查询招聘联系人信息
    public List<QzgzRecruitLinkmanDto> getLinkmanAll(){
        List<QzgzRecruitLinkman> linkmen = linkmanMapper.selectLinkmanAll();
        return DTOUtil.populateList(linkmen, QzgzRecruitLinkmanDto.class);
    }
}
