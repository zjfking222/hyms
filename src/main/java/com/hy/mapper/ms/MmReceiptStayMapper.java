package com.hy.mapper.ms;

import com.hy.model.MmReceiptStay;
import com.hy.model.VMmReceiptStay;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MmReceiptStayMapper {
    List<VMmReceiptStay> selectReceiptStayView(@Param("rid") int rid);
    List<MmReceiptStay> selectReceiptStay(@Param("rid") int rid);
    Integer updateReceiptStay(List<MmReceiptStay> mmReceiptStays);
    Integer insertReceiptStay(List<MmReceiptStay> mmReceiptStays);
}
