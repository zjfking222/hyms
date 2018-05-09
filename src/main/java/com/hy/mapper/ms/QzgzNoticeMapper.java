package com.hy.mapper.ms;

import com.hy.model.QzgzNotice;
import com.hy.model.QzgzStudy;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzNoticeMapper extends BaseMapper<Integer,QzgzNotice> {
    List<QzgzNotice> selectNotices();
    List<QzgzNotice> selectEffectNotices();
    int  selectNoticesTotal();
}