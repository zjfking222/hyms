package com.hy.mapper.ms;

import com.hy.model.MmReceiptDines;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MmReceiptDinesMapper {
    List<MmReceiptDines> selectReceiptDines(@Param("rid")int rid);
    Integer updateReceiptDines(List<MmReceiptDines> mmReceiptDines);
}
