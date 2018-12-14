package com.hy.service.qzgz.impl;

import com.hy.common.SecurityUtil;
import com.hy.dto.OpinionDto;
import com.hy.dto.OpinionForAdminDto;
import com.hy.dto.OpinionForAdminWithPageDto;
import com.hy.mapper.ms.QzgzOpinionMapper;
import com.hy.model.QzgzOpinion;
import com.hy.service.qzgz.OpinionService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpinionServiceImpl implements OpinionService{
    @Autowired
    private QzgzOpinionMapper opinionMapper;

    @Override
    public boolean addOpinion(OpinionDto opinionDto) {

        QzgzOpinion opinion = DTOUtil.populate(opinionDto,QzgzOpinion.class);
        opinion.setCreater("-1");
        opinion.setModifier("-1");
        return opinionMapper.insertOpinion(opinion) == 1;
    }

    @Override
    public OpinionForAdminWithPageDto getOpinion(int page, int number, String state) {
        //用户输入的页数转化为startRow
        int startRow = (page - 1) * number;
        List<OpinionForAdminDto> opinions =
                DTOUtil.populateList(opinionMapper.selectOpinion(state,startRow,number),
                        new ArrayList<OpinionForAdminDto>(),OpinionForAdminDto.class);
        return opinions==null?
                null: new OpinionForAdminWithPageDto(opinions,
                getTotalPageOfOpinion(number,state));
    }

    @Override
    public int getTotalPageOfOpinion(int number, String state) {
        int countOfOpinion = opinionMapper.selectCountOfOpinion(state);
        double totalPageD = (double)countOfOpinion/(double)number;
        return (int)Math.ceil(totalPageD);
    }

    @Override
    public boolean setStateOfOpinion(int id, String state) {
        QzgzOpinion opinion = new QzgzOpinion();
        opinion.setId(id);
        opinion.setState(state);
        opinion.setModifier(SecurityUtil.getLoginid());
        return opinionMapper.updateStateOfOpinion(opinion) == 1;
    }

}
