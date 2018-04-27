package com.hy.mapper.ms;

import com.hy.model.QzgzBusinessTravel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzBusinessTravelMapper {
    List<QzgzBusinessTravel> selectBusinessTravel();
    Integer insertBusinessTravel(QzgzBusinessTravel qzgzBusinessTravel);
    Integer deleteBusinessTravel(int id);
    Integer updateBusinessTravel(QzgzBusinessTravel qzgzBusinessTravel);
}
