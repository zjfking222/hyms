package com.hy.service.qzgz.impl;

import com.hy.common.SecurityHelp;
import com.hy.dto.CanteenDto;
import com.hy.mapper.ms.QzgzCanteenHistoryMapper;
import com.hy.model.QzgzCanteenHistory;
import com.hy.service.qzgz.CanteenHistoryService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CanteenHistoryServiceImpl implements CanteenHistoryService{
    @Autowired
    private QzgzCanteenHistoryMapper qzgzCanteenHistoryMapper;

    @Override
    public boolean addTodaysCanteen(int canteenId) {
        QzgzCanteenHistory canteenHistory = new QzgzCanteenHistory();
        canteenHistory.setCanteen_id(canteenId);
        canteenHistory.setCreater(SecurityHelp.getUserId());
        canteenHistory.setModifier(SecurityHelp.getUserId());
        return qzgzCanteenHistoryMapper
                .insertCanteenHistory(canteenHistory) == 1;
    }

    @Override
    public List<CanteenDto> getTodaysCanteen() {
        return  DTOUtil.populateList(qzgzCanteenHistoryMapper.selectCanteenHistory(),
                        new ArrayList<CanteenDto>(),CanteenDto.class);
    }

    @Override
    public boolean removeTodaysCanteen(int canteenId) {
        return qzgzCanteenHistoryMapper
                .deleteCanteenHistory(canteenId) == 1;
    }

}
