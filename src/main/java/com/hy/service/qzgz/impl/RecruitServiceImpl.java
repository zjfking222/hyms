package com.hy.service.qzgz.impl;

import com.hy.common.SecurityUtil;
import com.hy.dto.RecruitDto;
import com.hy.dto.RecruitWithTotalPageDto;
import com.hy.mapper.ms.QzgzRecruitMapper;
import com.hy.model.QzgzRecruit;
import com.hy.service.qzgz.RecruitService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitServiceImpl implements RecruitService{

    @Autowired
    private QzgzRecruitMapper recruitMapper;

    @Override
    public RecruitWithTotalPageDto getRecruit(int page, int number) {
        int startRow = (page - 1) * number;
        List<RecruitDto> recruits = DTOUtil
                .populateList(recruitMapper.selectRecruit(startRow, number),RecruitDto.class);
        return recruits==null?
                null:new RecruitWithTotalPageDto(recruits,getTotalPageOfRecruit(number));
    }

    @Override
    public Integer getTotalPageOfRecruit(int number) {
        int countOfRecruit = recruitMapper.selectCountOfRecruit();
        double totalPageD = (double) countOfRecruit / (double) number;
        return (int)Math.ceil(totalPageD);
    }

    @Override
    public boolean addRecruit(RecruitDto recruitDto) {
        QzgzRecruit recruit = DTOUtil.populate(recruitDto,QzgzRecruit.class);
        recruit.setCreater(SecurityUtil.getLoginid());
        recruit.setModifier(SecurityUtil.getLoginid());
        return recruitMapper.insertRecruit(recruit) == 1;
    }

    @Override
    public boolean setRecruit(RecruitDto recruitDto) {
        QzgzRecruit recruit = DTOUtil.populate(recruitDto,QzgzRecruit.class);
        recruit.setModifier(SecurityUtil.getLoginid());
        return recruitMapper.updateRecruit(recruit) == 1;
    }

    @Override
    public boolean delRecruit(int id) {
        return recruitMapper.deleteRecruit(id) == 1;
    }

    @Override
    public RecruitDto getRecruit(int id) {
        return DTOUtil.populate(recruitMapper.selectRecruitById(id),
                RecruitDto.class);
    }
}
