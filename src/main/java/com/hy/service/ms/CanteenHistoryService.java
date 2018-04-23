package com.hy.service.ms;

import com.hy.dto.CanteenDto;

import java.util.List;

public interface CanteenHistoryService {
    boolean addTodaysCanteen(int canteenId);
    List<CanteenDto> getTodaysCanteen();
    boolean removeTodaysCanteen(int canteenId);
}
