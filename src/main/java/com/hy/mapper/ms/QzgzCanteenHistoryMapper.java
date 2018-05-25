package com.hy.mapper.ms;

import com.hy.model.QzgzCanteenHistory;
import com.hy.model.QzgzCanteenHistoryListItem;
import com.hy.model.QzgzCanteenView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzCanteenHistoryMapper {
    int insertCanteenHistory (@Param("canteen") QzgzCanteenHistory qzgzCanteenHistory, @Param("plusDay")int plusDay);
    List<QzgzCanteenHistory> selectCanteenHistory(@Param("plusDay")int plusDay);
    int deleteCanteenHistory(@Param("canteen_id") int canteen_id, @Param("meal") int meal,@Param("plusDay")int plusDay);
    List<QzgzCanteenView> selectViewOfHistory();
    List<QzgzCanteenHistoryListItem> selectCanteenHistoryByDay(@Param("plusDay")int plusDay);
}
