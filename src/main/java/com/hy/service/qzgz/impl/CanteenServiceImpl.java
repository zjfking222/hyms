package com.hy.service.qzgz.impl;

import com.hy.common.SecurityHelp;
import com.hy.dto.CanteenDto;
import com.hy.dto.CanteenWithTotalPageDto;
import com.hy.mapper.ms.QzgzCanteenMapper;
import com.hy.model.QzgzCanteen;
import com.hy.service.qzgz.CanteenService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CanteenServiceImpl implements CanteenService{

    @Autowired
    private QzgzCanteenMapper canteenMapper;


    @Override
    public CanteenWithTotalPageDto getCanteen(int page, int number, String state) {

        //用户输入的页数转化为startRow
        int startRow = (page - 1) * number;
        List<CanteenDto> canteenDtos = DTOUtil.populateList(canteenMapper.selectCanteen(state, startRow, number),
                new ArrayList<CanteenDto>(),CanteenDto.class);

        return canteenDtos == null ?
                null: new CanteenWithTotalPageDto(canteenDtos, getTotalPageOfCanteen(number, state));
    }

    @Override
    public Integer getTotalPageOfCanteen(int number, String state) {
        int countOfCanteen = canteenMapper.selectCountOfCanteen(state);
        double totalPageD = (double)countOfCanteen/(double)number;
        return (int)Math.ceil(totalPageD);
    }

    @Override
    public boolean addCanteen(String name, double price) {
        QzgzCanteen qzgzCanteen = new QzgzCanteen();
        qzgzCanteen.setName(name);
        qzgzCanteen.setPrice(price);
        qzgzCanteen.setModifier(SecurityHelp.getUserId());
        qzgzCanteen.setCreater(SecurityHelp.getUserId());
        return canteenMapper.insertCanteen(qzgzCanteen) == 1;
    }

    @Override
    public boolean updateCanteen(String name, double price, int id) {
        QzgzCanteen qzgzCanteen = new QzgzCanteen();
        qzgzCanteen.setId(id);
        qzgzCanteen.setPrice(price);
        qzgzCanteen.setName(name);
        qzgzCanteen.setModifier(SecurityHelp.getUserId());
        return canteenMapper.updateCanteenById(qzgzCanteen) == 1;
    }

    @Override
    public boolean updateCanteenState(String state, int id) {
        QzgzCanteen qzgzCanteen = new QzgzCanteen();
        qzgzCanteen.setId(id);
        qzgzCanteen.setState(state);
        qzgzCanteen.setModifier(SecurityHelp.getUserId());
        return canteenMapper.updateCanteenState(qzgzCanteen) == 1;
    }

    @Override
    public List<CanteenDto> getCanteenBySearchName(String name) {
        return DTOUtil.populateList(canteenMapper.selectCanteenByName(name),
                new ArrayList<CanteenDto>(),CanteenDto.class);
    }

}
