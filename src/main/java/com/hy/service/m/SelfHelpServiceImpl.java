package com.hy.service.m;

import com.hy.common.SecurityUtil;
import com.hy.config.jco.JcoUtil;
import com.hy.dto.*;
import com.hy.mapper.zk.CheckinoutMapper;
import com.hy.model.Checkinout;
import com.hy.utils.VerificationCodeUtil;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/1 17:17
 * @Description:个人自助模块公用方法实现
 */
@Service
public class SelfHelpServiceImpl implements SelfHelpService {

    private static final Logger logger = LoggerFactory.getLogger(SelfHelpServiceImpl.class);
    @Autowired
    private CheckinoutMapper checkinoutMapper;
    //验证码保存key
    private String codeKey = "verificationCode";

    /**
     * @Author 钱敏杰
     * @Description 获取图片验证码并将验证码存入session
     * @Date 2018/12/1 10:42
     * @Param [out]
     * @return void
     **/
    @Override
    public void getVerificationCode(OutputStream out){
        try {
            //获取长度为4，屏蔽部分字母数字的验证码
            String code = VerificationCodeUtil.getRandomCode(4, "0oO1iIl");
            //将code保存到session中
            SecurityUtil.setAttribute(codeKey, code);
            //生成验证码图片
            BufferedImage image = VerificationCodeUtil.getCodeImage(95,40, code);
            //输出到页面
            ImageIO.write(image,"jpg", out);
        } catch (IOException e) {
            logger.error("验证码生成异常！", e);
            throw new RuntimeException("验证码生成异常！");
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 验证验证码
     * @Date 2018/11/5 16:37
     * @Param [code]
     * @return boolean
     **/
    @Override
    public boolean authentication(String code){
        //取出保存的验证码
        String rcode = (String)SecurityUtil.getAttribute(codeKey);
        //不区分大小写
        rcode = rcode.toLowerCase();
        code = code.toLowerCase();
        if(StringUtils.isNotEmpty(code) && code.equals(rcode)){
            //使该验证码失效
            SecurityUtil.removeAttribute(codeKey);
            return true;
        }else{
            return false;
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前年月当前用户的薪资数据
     * @Date 2018/11/7 16:01
     * @Param [id, password, year, month]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public Map<String, Object> getSalaryData(String id, String password, String year, String month){
        //获取操作对象
        JCoDestination destination = JcoUtil.getInstance("");
        //获取函数
        JCoFunction function = JcoUtil.getFunction(destination, "ZHR_OE009_SALARY_MANAGEMENT");
        //设置参数
        JcoUtil.setParameter(function,"I_PERNR", id);
        JcoUtil.setParameter(function,"I_MONTH", month);
        JcoUtil.setParameter(function,"I_YEAR", year);
        JcoUtil.setParameter(function,"I_UPASS", password);
        //执行获取数据
        JcoUtil.executeFunction(function, destination);
        Map<String, Object> results = new HashMap<>();
        //获取查询结果标记
        String flag =JcoUtil.getExportParameter(function, "LDAPRC");
        if("13".equals(flag)){
            results.put("flag", "false");
            results.put("message", "工号与输入账号不匹配！");
        }else if("17".equals(flag)){
            results.put("flag", "false");
            results.put("message", "初始密码未修改！");
        }else if("49".equals(flag)){
            results.put("flag", "false");
            results.put("message", "密码验证失败！");
        }else{
            results.put("flag", "true");
            results.put("message", "成功！");
            //取出数据表数据
            JCoTable codes = JcoUtil.getTable(function, "ZHR_PAYSLIP");
            SapSalaryDto dto = new SapSalaryDto();
            //只有一条薪资数据，指向该数据
            codes.setRow(0);
            dto = JcoUtil.getInfoFromTable(codes, dto);
            if(dto != null && StringUtils.isNotEmpty(dto.getPernr())){
                //计算代扣合计数据
                Float dkhj = Float.parseFloat(dto.getZwt022())+Float.parseFloat(dto.getZwt023())+Float.parseFloat(dto.getZwt024())
                        +Float.parseFloat(dto.getZwt025())+Float.parseFloat(dto.getZwt026())+Float.parseFloat(dto.getZwt027())
                        +Float.parseFloat(dto.getZwt028())+Float.parseFloat(dto.getZwt029())+Float.parseFloat(dto.getZwt030())+Float.parseFloat(dto.getZwt031());
                //取小数点后2位
                DecimalFormat df = new DecimalFormat("#.00");
                String hj = df.format(dkhj);
                dto.setDkhj(hj);
            }
            results.put("salaryData", dto);
        }
        return results;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前年月当前用户的考勤汇总数据
     * @Date 2018/11/7 16:49
     * @Param [id, year, month]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public Map<String, Object> getAttendance(String id, String year, String month){
        //获取操作对象
        JCoDestination destination = JcoUtil.getInstance("");
        //获取函数
        JCoFunction function = JcoUtil.getFunction(destination, "ZHR_OE009_ATTENDENCE");
        //设置参数
        JcoUtil.setParameter(function,"I_PERNR", id);
        JcoUtil.setParameter(function,"I_MONTH", month);
        JcoUtil.setParameter(function,"I_YEAR", year);
        //执行获取数据
        JcoUtil.executeFunction(function, destination);
        Map<String, Object> results = new HashMap<>();
        results.put("year", year);
        results.put("month", month);
        //取出数据表数据
        JCoTable codes = JcoUtil.getTable(function, "ZHR_KQYDB");
        SapAttendanceDto dto = null;
        //存在数据，则返回
        if(codes.getNumRows() >0){
            dto = new SapAttendanceDto();
            JcoUtil.getInfoFromTable(codes, dto);
            results.put("dto", dto);
        }
        return results;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取调用个人基本信息的接口
     * @Date 2018/11/7 19:46
     * @Param [id]
     * @return com.sap.conn.jco.JCoFunction
     **/
    @Override
    public JCoFunction getEmployeeFunction(String id){
        //获取操作对象
        JCoDestination destination = JcoUtil.getInstance("");
        //获取函数
        JCoFunction function = JcoUtil.getFunction(destination, "ZHR_OE009_EMPLOYEE_INFO");
        //设置参数
        JcoUtil.setParameter(function,"I_PERNR", id);
        //执行获取数据
        JcoUtil.executeFunction(function, destination);
        return function;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的员工基本信息数据
     * @Date 2018/11/7 19:55
     * @Param [function]
     * @return com.hy.dto.SapBaseInfoDto
     **/
    @Override
    public SapBaseInfoDto getBaseInfo(JCoFunction function){
        //取出数据表数据
        JCoTable codes = JcoUtil.getTable(function, "ZHR_PSNDOC");
        SapBaseInfoDto dto = null;
        //存在数据，则返回
        if(codes.getNumRows() >0){
            dto = new SapBaseInfoDto();
            JcoUtil.getInfoFromTable(codes, dto);
        }
        return dto;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的合同信息
     * @Date 2018/11/8 8:03
     * @Param [function]
     * @return com.hy.dto.SapContractInfoDto
     **/
    @Override
    public SapContractInfoDto getContractInfo(JCoFunction function){
        //取出数据表数据
        JCoTable codes = JcoUtil.getTable(function, "ZHR_HT");
        SapContractInfoDto dto = null;
        //存在数据，则返回
        if(codes.getNumRows() >0){
            dto = new SapContractInfoDto();
            JcoUtil.getInfoFromTable(codes, dto);
        }
        return dto;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的职称信息
     * @Date 2018/11/8 10:11
     * @Param [function]
     * @return java.util.List<com.hy.dto.SapTechTitleDto>
     **/
    @Override
    public List<SapTechTitleDto> getTechTitleInfo(JCoFunction function){
        //取出数据表数据
        JCoTable codes = JcoUtil.getTable(function, "ZHR_ZC");
        List<SapTechTitleDto> list = null;
        //存在数据，则返回
        if(codes.getNumRows() >0){
            SapTechTitleDto dto = new SapTechTitleDto();
            list = JcoUtil.getInfoListFromTable(codes, dto);
        }
        return list;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的资格证书信息
     * @Date 2018/11/8 9:38
     * @Param [function]
     * @return java.util.List<com.hy.dto.SapQuaCertificateDto>
     **/
    @Override
    public List<SapQuaCertificateDto> getQuaCertificateInfo(JCoFunction function){
        //取出数据表数据
        JCoTable codes = JcoUtil.getTable(function, "ZHR_ZGZS");
        List<SapQuaCertificateDto> list = null;
        //存在数据，则返回
        if(codes.getNumRows() >0){
            SapQuaCertificateDto dto = new SapQuaCertificateDto();
            list = JcoUtil.getInfoListFromTable(codes, dto);
        }
        return list;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的年休假调休信息
     * @Date 2018/11/8 11:13
     * @Param [function]
     * @return java.util.List<com.hy.dto.SapVacationDto>
     **/
    @Override
    public List<SapVacationDto> getVacationInfo(JCoFunction function){
        //取出数据表数据
        JCoTable codes = JcoUtil.getTable(function, "ZHR_NXTX");
        List<SapVacationDto> list = null;
        //存在数据，则返回
        if(codes.getNumRows() >0){
            SapVacationDto dto = new SapVacationDto();
            list = JcoUtil.getInfoListFromTable(codes, dto);
        }
        return list;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的技能等级信息
     * @Date 2018/11/8 8:24
     * @Param [function]
     * @return com.hy.dto.SapSkillLevelDto
     **/
    @Override
    public SapSkillLevelDto getSkillLevelInfo(JCoFunction function){
        //取出数据表数据
        JCoTable codes = JcoUtil.getTable(function, "ZHR_JNDJ");
        SapSkillLevelDto dto = null;
        //存在数据，则返回
        if(codes.getNumRows() >0){
            dto = new SapSkillLevelDto();
            JcoUtil.getInfoFromTable(codes, dto);
        }
        return dto;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的排班表信息
     * @Date 2018/11/8 16:06
     * @Param [id, stime, etime]
     * @return java.util.List<com.hy.dto.SapSchedulingDto>
     **/
    @Override
    public List<SapSchedulingDto> getSchedulingInfo(String id, String stime, String etime){
        //获取操作对象
        JCoDestination destination = JcoUtil.getInstance("");
        //获取函数
        JCoFunction function = JcoUtil.getFunction(destination, "ZHR_OE009_ARRANGMENT");
        //设置参数
        JcoUtil.setParameter(function,"I_PERNR", id);
        JcoUtil.setParameter(function,"I_BEGDA", stime);
        JcoUtil.setParameter(function,"I_ENDDA", etime);
        //执行获取数据
        JcoUtil.executeFunction(function, destination);
        //取出数据表数据
        JCoTable codes = JcoUtil.getTable(function, "ZHR_YDPB");
        List<SapSchedulingDto> list = null;
        //存在数据，则返回
        if(codes.getNumRows() >0){
            SapSchedulingDto dto = new SapSchedulingDto();
            list = JcoUtil.getInfoListFromTable(codes, dto);
        }
        return list;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的原始打卡记录，并按日期整理
     * @Date 2018/11/10 10:48
     * @Param [id, stime, etime]
     * @return java.util.Map<java.lang.String,java.util.List<com.hy.model.Checkinout>>
     **/
    @Override
    public Map<String, List<Checkinout>> getRecord(String id, Date stime, Date etime){
        //查询数据库，获取打卡记录
        List<Checkinout> list = checkinoutMapper.findByUserId(id, stime, etime);
        //整理数据，同日期的放一起
        Map<String, List<Checkinout>> map = new HashMap<>();
        if(list != null && list.size() >0){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //将数据按日期重分配数据
            for(Checkinout c:list){
                String date = sdf.format(c.getChecktime());
                List<Checkinout> lc = map.get(date);
                if(lc == null){
                    lc = new ArrayList<>();
                    map.put(date, lc);
                }
                lc.add(c);
            }
        }
        return map;
    }

    /**
     * @Author 钱敏杰
     * @Description 整理同一月中的数据，合并同一天的数据到一个dto对象中
     * @Date 2018/11/10 13:58
     * @Param [id, data]
     * @return java.util.Map<java.lang.String,com.hy.dto.SelfRecordDto>
     **/
    public Map<String, SelfRecordDto> arrangeRecordData(String id, Map<String, List<Checkinout>> data){
        Map<String, SelfRecordDto> results = new HashMap<>();
        SelfRecordDto dto = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //当前日期
        SimpleDateFormat tsdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = tsdf.format(new Date());
        if(data != null && !data.isEmpty()){
            //遍历数据，比较其打卡时间，取出当天最早与最晚时间，并保存到dto对象中
            Iterator<String> keys = data.keySet().iterator();
            String key = null;
            while(keys.hasNext()){
                key = keys.next();
                List<Checkinout> list = data.get(key);
                if(list != null && list.size() >0){
                    dto = new SelfRecordDto();
                    dto.setPernr(id);
                    if(today.equals(key)){//查询的当天默认数据量为2条，因为尚未记录完，不需要统计
                        dto.setRecnum(2);
                    }else{
                        dto.setRecnum(list.size());
                    }
                    dto.setDate(key);
                    if(list.size() <2){//打卡记录小于2条，只记录最早的数据
                        dto.setEarliestTime(sdf.format(list.get(0).getChecktime()));
                        dto.setEalias(list.get(0).getAlias());
                        dto.setEcity(list.get(0).getCity());
                        //防止页面显示null
                        dto.setLatestTime("");
                        dto.setLalias("");
                        dto.setLcity("");
                    }else{
                        for(Checkinout c:list){
                            try {
                                if(StringUtils.isEmpty(dto.getEarliestTime()) || c.getChecktime().before(sdf.parse(dto.getEarliestTime()))){//若为空或者数据更早，则添加或更新数据
                                    dto.setEarliestTime(sdf.format(c.getChecktime()));
                                    dto.setEalias(c.getAlias());
                                    dto.setEcity(c.getCity());
                                }
                                if(StringUtils.isEmpty(dto.getLatestTime()) || c.getChecktime().after(sdf.parse(dto.getEarliestTime()))){//若为空或者数据更晚，则添加或更新数据
                                    dto.setLatestTime(sdf.format(c.getChecktime()));
                                    dto.setLalias(c.getAlias());
                                    dto.setLcity(c.getCity());
                                }
                            } catch (ParseException e) {
                                logger.error("数据整理异常！", e);
                            }
                        }
                    }
                    //同一月内的数据，改用天数作为主键
                    String day = key.substring(key.length()-2);
                    Integer d = Integer.parseInt(day);
                    results.put(d.toString(), dto);
                }
            }
        }
        return results;
    }
}
