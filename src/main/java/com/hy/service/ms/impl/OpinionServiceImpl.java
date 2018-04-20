package com.hy.service.ms.impl;

import com.hy.dto.OpinionDto;
import com.hy.mapper.ms.QzgzOpinionMapper;
import com.hy.model.QzgzOpinion;
import com.hy.service.ms.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpinionServiceImpl implements OpinionService{
    @Autowired
    private QzgzOpinionMapper opinionMapper;

    @Override
    public boolean insertOpinion(OpinionDto opinionDto) {
        //dto=>model暂时性做法
        QzgzOpinion opinion = new QzgzOpinion(opinionDto.getName(),
                opinionDto.getDepartment(),opinionDto.getContact(),
                opinionDto.getOpinion());
        return opinionMapper.insertOpinion(opinion) == 1;
    }
}
