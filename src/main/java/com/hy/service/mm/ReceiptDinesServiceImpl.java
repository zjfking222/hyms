package com.hy.service.mm;

import com.hy.dto.MmReceiptDinesDto;
import com.hy.mapper.ms.MmReceiptDinesMapper;
import com.hy.model.MmReceiptDines;
import com.hy.utils.DTOUtil;
import com.hy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptDinesServiceImpl implements ReceiptDinesService {
    @Autowired
    private MmReceiptDinesMapper mmReceiptDinesMapper;
    @Override
    public List<MmReceiptDinesDto> getReceiptDines(int rid) {
        List<MmReceiptDines> rds = mmReceiptDinesMapper.selectReceiptDines(rid);
        List<MmReceiptDinesDto> rdds = DTOUtil.populateList(rds, MmReceiptDinesDto.class);
        for (int i = 0 ; i < rds.size(); i++){
            rdds.get(i).setDate(DateUtil.breviary(rds.get(i).getDate()));
        }
        return rdds;
    }
}
