package com.hy.service.mm;

import com.hy.dto.MmMeetingReceiptViewDto;

import java.util.List;

public interface ReceiptService {
    List<MmMeetingReceiptViewDto> getMeetingView(int pageNum, int pageSize, String value, String sort, String dir);
    Integer getMeetingViewTotal(String value);
}
