package com.hy.service.mm;

import com.hy.common.SecurityHelp;
import com.hy.dto.MmReceiptAgendaFetchDto;
import com.hy.dto.MmReceiptAgendaViewDto;
import com.hy.mapper.ms.MmReceiptAgendaMapper;
import com.hy.model.MmReceiptAgenda;
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

    @Override
    public boolean setReceiptAgenda(List<MmReceiptAgendaFetchDto> mmReceiptAgendaFetchDtos) {
        List<MmReceiptAgenda> mmReceiptAgenda =  DTOUtil.populateList(mmReceiptAgendaFetchDtos, MmReceiptAgenda.class);
        for (MmReceiptAgenda ra : mmReceiptAgenda){
            ra.setModifier(SecurityHelp.getUserId());
        }
        return mmReceiptAgendaMapper.updateReceiptAgenda(mmReceiptAgenda) == mmReceiptAgenda.size();
    }
}
