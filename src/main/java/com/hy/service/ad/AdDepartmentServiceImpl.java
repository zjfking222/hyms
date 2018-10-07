package com.hy.service.ad;

import com.alibaba.fastjson.JSONObject;
import com.hy.dto.AdDepartmentDto;
import com.hy.mapper.ms.AdDepartmentMapper;
import com.hy.model.AdDepartment;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class AdDepartmentServiceImpl implements AdDepartmentService {
    @Autowired
    private AdDepartmentMapper adDepartmentMapper;

    @Override
    public boolean addAdDepartment(JSONObject adDepartmentObj) {
        Object jsonArray = adDepartmentObj.getJSONArray("department");
        System.out.println(jsonArray);
        String js = JSONObject.toJSONString(jsonArray);
        List<AdDepartmentDto> adDepartmentDtos = JSONObject.parseArray(js, AdDepartmentDto.class);
        List<AdDepartment> adDepartments = DTOUtil.populateList(adDepartmentDtos, AdDepartment.class);
        IntStream.range(0, adDepartments.size()).forEach(i -> {
            adDepartments.get(i).setCreater(-1);
            adDepartments.get(i).setModifier(-1);
        });
        return adDepartmentMapper.insertAdDepartment(adDepartments) == adDepartments.size();
    }
}
