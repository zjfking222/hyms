package com.hy.service.qzgz.impl;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.dto.QzgzStudyDto;
import com.hy.mapper.ms.QzgzStudyMapper;
import com.hy.model.QzgzStudy;
import com.hy.service.qzgz.StudyService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyServiceImpl implements StudyService {

    @Autowired
    private QzgzStudyMapper qzgzStudyMapper;

    @Override
    public List<QzgzStudyDto> getList(String filters, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<QzgzStudy> list = qzgzStudyMapper.selectStudys(filters);

        return DTOUtil.populateList(list, QzgzStudyDto.class);
    }

    @Override
    public QzgzStudyDto getStudy(int id) {
        QzgzStudy qzgzStudy = qzgzStudyMapper.selectByPrimaryKey(id);
        QzgzStudyDto dto = new QzgzStudyDto();
        if (qzgzStudy == null)
            return dto;
        return DTOUtil.populate(qzgzStudy, dto);
    }

    @Override
    public int getTotal(String filters) {
        return qzgzStudyMapper.selectStudysTotal(filters);
    }

    @Override
    public QzgzStudyDto add(QzgzStudyDto dto) {
        QzgzStudy qzgzStudy = DTOUtil.populate(dto, QzgzStudy.class);
        qzgzStudy.setCreater(SecurityUtil.getLoginid());
        qzgzStudy.setCreatername(SecurityUtil.getUserName());
        qzgzStudy.setModifier(SecurityUtil.getLoginid());
        int id = qzgzStudyMapper.insertSelective(qzgzStudy);
        dto.setId(qzgzStudy.getId());
        return dto;
    }

    @Override
    public int update(QzgzStudyDto dto) {
        QzgzStudy qzgzStudy = DTOUtil.populate(dto, QzgzStudy.class);
        qzgzStudy.setModifier(SecurityUtil.getLoginid());
        return qzgzStudyMapper.updateByPrimaryKeySelective(qzgzStudy);
    }

    @Override
    public int deleteById(int id) {
        return qzgzStudyMapper.deleteByPrimaryKey(id);
    }
}
