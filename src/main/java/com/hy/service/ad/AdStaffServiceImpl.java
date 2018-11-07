package com.hy.service.ad;


import com.alibaba.fastjson.JSONObject;
import com.hy.config.jco.JcoDestinationDataProvider;
import com.hy.dto.AdStaffDto;
import com.hy.mapper.ms.AdStaffMapper;
import com.hy.model.AdStaff;
import com.hy.utils.DTOUtil;
import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.IntStream;

@Service
public class AdStaffServiceImpl implements AdStaffService {
    @Autowired
    private AdStaffMapper adStaffMapper;

    private void getProvider(){
        // 获取单例
        JcoDestinationDataProvider myProvider = JcoDestinationDataProvider.getInstance();
        Environment.registerDestinationDataProvider(myProvider);
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "192.168.100.152");
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "800");
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, "BG_OA");
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "Huay20180401");
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "zh");
        String destinationName = "ABAP_AS";
        myProvider.addDestination(destinationName, connectProperties);
    }
    //sap端获取人员和部门
    @Override
    public List<AdStaff> getSapStaff(){
        getProvider();
        try {
            JCoDestination destination = JCoDestinationManager.getDestination("ABAP_AS");
            List<AdStaff> staffList = new ArrayList<>();
            JCoFunction function = destination.getRepository().getFunction("ZHR_AD002_PERSON_INFO");//从对象仓库中获取 RFM 函数：获取公司列表
            if (function == null)
                throw new RuntimeException("ZHR_AD002_PERSON_INFO not found in SAP.");
            try {
                function.execute(destination);
            } catch (AbapException e) {
                System.out.println(e.toString());
            }
            JCoTable codes = function.getTableParameterList().getTable("ZHRS_PERNR");

            IntStream.range(0, codes.getNumRows()).forEach(i -> {
                codes.setRow(i);
                staffList.add(new AdStaff(Integer.valueOf(codes.getString("ZPERNR")),codes.getString("ZNACHN"),
                        codes.getString("ZYX"),codes.getString("ZDH"),Integer.valueOf(codes.getString("SBMID")),
                        codes.getString("SBMTX"), codes.getString("SGWTX")));
            });
            return staffList;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
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
