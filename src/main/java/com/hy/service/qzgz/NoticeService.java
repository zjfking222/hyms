package com.hy.service.qzgz;

import com.hy.dto.NoticeDto;
import com.hy.dto.NoticeInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeService {
    List<NoticeDto> getNotice(@Param("state") int state);
    Integer insertNotice(NoticeInfoDto noticeInfoDto);
    boolean deleteNotice(@Param("id") int id);
    boolean updateNotice(NoticeInfoDto noticeInfoDto);
    List<NoticeDto> selectByCreater(@Param("creater") int creater);
    List<NoticeDto> totalPage();
}

