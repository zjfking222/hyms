package com.hy.controller.mm;

import com.hy.common.ResultObj;
import com.hy.dto.MmAgendaDto;
import com.hy.enums.ResultCode;
import com.hy.service.mm.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/mm")
public class AgendaController {
    @Autowired
    private AgendaService agendaService;

    @PostMapping("/agenda/get")
    public ResultObj getAgenda(int page, int pageSize,int mid, @RequestParam(required = false) String sort,
                               @RequestParam(required = false) String dir){
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", agendaService.getAgenda(page, pageSize, mid, sort, dir));
        map.put("total", agendaService.getAgendaTotal(mid));
        return ResultObj.success(map);
    }

    @PostMapping("/agenda/set")
    public ResultObj setAgenda(MmAgendaDto mmAgendaDto){
        return agendaService.setAgenda(mmAgendaDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @PostMapping("/agenda/del")
    public ResultObj delAgenda(int id){
        return agendaService.delAgenda(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }

    @PostMapping("/agenda/add")
    public ResultObj addAgenda(MmAgendaDto mmAgendaDto){
        System.out.println(mmAgendaDto.getDate());
        System.out.println(mmAgendaDto.getMid());
        System.out.println(mmAgendaDto.getId());
        return agendaService.addAgenda(mmAgendaDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }
}
