package com.hy.service.ad;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.hy.dto.AdStaffDto;
import com.hy.mapper.ms.AdStaffMapper;

import com.hy.model.AdDepartment;
import com.hy.model.AdStaff;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class AdStaffServiceImpl implements AdStaffService {
    @Autowired
    private AdStaffMapper adStaffMapper;

    @Override
    public boolean addAdStaff(JSONObject adStaffObj) {
        Object jsonArray = adStaffObj.getJSONArray("staff");
        Object jsonState = adStaffObj.get("state");
        Object jsonDate = adStaffObj.get("date");
        Object jsonTime = adStaffObj.get("time");
        String ja = JSONObject.toJSONString(jsonArray);
        String js = JSONObject.toJSONString(jsonState);
        String date = JSONObject.toJSONString(jsonDate).substring(1, JSONObject.toJSONString(jsonDate).length()-1);
        String time= JSONObject.toJSONString(jsonTime).substring(1, JSONObject.toJSONString(jsonTime).length()-1);
        try {
            int state = Integer.parseInt(js);

            List<AdStaffDto> adStaffs = JSONObject.parseArray(ja, AdStaffDto.class);
            List<AdStaff> adStaff = DTOUtil.populateList(adStaffs, AdStaff.class);
            IntStream.range(0, adStaff.size()).forEach(i -> {
                adStaff.get(i).setDate(date);
                adStaff.get(i).setTime(time);
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

    @Override
    public List selectAdStaff(int page,int limit,String date,String time) {
        try {
            int startLine=(page-1)*limit;

            List<AdStaffDto>  adStaff =
                    DTOUtil.populateList(adStaffMapper.selectAdStaff(startLine,limit,date,time),
                            new ArrayList<AdStaffDto>(),AdStaffDto.class);
            return DTOUtil.populateList(adStaff, AdStaffDto.class);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }
    @Override
    public Integer getCount(String date,String time){
        return adStaffMapper.getCount(date,time);
    }
}
