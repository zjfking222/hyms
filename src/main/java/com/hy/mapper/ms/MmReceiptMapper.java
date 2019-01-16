package com.hy.mapper.ms;

import com.hy.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MmReceiptMapper {
    List<VMmMeetingReceipt> selectMeetingView(@Param("filters")String filters, @Param("value") String value, @Param("sort") String sort,
                                              @Param("dir") String dir, @Param("state") String state);
    Integer selectMeetingViewTotal(@Param("filters")String filters, @Param("value") String value, @Param("state") String state);

    List<VMmReceiptInfo> selectReceiptView(@Param("filters")String filters, @Param("value") String value, @Param("sort") String sort,
                                           @Param("dir") String dir, @Param("mid") int mid);

    Integer selectReceiptViewTotal(@Param("filters")String filters, @Param("value") String value, @Param("mid")int mid);

    List<VMmReceiptInfo> selectReceiptViewInBtid(@Param("filters")String filters, @Param("value") String value, @Param("sort") String sort,
                                                 @Param("dir") String dir, @Param("mid") int mid,
                                                 @Param("btid")List<Integer> btid);

    Integer selectReceiptViewInBtidTotal(@Param("filters")String filters, @Param("value") String value, @Param("mid") int mid,
                                         @Param("btid")List<Integer> btid);

    Integer deleteReceipt(@Param("id") int id);

    MmReceipt selectReceiptById(@Param("id")int id);

    Integer updateReceipt(MmReceipt mmReceipt);

    Integer insertReceipt(List<MmReceipt> mmReceipts);

    Integer updateReceiptState(List<MmReceipt> mmReceipts);
}
