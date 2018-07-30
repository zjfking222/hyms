package com.hy.service.mm;

import com.hy.dto.MmReceiptAgendaViewDto;
import com.hy.mapper.ms.MmReceiptAgendaMapper;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptAgendaServiceImpl implements ReceiptAgendaService {

    @Autowired
    private MmReceiptAgendaMapper mmReceiptAgendaMapper;

    @Override
    public List<MmReceiptAgendaViewDto> getReceiptAgendaView(int rid) {
        return DTOUtil.populateList(mmReceiptAgendaMapper.selectReceiptAgendaView(rid), MmReceiptAgendaViewDto.class);
    }
}
