package com.hy.mapper.ms;

import com.hy.model.QzgzCanteen;
import com.hy.model.QzgzCanteenHistory;
import com.hy.model.QzgzCanteenHistoryListItem;
import com.hy.model.QzgzCanteenView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Repository
public interface QzgzCanteenHistoryMapper {
    int insertCanteenHistory (QzgzCanteenHistory qzgzCanteenHistory);
    List<QzgzCanteenHistory> selectCanteenHistory();
    int deleteCanteenHistory(@Param("canteen_id") int canteen_id, @Param("meal") int meal);
    List<QzgzCanteenView> selectViewOfHistory();
    List<QzgzCanteenHistoryListItem> selectCanteenHistoryByDay(@Param("plusDay")int plusDay);
}
