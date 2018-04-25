package com.hy.service.qzgz.impl;

import com.hy.dto.NoticeDto;
import com.hy.dto.NoticeInfoDto;
import com.hy.mapper.ms.NoticeMapper;
import com.hy.model.Notice;
import com.hy.service.qzgz.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service (value = "Notice")
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    private final int GROUNDING=1;

    @Override
    public List<NoticeDto> getNotice(int state){
        return noticeMapper.selectNotice(GROUNDING);
    }
    public Integer insertNotice(NoticeInfoDto noticeInfoDto){
        Notice noticeInfo =new Notice(
                noticeInfoDto.getTitle(),
                noticeInfoDto.getCreater(),
                noticeInfoDto.getCreated(),
                noticeInfoDto.getContent(),
                noticeInfoDto.getNotifiedPerson()
        );

        return noticeMapper.insertNotice(noticeInfo);
    }
}
