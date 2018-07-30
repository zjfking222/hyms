package com.hy.service.mm;

import com.hy.dto.MmMeetingReceiptViewDto;
import com.hy.dto.MmReceiptInfoViewDto;

import java.util.HashMap;
import java.util.List;

public interface ReceiptService {

    List<MmMeetingReceiptViewDto> getMeetingView(int pageNum, int pageSize, String value, String sort, String dir);
    Integer getMeetingViewTotal(String value);

    List<MmReceiptInfoViewDto> getReceiptView(int pageNum, int pageSize, String value, String sort, String dir, int mid);
    Integer getReceiptViewTotal(String value, int mid);

    /**
     * 获取回执详情列表
     *
     * @param rid 回执id
     * @return 包含回执用餐列表，回执住宿列表，回执议程列表的详情列表
     */
    HashMap<String,Object> getReceiptDetail(int rid);

    boolean delReceipt(int id);

}
