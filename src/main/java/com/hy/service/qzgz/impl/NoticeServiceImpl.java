package com.hy.service.qzgz.impl;

import com.hy.common.SecurityHelp;
import com.hy.dto.NoticeDto;
import com.hy.dto.NoticeInfoDto;
import com.hy.mapper.ms.QzgzNoticeMapper;
import com.hy.model.QzgzNotice;
import com.hy.service.qzgz.NoticeService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service (value = "Notice")
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private QzgzNoticeMapper noticeMapper;
    private final int GROUNDING=1;

    @Override
    public List<NoticeDto> getNotice(int state,int pageNum){

        return noticeMapper.selectNotice(GROUNDING , pageNum);
    }
    public Integer insertNotice(NoticeInfoDto noticeInfoDto){
        QzgzNotice qzgzNoticeInfo =new QzgzNotice(
                noticeInfoDto.getId(),
                noticeInfoDto.getTitle(),
                noticeInfoDto.getCreater(),
                noticeInfoDto.getCreated(),
                noticeInfoDto.getContent(),
                noticeInfoDto.getNodifiedPerson(),
                noticeInfoDto.getModifier(),
                noticeInfoDto.getModifiorname(),
                noticeInfoDto.getCreator()
        );
        qzgzNoticeInfo.setCreatorname(SecurityHelp.getUserName());
        qzgzNoticeInfo.setModifiorname(SecurityHelp.getUserName());
        return noticeMapper.insertNotice(qzgzNoticeInfo);
    }
    @Override
    public boolean deleteNotice(int id){
        return noticeMapper.deleteNotice(id);
    }
    @Override
    public boolean updateNotice(NoticeInfoDto noticeInfoDto){
        QzgzNotice qzgzNoticeInfo =new QzgzNotice(
                noticeInfoDto.getId(),
                noticeInfoDto.getTitle(),
                noticeInfoDto.getCreater(),
                noticeInfoDto.getCreated(),
                noticeInfoDto.getContent(),
                noticeInfoDto.getNodifiedPerson(),
                noticeInfoDto.getModifier(),
                noticeInfoDto.getModifiorname(),
                noticeInfoDto.getCreator()
        );
        qzgzNoticeInfo.setModifiorname(SecurityHelp.getUserName());
        return noticeMapper.updateNotice(qzgzNoticeInfo);
    }
    @Override
    public List<NoticeDto> selectByCreater(int creater){

        return noticeMapper.selectByCreater(creater);
    }
    public int totalPage(){

        return noticeMapper.totalPage();
    }
}

