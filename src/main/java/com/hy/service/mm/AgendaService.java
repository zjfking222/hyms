package com.hy.service.mm;

import com.hy.dto.MmAgendaDto;

import java.util.List;

public interface AgendaService {
    boolean addAgenda(MmAgendaDto mmAgendaDto);
    boolean setAgenda(MmAgendaDto mmAgendaDto);
    boolean delAgenda(int id);
    List<MmAgendaDto> getAgenda(int mid);
    int getAgendaTotal(int mid);
}
