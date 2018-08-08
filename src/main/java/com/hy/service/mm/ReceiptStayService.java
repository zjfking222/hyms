package com.hy.service.mm;

import com.hy.dto.MmReceiptStayFetchDto;
import com.hy.dto.MmReceiptStayViewDto;

import java.util.List;


public interface ReceiptStayService {
    /**
     * 获取回执住宿列表（用于回执管理）
     *
     * @param rid 回执id
     * @return 住宿列表
     */
    List<MmReceiptStayViewDto> getReceiptStayView(int rid);
    /**
     * 批量更新ReceiptStay
     * 被ReceiptService引用
     */
    boolean setReceiptStay(List<MmReceiptStayFetchDto> mmReceiptStayFetchDtos);
    /**
     * 批量插入ReceiptStay
     * 被ReceiptService引用
     */
    boolean addReceiptStay(List<MmReceiptStayFetchDto> mmReceiptStayFetchDtos);
}
