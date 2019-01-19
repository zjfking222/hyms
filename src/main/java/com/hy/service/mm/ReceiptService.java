package com.hy.service.mm;

import com.hy.dto.*;

import java.util.HashMap;
import java.util.List;

public interface ReceiptService {

    List<MmMeetingReceiptViewDto> getMeetingView(String filters, int pageNum, int pageSize, String value, String sort, String dir,
                                                 String state);
    Integer getMeetingViewTotal(String filters, String value, String state);

    /**
     * 回执管理获取列表接口
     */
    List<MmReceiptInfoViewDto> getReceiptView(String filters, int pageNum, int pageSize, String value, String sort, String dir, int mid);


    Integer getReceiptViewTotal(String filters, String value, int mid);

    /**
     * 会议回执获取列表接口（通过btid控制）
     */
    List<MmReceiptInfoViewDto> getReceiptViewInBtid(String filters, int pageNum, int pageSize, String value, String sort, String dir,
                                                    int mid);

    Integer getReceiptViewInBtidTotal(String filters, String value, int mid);

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

    boolean setReceiptState(List<MmReceiptDto> mmReceiptDtos);
}
