package com.hy.service.ad;

import com.alibaba.fastjson.JSONObject;
import com.hy.dto.AdStaffDto;
import com.hy.model.AdStaff;

import java.util.List;

public interface AdStaffService {
    boolean addAdStaff(JSONObject adStaffObj);
    List<AdStaffDto> selectAdStaff(int page,int limit,String date,String time);
    List<AdStaff> getSapStaff();
    Integer getCount(String date,String time);
}
