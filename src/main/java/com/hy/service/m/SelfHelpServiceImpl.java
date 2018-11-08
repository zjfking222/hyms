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
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @Date 2018/11/5 16:32
     * @Param [request, response]
     * @return void
     **/
    @Override
    public void getVerificationCode(HttpServletResponse response){
        try {
            //获取长度为4，屏蔽部分字母数字的验证码
            String code = VerificationCodeUtil.getRandomCode(4, "0oO1iIl");
            //将code保存到session中
            SecurityUtil.setAttribute(codeKey, code);
            //生成验证码图片
            BufferedImage image = VerificationCodeUtil.getCodeImage(95,40, code);
            //输出到页面
            ImageIO.write(image,"jpg", response.getOutputStream());
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
     * @Param [id, username, password, year, month]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public Map<String, Object> getSalaryData(String id, String username, String password, String year, String month){
        //获取操作对象
        JCoDestination destination = JcoUtil.getInstance("");
        //获取函数
        JCoFunction function = JcoUtil.getFunction(destination, "ZHR_OE009_SALARY_MANAGEMENT");
        //设置参数
        JcoUtil.setParameter(function,"I_PERNR", id);
        JcoUtil.setParameter(function,"I_MONTH", month);
        JcoUtil.setParameter(function,"I_YEAR", year);
        JcoUtil.setParameter(function,"I_UPASS", password);
        JcoUtil.setParameter(function,"I_UNAME", username);
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
            //计算代扣合计数据
            Float dkhj = Float.parseFloat(dto.getZwt022())+Float.parseFloat(dto.getZwt023())+Float.parseFloat(dto.getZwt024())
                    +Float.parseFloat(dto.getZwt025())+Float.parseFloat(dto.getZwt026())+Float.parseFloat(dto.getZwt027())
                    +Float.parseFloat(dto.getZwt028())+Float.parseFloat(dto.getZwt029())+Float.parseFloat(dto.getZwt030())+Float.parseFloat(dto.getZwt031());
            String hj = dkhj.toString();
            //取小数点后2位
            hj = hj.substring(0, hj.indexOf(".")+3);
            dto.setDkhj(hj);
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
     * @Date 2018/11/8 8:09
     * @Param [function]
     * @return com.hy.dto.SapTechTitleDto
     **/
    @Override
    public SapTechTitleDto getTechTitleInfo(JCoFunction function){
        //取出数据表数据
        JCoTable codes = JcoUtil.getTable(function, "ZHR_ZC");
        SapTechTitleDto dto = null;
        //存在数据，则返回
        if(codes.getNumRows() >0){
            dto = new SapTechTitleDto();
            JcoUtil.getInfoFromTable(codes, dto);
        }
        return dto;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的资格证书信息
     * @Date 2018/11/8 8:15
     * @Param [function]
     * @return com.hy.dto.SapQuaCertificateDto
     **/
    @Override
    public SapQuaCertificateDto getQuaCertificateInfo(JCoFunction function){
        //取出数据表数据
        JCoTable codes = JcoUtil.getTable(function, "ZHR_ZGZS");
        SapQuaCertificateDto dto = null;
        //存在数据，则返回
        if(codes.getNumRows() >0){
            dto = new SapQuaCertificateDto();
            JcoUtil.getInfoFromTable(codes, dto);
        }
        return dto;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的年休假调休信息
     * @Date 2018/11/8 8:20
     * @Param [function]
     * @return com.hy.dto.SapVacationDto
     **/
    @Override
    public SapVacationDto getVacationInfo(JCoFunction function){
        //取出数据表数据
        JCoTable codes = JcoUtil.getTable(function, "ZHR_NXTX");
        SapVacationDto dto = null;
        //存在数据，则返回
        if(codes.getNumRows() >0){
            dto = new SapVacationDto();
            JcoUtil.getInfoFromTable(codes, dto);
        }
        return dto;
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
     * @Date 2018/11/8 8:59
     * @Param [id, stime, etime]
     * @return com.hy.dto.SapSchedulingDto
     **/
    @Override
    public SapSchedulingDto getSchedulingInfo(String id, String stime, String etime){
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
        JCoTable codes = JcoUtil.getTable(function, "ZHR_KQYDB");
        SapSchedulingDto dto = null;
        //存在数据，则返回
        if(codes.getNumRows() >0){
            dto = new SapSchedulingDto();
            JcoUtil.getInfoFromTable(codes, dto);
        }
        return dto;
    }

    public void getRecord(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date stime = sdf.parse("2018-11-01");
            Date etime = sdf.parse("2018-11-03");
            List<Checkinout> list = checkinoutMapper.findByUserId("100857", stime, etime);
            //DTOUtil.populateList();
            System.out.println();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
