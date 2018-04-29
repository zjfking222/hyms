package com.hy.service.qzgz;

import com.hy.dto.BusinessTravelDto;

import java.util.List;

public interface BusinessTravelService {
    List<BusinessTravelDto> getBusinessTravel();
    boolean addBusinessTravel(String title, String img);
    boolean delBusinessTravel(int id);
    boolean setBusinessTravel(BusinessTravelDto businessTravelDto);
}
