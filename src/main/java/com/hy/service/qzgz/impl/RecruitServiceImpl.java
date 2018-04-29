package com.hy.service.qzgz.impl;

import com.hy.dto.RecruitDto;
import com.hy.dto.RecruitWithTotalPageDto;
import com.hy.mapper.ms.QzgzRecruitMapper;
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
}
