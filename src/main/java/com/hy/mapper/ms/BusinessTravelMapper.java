package com.hy.mapper.ms;

import com.hy.model.BusinessTravel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessTravelMapper {
    List<BusinessTravel> selectBusinessTravel();
}
