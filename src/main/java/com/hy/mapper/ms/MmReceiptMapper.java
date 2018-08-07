package com.hy.mapper.ms;

import com.hy.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MmReceiptMapper {
    List<VMmMeetingReceipt> selectMeetingView(@Param("value") String value, @Param("sort") String sort,
                                              @Param("dir") String dir, @Param("state") String state);
    Integer selectMeetingViewTotal(@Param("value") String value, @Param("state") String state);

    List<VMmReceiptInfo> selectReceiptView(@Param("value") String value, @Param("sort") String sort,
                                           @Param("dir") String dir, @Param("mid") int mid);

    Integer selectReceiptViewTotal(@Param("value") String value, @Param("mid")int mid);

    Integer deleteReceipt(@Param("id") int id);

    MmReceipt selectReceiptById(@Param("id")int id);

    Integer updateReceipt(MmReceipt mmReceipt);

    Integer insertReceipt(List<MmReceipt> mmReceipts);
}
