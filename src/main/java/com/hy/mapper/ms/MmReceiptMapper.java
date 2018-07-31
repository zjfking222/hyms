package com.hy.mapper.ms;

import com.hy.model.MmReceipt;
import com.hy.model.VMmMeetingReceipt;
import com.hy.model.VMmReceiptAgenda;
import com.hy.model.VMmReceiptInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MmReceiptMapper {
    List<VMmMeetingReceipt> selectMeetingView(@Param("value") String value, @Param("sort") String sort,
                                              @Param("dir") String dir);
    Integer selectMeetingViewTotal(@Param("value") String value);

    List<VMmReceiptInfo> selectReceiptView(@Param("value") String value, @Param("sort") String sort,
                                           @Param("dir") String dir, @Param("mid") int mid);

    Integer selectReceiptViewTotal(@Param("value") String value, @Param("mid")int mid);

    Integer deleteReceipt(@Param("id") int id);

    MmReceipt selectReceiptById(@Param("id")int id);
}
