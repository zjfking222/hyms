package com.hy.mapper.ms;

import com.hy.dto.NoticeDto;
import com.hy.model.QzgzNotice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzNoticeMapper {
    List<NoticeDto> selectNotice(@Param("state") Integer state);
    Integer insertNotice(QzgzNotice qzgzNotice);
    boolean deleteNotice(@Param("id") int id);
    boolean updateNotice(QzgzNotice qzgzNotice);
    List<NoticeDto> selectByCreater(@Param("creater") int creater);
}

