package com.hy.mapper.ms;

import com.hy.model.MmReceiptAgenda;
import com.hy.model.VMmReceiptAgenda;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MmReceiptAgendaMapper {
    List<VMmReceiptAgenda> selectReceiptAgendaView(@Param("rid") int rid);
    Integer updateReceiptAgenda(List<MmReceiptAgenda> mmReceiptAgenda);
}
