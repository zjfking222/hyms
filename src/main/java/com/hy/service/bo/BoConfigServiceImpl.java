package com.hy.service.bo;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.config.ldap.LdapUtil;
import com.hy.dto.*;
import com.hy.mapper.ms.ReportAccadRelationMapper;
import com.hy.mapper.ms.ReportAccountMapper;
import com.hy.mapper.ms.ReportInfoMapper;
import com.hy.model.ReportAccadRelation;
import com.hy.model.ReportAccount;
import com.hy.model.ReportInfo;
import com.hy.utils.DTOUtil;
import com.unboundid.ldap.sdk.Filter;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 8:37
 * @Description:BO报表权限配置服务方法实现
 */
@Service
public class BoConfigServiceImpl implements BoConfigService {

    private static final Logger logger = LoggerFactory.getLogger(BoConfigServiceImpl.class);
    @Autowired
    private ReportAccountMapper reportAccount;
    @Autowired
    private ReportAccadRelationMapper reportAccadRelation;
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
    @Override
    public boolean delReportInfo(int id){
        return reportInfoMapper.deleteReport(id) == 1;
    }

    /**
     * @Author 钱敏杰
     * @Description 根据accountid查询BO账号
     * @Date 2018/12/3 16:42
     * @Param [accountid]
     * @return java.util.List<com.hy.dto.ReportAccountDto>
     **/
    @Override
    public List<ReportAccountDto> selectByAccountid(String accountid){
        List<ReportAccount> list = reportAccount.selectByAccountid(accountid);
        return DTOUtil.populateList(list, ReportAccountDto.class);
    }

    /**
     * @Author 钱敏杰
     * @Description 新增BO账号
     * @Date 2018/12/3 17:02
     * @Param [dto]
     * @return com.hy.model.ReportAccount
     **/
    @Override
    public int addAccount(ReportAccountDto dto){
        ReportAccount account = DTOUtil.populate(dto, ReportAccount.class);
        account.setCreater(SecurityUtil.getLoginid());
        account.setModifier(SecurityUtil.getLoginid());
        return reportAccount.insertSelective(account);
    }

    /**
     * @Author 钱敏杰
     * @Description 更新BO账号信息
     * @Date 2018/12/3 17:04
     * @Param [dto]
     * @return int
     **/
    @Override
    public int updateAccount(ReportAccountDto dto){
        ReportAccount account = DTOUtil.populate(dto, ReportAccount.class);
        account.setModifier(SecurityUtil.getLoginid());
        return reportAccount.updateByPrimaryKeySelective(account);
    }

    /**
     * @Author 钱敏杰
     * @Description 根据主键删除BO账号
     * @Date 2018/12/4 10:09
     * @Param [id]
     * @return int
     **/
    public int deleteAccount(Integer id){
        return reportAccount.deleteByPrimaryKey(id);
    }

    /**
     * @Author 钱敏杰
     * @Description 根据BO账号查询AD域员工账号信息
     * @Date 2018/12/6 14:10
     * @Param [accountid]
     * @return java.util.List<com.hy.dto.HrmResourceDto>
     **/
    @Override
    public List<HrmResourceDto> getUsersByAccountid(String accountid){
        List<ReportAccadRelation> list = reportAccadRelation.getListByAccountid(accountid);
        List<HrmResourceDto> resultList = null;
        try {
            //判断是否存在需要查询的员工号
            if(list != null && list.size() >0){
                String filterString = null;
                if(list.size() >1){//根据员工号数组拼接查询语句
                    filterString = "(|";
                    for(int i=0;i<list.size();i++){
                        filterString += "(sAMAccountName=" + list.get(i).getEmpnum() + ")";
                    }
                    filterString += ")";
                }else{
                    filterString = "(sAMAccountName=" + list.get(0).getEmpnum() + ")";
                }
                //查询多个员工号
                Filter filter = Filter.create(filterString);
                SearchResult searchResult = LdapUtil.searchResult(null, null, filter);
                if (null != searchResult && searchResult.getEntryCount() > 0) {
                    resultList = new ArrayList<>();
                    HrmResourceDto dto = null;
                    //生成返回结果
                    for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                        String[] depStr = entry.getAttributeValue("distinguishedName").split("OU=");
                        dto = new HrmResourceDto();
                        dto.setId(Integer.parseInt(entry.getAttributeValue("sAMAccountName")));
                        dto.setLoginid(entry.getAttributeValue("sAMAccountName"));
                        dto.setLastname(entry.getAttributeValue("sn"));
                        resultList.add(dto);
                    }
                }
            }
        } catch (LDAPException e) {
            logger.error("从ad域查询员工信息异常！", e);
        }
        return resultList;
    }

    /**
     * @Author 钱敏杰
     * @Description 保存BO账号与AD域账号的关系数据
     * @Date 2018/12/6 15:23
     * @Param [dto]
     * @return void
     **/
    @Override
    @Transactional
    public void buildRelation(SysRolesUserDto dto) {
        int i = 0;
        //循环执行删除
        for (SysRolesUserDelDto del: dto.getrHrmResources()){
            i = reportAccadRelation.deleteByAccEmp(dto.getName(), del.getUid());
            if(i <= 0){
                throw new RuntimeException("删除BO账号与AD域账号关联数据失败！");
            }
        }
        //新增
        List<ReportAccadRelation> addList = null;
        if(dto.getnHrmResources() != null && dto.getnHrmResources().length >0){
            addList = new ArrayList<>();
            ReportAccadRelation relation = null;
            for (HrmResourceDto add: dto.getnHrmResources()){
                relation = new ReportAccadRelation();
                relation.setAccountid(dto.getName());
                relation.setCreater(SecurityUtil.getLoginid());
                relation.setEmpnum(add.getLoginid());
                relation.setModifier(SecurityUtil.getLoginid());
                addList.add(relation);
            }
            i = reportAccadRelation.insertBatch(addList);
            if(i < addList.size()){
                throw new RuntimeException("新增BO账号与AD域账号关联数据失败！");
            }
        }
    }
}
