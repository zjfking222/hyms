package com.hy.service.mm;

import com.hy.dto.MmReceiptStayViewDto;
import com.hy.mapper.ms.MmReceiptStayMapper;
import com.hy.model.VMmReceiptStay;
import com.hy.utils.DTOUtil;
import com.hy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptStayServiceImpl implements ReceiptStayService {
    @Autowired
    private MmReceiptStayMapper mmReceiptStayMapper;
    @Override
    public List<MmReceiptStayViewDto> getReceiptStayView(int rid) {
        List<VMmReceiptStay> vrs = mmReceiptStayMapper.selectReceiptStayView(rid);
        List<MmReceiptStayViewDto> rsdto = DTOUtil.populateList(vrs, MmReceiptStayViewDto.class);
        for(int i = 0 ;i < vrs.size() ; i++){
            rsdto.get(i).setDate(DateUtil.breviary(vrs.get(i).getDate()));
        }
        return rsdto;
    }
}
