package com.hy.service.ad;

import com.alibaba.fastjson.JSONObject;
import com.hy.dto.AdDepartmentDto;
import com.hy.model.AdDepartment;
import com.hy.model.LdapDepartment;

import java.util.List;

public interface AdDepartmentService {
    boolean addAdDepartment(JSONObject adDepartmentObj);
    List<AdDepartmentDto> selectAdDepartment(String date,String time);
    List<AdDepartmentDto> getTime(String date);
    List<AdDepartmentDto> getChangeDep(String date,String time);
    List<LdapDepartment> getSapDepartment();
}
