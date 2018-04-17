package com.hy.service.ms.impl;

import com.hy.mapper.ms.CanteenMapper;
import com.hy.model.Canteen;
import com.hy.service.ms.CanteenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanteenServiceImpl implements CanteenService{

    @Autowired
    private CanteenMapper canteenMapper;
    private final int GROUNDING = 1;

    @Override
    public List<Canteen> getCanteen() {
        return canteenMapper.selectCanteen(GROUNDING);
    }
}
