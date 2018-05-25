package com.hy.service.qzgz.impl;

import com.hy.common.SecurityHelp;
import com.hy.dto.CanteenHistoryDto;
import com.hy.mapper.ms.QzgzCanteenHistoryMapper;
import com.hy.model.QzgzCanteenHistory;
import com.hy.model.QzgzCanteenHistoryListItem;
import com.hy.model.QzgzCanteenView;
import com.hy.service.qzgz.CanteenHistoryService;
import com.hy.service.qzgz.CanteenService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CanteenHistoryServiceImpl implements CanteenHistoryService{
    @Autowired
    private QzgzCanteenHistoryMapper qzgzCanteenHistoryMapper;
    @Autowired
    private CanteenService canteenService;

    @Override
    public boolean addTodaysCanteen(int canteenId ,int meal, int plusDay) {
        QzgzCanteenHistory canteenHistory = new QzgzCanteenHistory();
        canteenHistory.setCanteen_id(canteenId);
        canteenHistory.setCreater(SecurityHelp.getUserId());
        canteenHistory.setModifier(SecurityHelp.getUserId());
        canteenHistory.setMeal(meal);
        return qzgzCanteenHistoryMapper
                .insertCanteenHistory(canteenHistory, plusDay) == 1;
    }

    @Override
    public List<CanteenHistoryDto> getTodaysCanteen(int plusDay) {

        List<CanteenHistoryDto> canteenHistoryDtos = DTOUtil
                .populateList(qzgzCanteenHistoryMapper.selectCanteenHistory(plusDay),
                        CanteenHistoryDto.class);
        for (CanteenHistoryDto ch : canteenHistoryDtos)
        {
            ch.setCanteen(canteenService.getCanteenById(ch.getCanteen_id()));
        }
        return canteenHistoryDtos;
    }

    @Override
    public boolean removeTodaysCanteen(int canteenId, int meal, int plusDay) {
        return qzgzCanteenHistoryMapper
                .deleteCanteenHistory(canteenId, meal, plusDay) == 1;
    }

    @Override
    public List<QzgzCanteenView> getTodaysCanteenView() {
        return qzgzCanteenHistoryMapper.selectViewOfHistory();
    }

    @Override
    public List<QzgzCanteenHistoryListItem> getCanteenHistoryByDay(int plusDay) {
        return qzgzCanteenHistoryMapper.selectCanteenHistoryByDay(plusDay);
    }

}
