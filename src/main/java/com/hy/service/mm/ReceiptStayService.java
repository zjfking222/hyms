package com.hy.service.mm;

import com.hy.dto.MmReceiptStayFetchDto;
import com.hy.dto.MmReceiptStayViewDto;

import java.util.List;


public interface ReceiptStayService {
    /**
     * 获取回执住宿列表
     *
     * @param rid 回执id
     * @return 住宿列表
     */
    List<MmReceiptStayViewDto> getReceiptStayView(int rid);
    boolean setReceiptStay(List<MmReceiptStayFetchDto> mmReceiptStayFetchDtos);
}
