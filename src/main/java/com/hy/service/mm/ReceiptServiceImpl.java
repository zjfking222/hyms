package com.hy.service.mm;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityHelp;
import com.hy.dto.MmMeetingReceiptViewDto;
import com.hy.dto.MmReceiptDto;
import com.hy.dto.MmReceiptFetchDto;
import com.hy.dto.MmReceiptInfoViewDto;
import com.hy.mapper.ms.MmReceiptMapper;
import com.hy.model.MmReceipt;
import com.hy.model.VMmMeetingReceipt;
import com.hy.model.VMmReceiptInfo;
import com.hy.service.crm.CustomersService;
import com.hy.utils.DTOUtil;
import com.hy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ReceiptServiceImpl implements ReceiptService {

    @Autowired
    private MmReceiptMapper mmReceiptMapper;

    @Autowired
    private ReceiptAgendaService agendaService;

    @Autowired
    private ReceiptDinesService dinesService;

    @Autowired
    private ReceiptStayService stayService;

    @Autowired
    private CustomersService customersService;

    @Override
    public List<MmMeetingReceiptViewDto> getMeetingView(int pageNum, int pageSize, String value, String sort, String dir) {
        PageHelper.startPage(pageNum, pageSize);
        List<VMmMeetingReceipt> vMmMeetingReceipts = mmReceiptMapper.selectMeetingView(value, sort, dir);
        List<MmMeetingReceiptViewDto> mmMeetingReceiptViewDtos = DTOUtil.populateList(vMmMeetingReceipts, MmMeetingReceiptViewDto.class);

        for (int i = 0 ; i < vMmMeetingReceipts.size() ; i++){
            VMmMeetingReceipt mr = vMmMeetingReceipts.get(i);
            MmMeetingReceiptViewDto md = mmMeetingReceiptViewDtos.get(i);
            md.setState(DateUtil.getState(mr.getBegindate(),mr.getEnddate()));

            //过滤空值
            if(mr.getBegindate() != null){
                md.setBegindate(DateUtil.translate(mr.getBegindate()));
            }
            if(mr.getEnddate() != null){
                md.setEnddate(DateUtil.translate(mr.getEnddate()));
            }
            if(mr.getDeadline() != null){
                md.setDeadline(DateUtil.translate(mr.getDeadline()));
            }
        }
        return mmMeetingReceiptViewDtos;
    }

    @Override
    public Integer getMeetingViewTotal(String value){
        return mmReceiptMapper.selectMeetingViewTotal(value);
    }


    @Override
    public List<MmReceiptInfoViewDto> getReceiptView(int pageNum, int pageSize, String value, String sort, String dir, int mid) {
        PageHelper.startPage(pageNum, pageSize);
        List<VMmReceiptInfo> vMmReceiptInfos = mmReceiptMapper.selectReceiptView(value, sort, dir, mid);

        List<MmReceiptInfoViewDto> mmReceiptInfoViewDtos = DTOUtil.populateList(vMmReceiptInfos,MmReceiptInfoViewDto.class);
        for (int i = 0 ; i < vMmReceiptInfos.size() ; i++){
            if(vMmReceiptInfos.get(i).getSex()){
                mmReceiptInfoViewDtos.get(i).setSex("男");
            }
            else {
                mmReceiptInfoViewDtos.get(i).setSex("女");
            }
        }

        return mmReceiptInfoViewDtos;
    }

    @Override
    public Integer getReceiptViewTotal(String value , int mid) {
        return mmReceiptMapper.selectReceiptViewTotal(value, mid);
    }

    @Override
    public HashMap<String, Object> getReceiptDetail(int rid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("agenda",agendaService.getReceiptAgendaView(rid));
        map.put("dines",dinesService.getReceiptDines(rid));
        map.put("stay",stayService.getReceiptStayView(rid));
        MmReceipt mmReceipt = mmReceiptMapper.selectReceiptById(rid);
        MmReceiptDto mmReceiptDto = DTOUtil.populate(mmReceipt,MmReceiptDto.class);
        //时间格式转化
        if(mmReceipt.getArrivaldate() != null)
        {
            mmReceiptDto.setArrivaldate(DateUtil.translate(mmReceipt.getArrivaldate()));
        }
        if(mmReceipt.getDeparturedate() != null)
        {
            mmReceiptDto.setDeparturedate(DateUtil.translate(mmReceipt.getDeparturedate()));
        }
        //设置用户信息
        mmReceiptDto.setCustomers(customersService.getCrmCustomerById(mmReceipt.getCid()));
        map.put("receipt",mmReceiptDto);
        return map;
    }

    @Override
    public boolean delReceipt(int id) {
        return mmReceiptMapper.deleteReceipt(id) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setReceipt(MmReceiptFetchDto mmReceiptFetchDto) {
        try {
            //更新receipt
            MmReceipt mmReceipt = DTOUtil.populate(mmReceiptFetchDto.getReceipt(),MmReceipt.class);
            //判断空值
            if(mmReceiptFetchDto.getReceipt().getArrivaldate() != null){
                mmReceipt.setArrivaldate(DateUtil.translate(mmReceiptFetchDto.getReceipt().getArrivaldate()));
            }
            if(mmReceiptFetchDto.getReceipt().getDeparturedate() != null){
                mmReceipt.setDeparturedate(DateUtil.translate(mmReceiptFetchDto.getReceipt().getDeparturedate()));
            }
            mmReceipt.setModifier(SecurityHelp.getUserId());
            mmReceiptMapper.updateReceipt(mmReceipt);

            //批量更新或插入 receiptAgenda
            if(mmReceiptFetchDto.getAgenda().size() > 0){
                if (mmReceiptFetchDto.getAgenda().get(0).getId() != 0) {
                    agendaService.setReceiptAgenda(mmReceiptFetchDto.getAgenda());
                } else {
                    agendaService.addReceiptAgenda(mmReceiptFetchDto.getAgenda());
                }
            }

            //批量更新或插入 receiptDines
            if(mmReceiptFetchDto.getDines().size() > 0){
                if (mmReceiptFetchDto.getDines().get(0).getId() != 0){
                    dinesService.setReceiptDines(mmReceiptFetchDto.getDines());
                } else {
                    dinesService.addReceiptDines(mmReceiptFetchDto.getDines());
                }
            }

            //批量更新或插入 receiptStay
            if(mmReceiptFetchDto.getStay().size() > 0){
                if(mmReceiptFetchDto.getStay().get(0).getId() != 0){
                    stayService.setReceiptStay(mmReceiptFetchDto.getStay());
                } else {
                    stayService.addReceiptStay(mmReceiptFetchDto.getStay());
                }
            }

            return true;
        }catch (Exception e){
            throw e;
        }
    }
}
