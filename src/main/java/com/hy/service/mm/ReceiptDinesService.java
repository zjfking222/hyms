package com.hy.service.mm;

import com.hy.dto.MmReceiptDineFetchDto;
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
    /**
     * 批量更新ReceiptDines
     * 被ReceiptService引用
     */
    boolean setReceiptDines(List<MmReceiptDineFetchDto> mmReceiptDineFetchDtos);
    /**
     * 批量插入ReceiptDines
     * 被ReceiptService引用
     */
    boolean addReceiptDines(List<MmReceiptDineFetchDto> mmReceiptDineFetchDtos);
}
