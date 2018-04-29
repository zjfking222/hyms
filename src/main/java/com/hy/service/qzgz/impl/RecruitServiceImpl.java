package com.hy.service.qzgz.impl;

import com.hy.dto.RecruitWithTotalPageDto;
import com.hy.mapper.ms.QzgzRecruitMapper;
import com.hy.service.qzgz.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruitServiceImpl implements RecruitService{

    @Autowired
    private QzgzRecruitMapper recruitMapper;

    @Override
    public RecruitWithTotalPageDto getRecruit(int page, int number) {

        return null;
    }

    @Override
    public Integer getCountOfRecruit() {
        return recruitMapper.selectCountOfRecruit();
    }
}
