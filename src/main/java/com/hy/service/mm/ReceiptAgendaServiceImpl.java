package com.hy.service.mm;

import com.hy.common.SecurityUtil;
import com.hy.dto.MmReceiptAgendaFetchDto;
import com.hy.dto.MmReceiptAgendaViewDto;
import com.hy.mapper.ms.MmReceiptAgendaMapper;
import com.hy.model.MmAgenda;
import com.hy.model.MmReceiptAgenda;
import com.hy.model.VMmReceiptAgenda;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            List<MmAgenda> agenda = agendaService.getAgendaByRid(rid);
            List<MmReceiptAgendaViewDto> agendaViewDtos = new ArrayList<>();
            for (MmAgenda anAgenda : agenda) {
                agendaViewDtos.add(new MmReceiptAgendaViewDto(anAgenda.getName(), anAgenda.getDate(),
                        false, anAgenda.getMid(), rid, anAgenda.getId(), 0, false));
            }
            return agendaViewDtos;
        }
    }

    @Override
    public boolean setReceiptAgenda(List<MmReceiptAgendaFetchDto> mmReceiptAgendaFetchDtos) {
        List<MmReceiptAgenda> mmReceiptAgenda =  DTOUtil.populateList(mmReceiptAgendaFetchDtos, MmReceiptAgenda.class);
        for (MmReceiptAgenda ra : mmReceiptAgenda){
            ra.setModifier(SecurityUtil.getLoginid());
        }
        return mmReceiptAgendaMapper.updateReceiptAgenda(mmReceiptAgenda) == mmReceiptAgenda.size();
    }

    @Override
    public boolean addReceiptAgenda(List<MmReceiptAgendaFetchDto> mmReceiptAgendaFetchDtos) {
        List<MmReceiptAgenda> mmReceiptAgenda = DTOUtil.populateList(mmReceiptAgendaFetchDtos, MmReceiptAgenda.class);
        for(MmReceiptAgenda ra : mmReceiptAgenda){
            ra.setModifier(SecurityUtil.getLoginid());
            ra.setDomain(SecurityUtil.getDepartmentId());
            ra.setCreater(SecurityUtil.getLoginid());
        }
        return mmReceiptAgendaMapper.insertReceiptAgenda(mmReceiptAgenda) == mmReceiptAgenda.size();
    }
}
