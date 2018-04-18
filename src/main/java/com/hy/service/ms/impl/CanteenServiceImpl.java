package com.hy.service.ms.impl;

import com.hy.dto.CanteenDto;
import com.hy.dto.CanteenWithTotalPageDto;
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
    public CanteenWithTotalPageDto getCanteen(int page, int number) {

        //用户输入的页数转化为startRow
        int startRow = (page - 1) * number;
        List<CanteenDto> canteenDtos = canteenMapper.selectCanteen(GROUNDING, startRow, number);
        if(canteenDtos == null)
        {
            return null;
        }
        else
        {
            return new CanteenWithTotalPageDto(canteenDtos, getTotalPageOfCanteen(number));
        }
    }

    @Override
    public Integer getTotalPageOfCanteen(int number) {
        int countOfCanteen = canteenMapper.selectCountOfCanteen(GROUNDING);
        double totalPageD = (double)countOfCanteen/(double)number;
        return (int)Math.ceil(totalPageD);
    }
}
