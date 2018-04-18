package com.hy.mapper.ms;

import com.hy.model.Bus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusMapper {
    List<Bus> selectBus();
}
