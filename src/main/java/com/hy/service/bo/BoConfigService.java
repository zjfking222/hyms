package com.hy.service.bo;

import com.hy.model.ReportInfo;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 8:37
 * @Description:BO报表权限配置服务方法接口
 */
public interface BoConfigService {
    List<ReportInfo> getReportInfo(int pageNum, int pageSize,String value, String sort, String dir);
    Integer getReportInfoTotal(String value);
    boolean addReportInfo(ReportInfo reportInfos);
    boolean setReportInfo(ReportInfo reportInfo);
    boolean delReportInfo(int id);
}
