package com.hy.service.qzgz.impl;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.dto.QzgzNoticeDto;
import com.hy.mapper.ms.QzgzNoticeMapper;
import com.hy.model.QzgzNotice;
import com.hy.service.qzgz.NoticeService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "Notice")
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private QzgzNoticeMapper qzgzNoticeMapper;

    @Override
    public List<QzgzNoticeDto> getList(String filters, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<QzgzNotice> list = qzgzNoticeMapper.selectNotices(filters);
        return DTOUtil.populateList(list, QzgzNoticeDto.class);
    }

    @Override
    public List<QzgzNoticeDto> getEffecttList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<QzgzNotice> list = qzgzNoticeMapper.selectEffectNotices();
        return DTOUtil.populateList(list, QzgzNoticeDto.class);
    }

    @Override
    public QzgzNoticeDto getNotice(int id) {
        QzgzNotice qzgzNotice = qzgzNoticeMapper.selectByPrimaryKey(id);
        QzgzNoticeDto dto = new QzgzNoticeDto();
        if (qzgzNotice == null)
            return dto;
        return DTOUtil.populate(qzgzNotice, dto);
    }

    @Override
    public int getTotal(String filters) {
        return qzgzNoticeMapper.selectNoticesTotal(filters);
    }

    @Override
    public QzgzNoticeDto add(QzgzNoticeDto dto) {
        QzgzNotice qzgzNotice = DTOUtil.populate(dto, QzgzNotice.class);
        qzgzNotice.setCreater(SecurityUtil.getLoginid());
        qzgzNotice.setCreatername(SecurityUtil.getUserName());
        qzgzNotice.setModifier(SecurityUtil.getLoginid());
        qzgzNotice.setModifiername(SecurityUtil.getUserName());
        int id = qzgzNoticeMapper.insertSelective(qzgzNotice);
        return this.getNotice(qzgzNotice.getId());
    }

    @Override
    public int update(QzgzNoticeDto dto) {
        QzgzNotice qzgzNotice = DTOUtil.populate(dto, QzgzNotice.class);
        qzgzNotice.setModifier(SecurityUtil.getLoginid());
        qzgzNotice.setModifiername(SecurityUtil.getUserName());
        return qzgzNoticeMapper.updateByPrimaryKeySelective(qzgzNotice);
    }

    @Override
    public int deleteById(int id) {
        return qzgzNoticeMapper.deleteByPrimaryKey(id);
    }
}

