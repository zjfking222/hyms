package com.hy.service.mm;

import com.hy.dto.MmReceiptAgendaViewDto;
import com.hy.mapper.ms.MmReceiptAgendaMapper;
import com.hy.model.VMmReceiptAgenda;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptAgendaServiceImpl implements ReceiptAgendaService {

    @Autowired
    private MmReceiptAgendaMapper mmReceiptAgendaMapper;

    @Autowired
    private AgendaService agendaService;

    @Override
    public List<MmReceiptAgendaViewDto> getReceiptAgendaView(int rid) {
        List<VMmReceiptAgenda> vMmReceiptAgenda = mmReceiptAgendaMapper.selectReceiptAgendaView(rid);
        if(vMmReceiptAgenda.size() != 0){
            return DTOUtil.populateList(vMmReceiptAgenda, MmReceiptAgendaViewDto.class);
        }
        else {
            return DTOUtil.populateList(agendaService.getAgendaByRid(rid),MmReceiptAgendaViewDto.class);
        }
    }
}
