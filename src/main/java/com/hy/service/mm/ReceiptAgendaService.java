package com.hy.service.mm;

import com.hy.dto.MmReceiptAgendaFetchDto;
import com.hy.dto.MmReceiptAgendaViewDto;

import java.util.List;

public interface ReceiptAgendaService {
    /**
     * 获取回执议程列表
     *
     * @param rid 回执id
     * @return 议程列表
     */
    List<MmReceiptAgendaViewDto> getReceiptAgendaView(int rid);
    /**
     * 批量更新ReceiptAgenda
     * 被ReceiptService引用
     */
    boolean setReceiptAgenda(List<MmReceiptAgendaFetchDto> mmReceiptAgendaFetchDtos);
    /**
     * 批量插入ReceiptAgenda
     * 被ReceiptService引用
     */
    boolean addReceiptAgenda(List<MmReceiptAgendaFetchDto> mmReceiptAgendaFetchDtos);
}
