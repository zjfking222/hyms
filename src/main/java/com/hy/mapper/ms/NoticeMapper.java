package com.hy.mapper.ms;

import com.hy.dto.NoticeDto;
import com.hy.dto.NoticeInfoDto;
import com.hy.model.Notice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeMapper {
    List<NoticeDto> selectNotice(@Param("state") Integer state);
    Integer insertNotice(Notice notice);
    void deleteNotice(int id);
    void updateNotice(Notice notice);
}
