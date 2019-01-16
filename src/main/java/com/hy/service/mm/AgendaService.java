package com.hy.service.mm;

import com.hy.dto.MmAgendaDto;
import com.hy.model.MmAgenda;

import java.util.List;

public interface AgendaService {
    boolean addAgenda(MmAgendaDto mmAgendaDto);
    boolean setAgenda(MmAgendaDto mmAgendaDto);
    boolean delAgenda(int id);
    List<MmAgendaDto> getAgenda(String filters, int pageNum, int pageSize, int mid, String sort, String dir);
    int getAgendaTotal(String filters, int mid);
    //通过回执id来获取议程列表
    List<MmAgenda> getAgendaByRid(int rid);
}
