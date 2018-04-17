package com.hy.service.ms.impl;

import com.hy.dto.CanteenDto;
import com.hy.mapper.ms.CanteenMapper;
import com.hy.service.ms.CanteenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanteenServiceImpl implements CanteenService{

    @Autowired
    private CanteenMapper canteenMapper;
    //GROUNDING = 1 为已上架的菜
    private final int GROUNDING = 1;

    @Override
    public List<CanteenDto> getCanteen() {
        return canteenMapper.selectCanteen(GROUNDING);
    }
}
