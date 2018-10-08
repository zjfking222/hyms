package com.hy.service.ad;


import com.alibaba.fastjson.JSONObject;
import com.hy.dto.AdStaffDto;
import com.hy.mapper.ms.AdStaffMapper;

import com.hy.model.AdStaff;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class AdStaffServiceImpl implements AdStaffService {
    @Autowired
    private AdStaffMapper adStaffMapper;

    @Override
    public boolean addAdStaff(JSONObject adStaffObj) {
        Object jsonArray = adStaffObj.getJSONArray("staff");
        Object jsonState = adStaffObj.get("state");
        String ja = JSONObject.toJSONString(jsonArray);
        String js = JSONObject.toJSONString(jsonState);
        try {
            int state = Integer.parseInt(js);

            List<AdStaffDto> adStaffs = JSONObject.parseArray(ja, AdStaffDto.class);
            List<AdStaff> adStaff = DTOUtil.populateList(adStaffs, AdStaff.class);
            IntStream.range(0, adStaff.size()).forEach(i -> {
                adStaff.get(i).setSid(adStaffs.get(i).getId());
                adStaff.get(i).setState(state);
                adStaff.get(i).setCreater(-1);
                adStaff.get(i).setModifier(-1);
            });
            return adStaffMapper.insertStaff(adStaff) == adStaff.size();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
