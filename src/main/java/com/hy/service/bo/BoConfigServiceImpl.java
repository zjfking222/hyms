package com.hy.service.bo;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.mapper.ms.ReportInfoMapper;
import com.hy.model.ReportInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 8:37
 * @Description:BO报表权限配置服务方法实现
 */

@Service
public class BoConfigServiceImpl implements BoConfigService {
    //报表列表
    @Autowired
    private ReportInfoMapper reportInfoMapper;
    @Override
    public List<ReportInfo> getReportInfo(int pageNum, int pageSize, String value, String sort, String dir){
        PageHelper.startPage(pageNum,pageSize);
        return reportInfoMapper.selectReport(value,sort,dir);
    }
    @Override
    public Integer getReportInfoTotal(String value){
        return reportInfoMapper.selectReportAll(value);
    }

    @Override
    public boolean addReportInfo(ReportInfo reportInfos){
        reportInfos.setCreater(SecurityUtil.getLoginid());
        reportInfos.setModifier(SecurityUtil.getLoginid());
        return reportInfoMapper.insertReport(reportInfos) == 1;
    }
    @Override
    public boolean setReportInfo(ReportInfo reportInfo){
        reportInfo.setModifier(SecurityUtil.getLoginid());
        return reportInfoMapper.updateReport(reportInfo) == 1;
    }
    public boolean delReportInfo(int id){
        return reportInfoMapper.deleteReport(id) == 1;
    }
}
