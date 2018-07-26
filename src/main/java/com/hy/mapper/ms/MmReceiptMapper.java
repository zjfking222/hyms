package com.hy.mapper.ms;

import com.hy.model.VMmMeetingReceipt;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MmReceiptMapper {
    List<VMmMeetingReceipt> selectMeetingView(@Param("value") String value, @Param("sort") String sort,
                                              @Param("dir") String dir);
    Integer selectMeetingViewTotal(@Param("value") String value);
}
