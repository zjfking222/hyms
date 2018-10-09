package com.hy.service.ad;

import com.alibaba.fastjson.JSONObject;
import com.hy.dto.AdDepartmentDto;
import com.hy.mapper.ms.AdDepartmentMapper;
import com.hy.model.AdDepartment;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class AdDepartmentServiceImpl implements AdDepartmentService {
    @Autowired
    private AdDepartmentMapper adDepartmentMapper;

    @Override
    public boolean addAdDepartment(JSONObject adDepartmentObj) {
        Object jsonArray = adDepartmentObj.getJSONArray("department");
        Object jsonState = adDepartmentObj.get("state");
        Object jsonDate = adDepartmentObj.get("date");
        Object jsonTime = adDepartmentObj.get("time");
        String ja = JSONObject.toJSONString(jsonArray);
        String js = JSONObject.toJSONString(jsonState);
        String date = JSONObject.toJSONString(jsonDate).substring(1, JSONObject.toJSONString(jsonDate).length() - 1);
        String time = JSONObject.toJSONString(jsonTime).substring(1, JSONObject.toJSONString(jsonTime).length() - 1);
        try {
            int state = Integer.parseInt(js);
            List<AdDepartmentDto> adDepartmentDtos = JSONObject.parseArray(ja, AdDepartmentDto.class);
            List<AdDepartment> adDepartments = DTOUtil.populateList(adDepartmentDtos, AdDepartment.class);
            IntStream.range(0, adDepartments.size()).forEach(i -> {
                adDepartments.get(i).setDate(date);
                adDepartments.get(i).setTime(time);
                adDepartments.get(i).setState(state);
                adDepartments.get(i).setCreater(-1);
                adDepartments.get(i).setModifier(-1);
            });
            return adDepartmentMapper.insertAdDepartment(adDepartments) == adDepartments.size();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
