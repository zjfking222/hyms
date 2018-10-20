package com.hy.service.mm;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityHelp;
import com.hy.dto.MmAgendaDto;
import com.hy.mapper.ms.MmAgendaMapper;
import com.hy.model.MmAgenda;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AgendaServiceImpl implements AgendaService {

    @Autowired
    private MmAgendaMapper mmAgendaMapper;

    private SimpleDateFormat sdf;

    private Date tempDate;

    private MmAgenda mmAgenda;

    public AgendaServiceImpl(){
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public boolean addAgenda(MmAgendaDto mmAgendaDto) {
        try{
            tempDate = sdf.parse(mmAgendaDto.getDate());
            mmAgenda = DTOUtil.populate(mmAgendaDto,MmAgenda.class);
            mmAgenda.setCreater(SecurityHelp.getUserId());
            mmAgenda.setModifier(SecurityHelp.getUserId());
            mmAgenda.setDomain(SecurityHelp.getDepartmentId());
            mmAgenda.setDate(tempDate);
        }catch (ParseException e){
            return false;
        }
        return mmAgendaMapper.insertMmAgenda(mmAgenda) == 1;
    }

    @Override
    public boolean setAgenda(MmAgendaDto mmAgendaDto) {
        try{
            tempDate = sdf.parse(mmAgendaDto.getDate());
            mmAgenda = DTOUtil.populate(mmAgendaDto,MmAgenda.class);
            mmAgenda.setModifier(SecurityHelp.getUserId());
            mmAgenda.setDate(tempDate);
        }catch (ParseException e){
            return false;
        }
        return mmAgendaMapper.updateMmAgenda(mmAgenda) == 1;
    }

    @Override
    public boolean delAgenda(int id) {
        return mmAgendaMapper.deleteMmAgenda(id) == 1;
    }

    @Override
    public List<MmAgendaDto> getAgenda(int pageNum, int pageSize, int mid, String sort, String dir) {
        PageHelper.startPage(pageNum, pageSize);
        List<MmAgenda> agenda = mmAgendaMapper.selectMmAgenda(mid, sort, dir);
        List<MmAgendaDto> agendaDtos = DTOUtil.populateList(agenda, MmAgendaDto.class);
        for(int i = 0 ; i < agenda.size() ; i++){
            agendaDtos.get(i).setDate(sdf.format(agenda.get(i).getDate()));
        }
        return agendaDtos;
    }

    @Override
    public int getAgendaTotal(int mid) {
        return mmAgendaMapper.selectMmAgendaTotal(mid);
    }

    @Override
    public List<MmAgenda> getAgendaByRid(int rid) {
        return mmAgendaMapper.selectMmAgendaByRid(rid);
    }
}
