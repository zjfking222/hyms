package com.hy.service.mm;

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
import java.util.TimeZone;

@Service
public class AgendaServiceImpl implements AgendaService {

    @Autowired
    private MmAgendaMapper mmAgendaMapper;

    private SimpleDateFormat sdf;

    private Date tempDate;

    private MmAgenda mmAgenda;

    public AgendaServiceImpl(){
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
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
    public List<MmAgendaDto> getAgenda(int mid) {
        //TODO
        return null;
    }

    @Override
    public int getAgendaTotal(int mid) {
        return mmAgendaMapper.selectMmAgendaTotal(mid);
    }
}
