package com.hy.mapper.ms;

import com.hy.model.QzgzBus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzBusMapper {
    List<QzgzBus> selectBus();
    int updateBus(QzgzBus qzgzBus);
    int insertBus(QzgzBus qzgzBus);
    int deleteBus(QzgzBus qzgzBus);
}
