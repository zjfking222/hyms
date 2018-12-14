package com.hy.service.ad;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hy.common.ResultObj;
import com.hy.common.SecurityUtil;
import com.hy.config.jco.JcoUtil;
import com.hy.dto.AdDepartmentDto;
import com.hy.dto.AdStaffDto;
import com.hy.enums.ResultCode;
import com.hy.mapper.ms.AdDepartmentMapper;
import com.hy.mapper.ms.AdStaffMapper;
import com.hy.model.AdDepartment;
import com.hy.model.LdapDepartment;
import com.hy.model.LdapStaff;
import com.hy.utils.DTOUtil;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class AdDepartmentServiceImpl implements AdDepartmentService {
    @Autowired
    private AdDepartmentMapper adDepartmentMapper;
    @Autowired
    private AdStaffService adStaffService;
    @Autowired
    private AdDepartmentService adDepartmentService;

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
                adDepartments.get(i).setCreater("-1");
                adDepartments.get(i).setModifier("-1");
            });
            return adDepartmentMapper.insertAdDepartment(adDepartments) == adDepartments.size();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public String selectAdDepartment(){
        try {
            List<LdapStaff> staffs = adStaffService.getSapStaff();//获得SAP人员
            List<LdapDepartment> ldapDep = adDepartmentService.getSapDepartment();//获得SAP组织架构
            Map<String, AdDepartmentDto> adDepartmentMap = new HashMap<>();//创建组织架构MAP
            Map<Integer, String> ldapStaffMap = new HashMap<>();//创建人员MAP
            List<AdDepartmentDto>  adDepartment = new ArrayList<>();//新建一个List

            IntStream.range(0, ldapDep.size()).forEach(i -> {  //将SAP部门id和名字添加至newList
                AdDepartmentDto department = new AdDepartmentDto();//新建一个departmentDto
                department.setDid(String.valueOf(ldapDep.get(i).getId()));
                department.setName(ldapDep.get(i).getName());
                if(null == ldapDep.get(i).getParentId() || ldapDep.get(i).getParentId().equals("")) {//若部门没有父id，则将父id设置为10000000
                    department.setParentid("99999999");
                }
                else {
                    department.setParentid(String.valueOf(ldapDep.get(i).getParentId()));//否则将原部门父id设为他的父id
                }
                department.setChild(new ArrayList<>());//新建属性child
                adDepartment.add(department);//将该Dto添加至newList
            });

            List<AdDepartmentDto>  adDepartmentReturn = new ArrayList<>(); //新建一个返回list
            IntStream.range(0, staffs.size()).forEach(i -> {//遍历SAP员工，获取所有员工部门id
                ldapStaffMap.put(i, staffs.get(i).getDepid());//将所有员工部门id，添加进新Map
            });
            IntStream.range(0, adDepartment.size()).forEach(i -> {//遍历所有部门
                adDepartmentMap.put(adDepartment.get(i).getDid(),adDepartment.get(i));//遍历newList，将相同父id的部门放在一起，归于Map
            });

            ldapStaffMap.forEach((key, value) -> {//遍历所有员工部门id
                IntStream.range(0, adDepartment.size()).forEach(i -> {//遍历所有部门

                    if(value.equals(String.valueOf(adDepartment.get(i).getDid()))){//若员工部门id==部门Map中的部门id
                        AdDepartmentDto adStaffDto1 = new AdDepartmentDto();//新建一个dto
                        adStaffDto1.setDid(String.valueOf(staffs.get(key).getId()));//设置该员工dto的id和name
                        adStaffDto1.setName(staffs.get(key).getName());
                        adDepartmentMap.get(adDepartment.get(i).getDid()).getChild().add(adStaffDto1);//将该dto放入部门Map中的staff属性
                    }

                });
            });

            IntStream.range(0, adDepartment.size()).forEach(i -> {//遍历所有部门
                if(adDepartmentMap.containsKey(adDepartment.get(i).getParentid())){//若存在父id
                    adDepartmentMap.get(adDepartment.get(i).getParentid()).getChild()
                            .add(adDepartment.get(i));
                };
                if(adDepartment.get(i).getDid().equals("10000000")){
                    adDepartmentReturn.add(adDepartment.get(i));
                }
            });

            String str = JSON.toJSONString(adDepartmentReturn);

            return str;

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

    @Override
    public List<LdapDepartment> getSapDepartment() {
        try {
            JCoDestination destination = JcoUtil.getInstance("ABAP_AS");
            List<LdapDepartment> depList = new ArrayList<>();
            JCoFunction function = destination.getRepository().getFunction("ZHR_AD001_ORGEH_INFO");//从对象仓库中获取 RFM 函数：获取公司列表
            if (function == null)
                throw new RuntimeException("ZHR_AD001_ORGEH_INFO not found in SAP.");
            try {
                function.execute(destination);
            } catch (AbapException e) {
                System.out.println(e.toString());
            }
            JCoTable codes = function.getTableParameterList().getTable("ZHRS_ORGEH");

            IntStream.range(0, codes.getNumRows()).forEach(i -> {
                codes.setRow(i);
                depList.add(new LdapDepartment(codes.getString("ZBMID"),codes.getString("ZBMMC"),codes.getString("ZCXBZ"),
                        codes.getString("ZSJBM"),codes.getString("ZSJMC")));
            });
            return depList;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public Integer updateOperator(String id){
        return adDepartmentMapper.updateOperator(SecurityUtil.getLoginid(), id);
    }
}
