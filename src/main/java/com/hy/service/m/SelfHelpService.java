package com.hy.service.m;

import com.hy.dto.*;
import com.hy.model.Checkinout;
import com.sap.conn.jco.JCoFunction;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/1 17:16
 * @Description:个人自助模块公用方法接口
 */
public interface SelfHelpService {

    /**
     * @Author 钱敏杰
     * @Description 获取图片验证码并将验证码存入session
     * @Date 2018/11/5 16:32
     * @Param [response]
     * @return void
     **/
    public void getVerificationCode(HttpServletResponse response);

    /**
     * @Author 钱敏杰
     * @Description 验证验证码
     * @Date 2018/11/5 16:37
     * @Param [code]
     * @return boolean
     **/
    public boolean authentication(String code);

    /**
     * @Author 钱敏杰
     * @Description 获取当前年月当前用户的薪资数据
     * @Date 2018/11/7 16:01
     * @Param [id, password, year, month]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> getSalaryData(String id, String password, String year, String month);

    /**
     * @Author 钱敏杰
     * @Description 获取当前年月当前用户的考勤汇总数据
     * @Date 2018/11/7 16:49
     * @Param [id, year, month]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> getAttendance(String id, String year, String month);

    /**
     * @Author 钱敏杰
     * @Description 获取调用个人基本信息的接口
     * @Date 2018/11/7 19:46
     * @Param [id]
     * @return com.sap.conn.jco.JCoFunction
     **/
    public JCoFunction getEmployeeFunction(String id);

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的员工基本信息数据
     * @Date 2018/11/7 19:55
     * @Param [function]
     * @return com.hy.dto.SapBaseInfoDto
     **/
    public SapBaseInfoDto getBaseInfo(JCoFunction function);

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的合同信息
     * @Date 2018/11/8 8:03
     * @Param [function]
     * @return com.hy.dto.SapContractInfoDto
     **/
    public SapContractInfoDto getContractInfo(JCoFunction function);

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的职称信息
     * @Date 2018/11/8 10:11
     * @Param [function]
     * @return java.util.List<com.hy.dto.SapTechTitleDto>
     **/
    public List<SapTechTitleDto> getTechTitleInfo(JCoFunction function);

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的资格证书信息
     * @Date 2018/11/8 9:38
     * @Param [function]
     * @return java.util.List<com.hy.dto.SapQuaCertificateDto>
     **/
    public List<SapQuaCertificateDto> getQuaCertificateInfo(JCoFunction function);

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的年休假调休信息
     * @Date 2018/11/8 11:13
     * @Param [function]
     * @return java.util.List<com.hy.dto.SapVacationDto>
     **/
    public List<SapVacationDto> getVacationInfo(JCoFunction function);

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的技能等级信息
     * @Date 2018/11/8 8:24
     * @Param [function]
     * @return com.hy.dto.SapSkillLevelDto
     **/
    public SapSkillLevelDto getSkillLevelInfo(JCoFunction function);

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的排班表信息
     * @Date 2018/11/8 16:06
     * @Param [id, stime, etime]
     * @return java.util.List<com.hy.dto.SapSchedulingDto>
     **/
    public List<SapSchedulingDto> getSchedulingInfo(String id, String stime, String etime);

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的原始打卡记录，并按日期整理
     * @Date 2018/11/10 10:48
     * @Param [id, stime, etime]
     * @return java.util.Map<java.lang.String,java.util.List<com.hy.model.Checkinout>>
     **/
    public Map<String, List<Checkinout>> getRecord(String id, Date stime, Date etime);

    /**
     * @Author 钱敏杰
     * @Description 整理同一月中的数据，合并同一天的数据到一个dto对象中
     * @Date 2018/11/10 13:58
     * @Param [id, data]
     * @return java.util.Map<java.lang.String,com.hy.dto.SelfRecordDto>
     **/
    public Map<String, SelfRecordDto> arrangeRecordData(String id, Map<String, List<Checkinout>> data);
}
