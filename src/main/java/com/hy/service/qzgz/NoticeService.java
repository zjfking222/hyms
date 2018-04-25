package com.hy.service.qzgz;

import com.hy.dto.NoticeDto;
import com.hy.dto.NoticeInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeService {
    List<NoticeDto> getNotice(@Param("state") int state);
    Integer insertNotice(NoticeInfoDto noticeInfoDto);
}
