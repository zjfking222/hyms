package com.hy.service.mm;

import com.hy.dto.MmMeetingReceiptViewDto;
import com.hy.dto.MmReceiptFetchDto;
import com.hy.dto.MmReceiptInfoViewDto;
import com.hy.dto.MmReceiptNewDto;

import java.util.HashMap;
import java.util.List;

public interface ReceiptService {

    List<MmMeetingReceiptViewDto> getMeetingView(int pageNum, int pageSize, String value, String sort, String dir,
                                                 String state);
    Integer getMeetingViewTotal(String value, String state);

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

    boolean setReceipt(MmReceiptFetchDto mmReceiptFetchDto);

    boolean addReceipt(List<MmReceiptNewDto> mmReceiptNewDtos);
}
