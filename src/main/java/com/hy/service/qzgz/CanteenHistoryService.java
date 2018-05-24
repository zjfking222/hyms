package com.hy.service.qzgz;

import com.hy.dto.CanteenDto;
import com.hy.dto.CanteenHistoryDto;
import com.hy.model.QzgzCanteenHistoryListItem;
import com.hy.model.QzgzCanteenView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CanteenHistoryService {
    boolean addTodaysCanteen(int canteenId, int meal, int plusDay);
    List<CanteenHistoryDto> getTodaysCanteen( int plusDay);
    boolean removeTodaysCanteen(int canteenId, int meal, int plusDay);
    List<QzgzCanteenView> getTodaysCanteenView();
    List<QzgzCanteenHistoryListItem> getCanteenHistoryByDay(int plusDay);
}
