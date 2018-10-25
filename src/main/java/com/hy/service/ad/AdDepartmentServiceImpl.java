package com.hy.service.ad;

import com.alibaba.fastjson.JSONObject;
import com.hy.common.ResultObj;
import com.hy.dto.AdDepartmentDto;
import com.hy.enums.ResultCode;
import com.hy.mapper.ms.AdDepartmentMapper;
import com.hy.model.AdDepartment;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
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
    @Override
    public List<AdDepartmentDto> selectAdDepartment(String date,String time){
        try {
            Map<Integer, AdDepartmentDto> adDepartmentMap = new HashMap<>();
            List<AdDepartmentDto>  adDepartment =
                    DTOUtil.populateList(adDepartmentMapper.selectAdDepartment(date,time),
                            new ArrayList<AdDepartmentDto>(),AdDepartmentDto.class);
            List<AdDepartmentDto>  adDepartmentReturn = new ArrayList<>();
            IntStream.range(0, adDepartment.size()).forEach(i -> {
                adDepartment.get(i).setChild(new ArrayList<>());
                adDepartmentMap.put(adDepartment.get(i).getDid(),adDepartment.get(i));
            });
            IntStream.range(0, adDepartment.size()).forEach(i -> {
                if(adDepartmentMap.containsKey(adDepartment.get(i).getParentid())){
                    adDepartmentMap.get(adDepartment.get(i).getParentid()).getChild()
                            .add(adDepartment.get(i));
                }
            });
            IntStream.range(0, adDepartment.size()).forEach(i ->
            {
                if(adDepartment.get(i).getParentid() == 0&&!adDepartment.get(i).getName().contentEquals("新组织单位")){
//                    &&!adDepartment.get(i).getChild().isEmpty()
                    adDepartmentReturn.add(adDepartment.get(i));
                }
            });
            return adDepartmentReturn;

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List getTime(String date) {
        try {
            List<AdDepartmentDto>  adStaff =
                    DTOUtil.populateList(adDepartmentMapper.getTime(date),
                            new ArrayList<>(),AdDepartmentDto.class);
            return DTOUtil.populateList(adStaff, AdDepartmentDto.class);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List getChangeDep(String date,String time) {
        try {
            List<AdDepartmentDto>  getChangeDep =
                    DTOUtil.populateList(adDepartmentMapper.getChangeDep(date,time),
                            new ArrayList<>(),AdDepartmentDto.class);
            return DTOUtil.populateList(getChangeDep, AdDepartmentDto.class);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }
}
