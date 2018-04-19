package com.hy.service.ms.impl;

import com.hy.dto.OpinionDto;
import com.hy.mapper.ms.OpinionMapper;
import com.hy.model.Opinion;
import com.hy.service.ms.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpinionServiceImpl implements OpinionService{
    @Autowired
    private OpinionMapper opinionMapper;

    @Override
    public boolean insertOpinion(OpinionDto opinionDto) {
        //dto=>model暂时性做法
        Opinion opinion = new Opinion(opinionDto.getName(),
                opinionDto.getDepartment(),opinionDto.getContact(),
                opinionDto.getOpinion());
        return opinionMapper.insertOpinion(opinion) == 1;
    }
}
