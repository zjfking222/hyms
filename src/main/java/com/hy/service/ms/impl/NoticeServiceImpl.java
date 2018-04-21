package com.hy.service.ms.impl;

import com.hy.dto.NoticeDto;
import com.hy.dto.NoticeInfoDto;
import com.hy.mapper.ms.QzgzNoticeMapper;
import com.hy.model.QzgzNotice;
import com.hy.service.ms.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service (value = "Notice")
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private QzgzNoticeMapper noticeMapper;
    private final int GROUNDING=1;

    @Override
    public List<NoticeDto> getNotice(int state){
        return noticeMapper.selectNotice(GROUNDING);
    }
    public Integer insertNotice(NoticeInfoDto noticeInfoDto){
        QzgzNotice qzgzNoticeInfo =new QzgzNotice(
                noticeInfoDto.getTitle(),
                noticeInfoDto.getCreater(),
                noticeInfoDto.getCreated(),
                noticeInfoDto.getContent(),
                noticeInfoDto.getNotifiedPerson()
        );

        return noticeMapper.insertNotice(qzgzNoticeInfo);
    }
}
