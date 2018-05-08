package com.hy.mapper.ms;

import com.hy.model.QzgzCanteen;
import com.hy.model.QzgzCanteenHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzCanteenHistoryMapper {
    int insertCanteenHistory (QzgzCanteenHistory qzgzCanteenHistory);
    List<QzgzCanteen> selectCanteenHistory();
    int deleteCanteenHistory(int canteen_id);
}
