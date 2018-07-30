package com.hy.service.mm;

import com.hy.dto.MmReceiptDinesDto;

import java.util.List;

public interface ReceiptDinesService {
    /**
     * 获取回执用餐列表
     *
     * @param rid 回执id
     * @return 用餐列表
     */
    List<MmReceiptDinesDto> getReceiptDines(int rid);
}
