package com.hy.service.bo;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.config.ldap.LdapUtil;
import com.hy.dto.*;
import com.hy.mapper.ms.*;
import com.hy.model.*;
import com.hy.utils.DTOUtil;
import com.unboundid.ldap.sdk.Filter;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 8:37
 * @Description:BO报表权限配置服务方法实现
 */
@Service
public class BoConfigServiceImpl implements BoConfigService {

    private static final Logger logger = LoggerFactory.getLogger(BoConfigServiceImpl.class);
    @Autowired
    private BOAccountMapper reportAccount;
    @Autowired
    private BOAccadRelationMapper reportAccadRelation;
    //报表列表
    @Autowired
    private BOInfoMapper reportInfoMapper;
    @Autowired
    private BOCatalogueMapper reportCatalogueMapper;
    @Autowired
    private BOAccInfoMapper reportAccInfoMapper;
    @Autowired
    private BOPermissionMapper reportPermissionMapper;
    @Autowired
    private BORoleMapper boRoleMapper;
    @Autowired
    private BORoleAdMapper boRoleAdMapper;
    @Autowired
    private BORoleAccountMapper boRoleAccountMapper;
    @Autowired
    private BORoleReportMapper boRoleReportMapper;

    @Override
    public List<BOInfo> getReportInfo(String filters, int pageNum, int pageSize, String value, String sort, String dir, String directoryid){
        PageHelper.startPage(pageNum,pageSize);
        return reportInfoMapper.selectReport(filters, value,sort,dir,directoryid);
    }

    @Override
    public Integer getReportInfoTotal(String filters, String value, String directoryid){
        return reportInfoMapper.selectReportAll(filters, value, directoryid);
    }

    @Override
    public boolean addReportInfo(BOInfo reportInfos) {
        reportInfos.setCreater(SecurityUtil.getLoginid());
        reportInfos.setModifier(SecurityUtil.getLoginid());
        return reportInfoMapper.insertReport(reportInfos) == 1;
    }

    @Override
    public boolean setReportInfo(BOInfo reportInfo) {
        reportInfo.setModifier(SecurityUtil.getLoginid());
        return reportInfoMapper.updateReport(reportInfo) == 1;
    }

    @Override
    public boolean delReportInfo(int id) {
        return reportInfoMapper.deleteReport(id) == 1;
    }

    /**
     * @Author 钱敏杰
     * @Description 删除报表及其所有关联数据
     * @Date 2018/12/24 17:14
     * @Param [reportid]
     * @return void
     **/
    @Override
    @Transactional
    public void delReportInfo(String reportid) {
        //删除报表数据
        int i = reportInfoMapper.deleteReportByrid(reportid);
        if(i >0){
            //删除当前报表的BO账号与报表的关联关系数据
            i = reportAccInfoMapper.deleteByReportid(reportid);
            if(i >=0){
                //删除当前报表的角色与报表的关联关系数据
                i = boRoleReportMapper.deleteByReportid(reportid);
                if(i >=0){
                    //删除当前报表的人员与报表的关联关系数据
                    i = reportPermissionMapper.deleteByReportid(reportid);
                    if(i <0){
                        throw new RuntimeException("删除当前报表的人员与报表的关联关系数据失败");
                    }
                }else{
                    throw new RuntimeException("删除当前报表的角色与报表的关联关系数据失败");
                }
            }else{
                throw new RuntimeException("删除当前报表的BO账号与报表的关联关系数据失败");
            }
        }else{
            throw new RuntimeException("删除报表数据失败");
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 根据accountid查询BO账号
     * @Date 2018/12/3 16:42
     * @Param [accountid]
     * @return java.util.List<com.hy.dto.ReportAccountDto>
     **/
    @Override
    public List<BOAccountDto> selectByAccountid(String accountid) {
        List<BOAccount> list = reportAccount.selectByAccountid(accountid);
        return DTOUtil.populateList(list, BOAccountDto.class);
    }

    /**
     * @return com.hy.model.ReportAccount
     * @Author 钱敏杰
     * @Description 新增BO账号
     * @Date 2018/12/3 17:02
     * @Param [dto]
     * @return com.hy.model.ReportAccount
     **/
    @Override
    public int addAccount(BOAccountDto dto) {
        BOAccount account = DTOUtil.populate(dto, BOAccount.class);
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
    public int updateAccount(BOAccountDto dto) {
        BOAccount account = DTOUtil.populate(dto, BOAccount.class);
        account.setModifier(SecurityUtil.getLoginid());
        return reportAccount.updateByPrimaryKeySelective(account);
    }

    /**
     * @Author 钱敏杰
     * @Description 根据主键删除BO账号
     * @Date 2018/12/12 10:23
     * @Param [id]
     * @return void
     **/
    @Override
    @Transactional
    public void deleteAccount(String id) {
        int rid = Integer.parseInt(id);
        BOAccount account = reportAccount.selectByPrimaryKey(rid);
        //删除BO账号与员工的关联关系数据
        int i = reportAccadRelation.deleteByAccountid(account.getAccountid());
        if (i < 0) {
            throw new RuntimeException("删除BO账号与所有AD域账号关联数据失败！");
        }
        //删除BO账号下配置的报表关联关系数据
        i = reportAccInfoMapper.deleteByAccountid(account.getAccountid());
        if (i < 0) {
            throw new RuntimeException("删除BO账号与所有报表的关联数据失败！");
        }
        //删除BO账号数据
        i = reportAccount.deleteByPrimaryKey(rid);
        if (i <= 0) {
            throw new RuntimeException("删除BO账号失败！");
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 根据BO账号查询AD域员工账号信息
     * @Date 2018/12/6 14:10
     * @Param [accountid]
     * @return java.util.List<com.hy.dto.HrmResourceDto>
     **/
    @Override
    public List<HrmResourceDto> getUsersByAccountid(String filters, String accountid) {
        List<BOAccadRelation> list = reportAccadRelation.getListByAccountid(filters, accountid);
        List<HrmResourceDto> resultList = null;
        try {
            //判断是否存在需要查询的员工号
            if (list != null && list.size() > 0) {
                String filterString = null;
                if (list.size() > 1) {//根据员工号数组拼接查询语句
                    filterString = "(|";
                    for (int i = 0; i < list.size(); i++) {
                        filterString += "(sAMAccountName=" + list.get(i).getEmpnum() + ")";
                    }
                    filterString += ")";
                } else {
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
        for (SysRolesUserDelDto del : dto.getrHrmResources()) {
            i = reportAccadRelation.deleteByAccEmp(dto.getName(), del.getUid());
            if (i <= 0) {
                throw new RuntimeException("删除BO账号与AD域账号关联数据失败！");
            }
            i = reportPermissionMapper.deleteByEmp(del.getUid());
            if (i < 0) {//可能为0，未配置报表
                throw new RuntimeException("删除BO账号下该员工所有已配置报表的关联数据失败！");
            }
        }
        //新增
        List<BOAccadRelation> addList = null;
        if (dto.getnHrmResources() != null && dto.getnHrmResources().length > 0) {
            addList = new ArrayList<>();
            BOAccadRelation relation = null;
            for (HrmResourceDto add : dto.getnHrmResources()) {
                if (StringUtils.isNotEmpty(add.getLoginid()) && StringUtils.isNotEmpty(dto.getName())) {
                    relation = new BOAccadRelation();
                    relation.setAccountid(dto.getName());
                    relation.setCreater(SecurityUtil.getLoginid());
                    relation.setEmpnum(add.getLoginid());
                    relation.setEmpname(add.getLastname());
                    relation.setModifier(SecurityUtil.getLoginid());
                    addList.add(relation);
                }
            }
            i = reportAccadRelation.insertBatch(addList);
            if (i < addList.size()) {
                throw new RuntimeException("新增BO账号与AD域账号关联数据失败！");
            }
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 从BO账号中删除该人员
     * @Date 2018/12/19 16:16
     * @Param [accountid, empnum]
     * @return void
     **/
    @Override
    @Transactional
    public void deleteEmpAccount(String accountid, String empnum){
        int i = reportAccadRelation.deleteByAccEmp(accountid, empnum);
        if (i <= 0) {
            throw new RuntimeException("删除BO账号与AD域账号关联数据失败！");
        }
        i = reportPermissionMapper.deleteByEmp(empnum);
        if (i < 0) {//可能为0，未配置报表
            throw new RuntimeException("删除BO账号下该员工所有已配置报表的关联数据失败！");
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 获取全部目录及报表树，以及当前BO账号选中的报表
     * @Date 2018/12/10 14:21
     * @Param [accountid]
     * @return java.util.List<com.hy.dto.ReportCatalogueDto>
     **/
    @Override
    public List<BOCatalogueDto> getAllReportTree(String accountid) {
        Map<Integer, BOCatalogueDto> cataMap = null;
        //查询出全部目录结构
        List<BOCatalogue> list = reportCatalogueMapper.selectAll();
        //查询出全部报表信息
        List<BOInfo> rList = reportInfoMapper.selectReport(null, null, null, null, null);
        //循环目录结构，生成以id为key的map结构
        if (list != null && list.size() > 0 && rList != null && rList.size() > 0) {
            List<BOCatalogueDto> catalogues = DTOUtil.populateList(list, BOCatalogueDto.class);
            //移除空目录
            removeNullNode(catalogues, rList);
            //已选中的数据
            List<BOInfo> checked = this.getReportInfoByAcc(accountid);
            //整理树的结构
            cataMap = this.arrageCatalogue(catalogues, rList, checked);
        }
        List<BOCatalogueDto> returnList = new ArrayList(Arrays.asList(cataMap.values().toArray()));
        return returnList;
    }

    /**
     * @Author 钱敏杰
     * @Description 根据BO账户获取关联的BO报表
     * @Date 2018/12/8 15:08
     * @Param [accountid]
     * @return java.util.List<com.hy.dto.ReportInfoDto>
     **/
    @Override
    public List<BOInfo> getReportInfoByAcc(String accountid) {
        List<BOInfo> list = reportInfoMapper.selectByAccountid(accountid);
        return list;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前BO账号下的目录及报表树，以及当前员工选中的报表
     * @Date 2018/12/10 11:48
     * @Param [accountid]
     * @return java.util.List<com.hy.dto.ReportCatalogueDto>
     **/
    @Override
    public List<BOCatalogueDto> getAccReportTree(String accountid, String empnum) {
        Map<Integer, BOCatalogueDto> cataMap = null;
        //查询出全部目录结构
        List<BOCatalogue> list = reportCatalogueMapper.selectAll();
        //查询出当前BO账号下的全部报表信息
        List<BOInfo> rList = reportInfoMapper.selectByAccountid(accountid);
        //循环目录结构，生成以id为key的map结构
        if (list != null && list.size() > 0 && rList != null && rList.size() > 0) {
            List<BOCatalogueDto> catalogues = DTOUtil.populateList(list, BOCatalogueDto.class);
            //移除空目录
            removeNullNode(catalogues, rList);
            //已选中的数据
            List<BOInfo> checked = this.getReportInfoByEmp(empnum);
            //整理树的结构
            cataMap = this.arrageCatalogue(catalogues, rList, checked);
        }
        List<BOCatalogueDto> returnList = new ArrayList(Arrays.asList(cataMap.values().toArray()));
        return returnList;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前人员的全部BO报表目录
     * @Date 2018/12/26 8:43
     * @Param [empnum]
     * @return java.util.List<com.hy.dto.BOCatalogueDto>
     **/
    @Override
    public List<BOCatalogueDto> getCatalogueByEmp(String empnum){
        Map<Integer, BOCatalogueDto> cataMap = null;
        //查询出全部目录结构
        List<BOCatalogue> list = reportCatalogueMapper.selectAll();
        //查询出当前人员拥有的BO账号下的全部报表信息
        List<BOInfo> aList = reportInfoMapper.selectAllByEmpAcc(empnum);
        //查询出当前人员拥有的角色下所有的报表信息
        List<BOInfo> rList = reportInfoMapper.selectAllByEmpRole(empnum);
        //合并报表
        List<BOInfo> allList = new ArrayList<>();
        if(aList != null && aList.size() >0){
            allList.addAll(aList);
        }
        if(rList != null && rList.size() >0){
            allList.addAll(rList);
        }
        //整理目录，剔除不含有报表的目录
        if (list != null && list.size() > 0 && allList.size() > 0) {
            List<BOCatalogueDto> catalogues = DTOUtil.populateList(list, BOCatalogueDto.class);
            //移除空目录
            this.removeNullNode(catalogues, allList);
            //整理树的结构
            //list转map
            cataMap = catalogues.stream().collect(Collectors.toMap(BOCatalogueDto::getId, a -> a, (k1, k2) -> k1));
            this.setListToTree(cataMap, catalogues);
        }
        //返回目录树
        List<BOCatalogueDto> returnList = new ArrayList(Arrays.asList(cataMap.values().toArray()));
        return returnList;
    }

    /**
     * @Author 钱敏杰
     * @Description 根据员工号获取关联的BO报表
     * @Date 2018/12/10 14:16
     * @Param [empnum]
     * @return java.util.List<com.hy.model.ReportInfo>
     **/
    @Override
    public List<BOInfo> getReportInfoByEmp(String empnum){
        List<BOInfo> list = reportInfoMapper.selectOwnByEmp(empnum);
        return list;
    }

    /**
     * @Author 钱敏杰
     * @Description 新增或删除BO账号与报表的关联数据
     * @Date 2018/12/11 9:07
     * @Param [catalogueDto]
     * @return void
     **/
    @Override
    @Transactional
    public void saveAccountReport(BOCatalogueDto catalogueDto) {
        if (catalogueDto != null) {
            int i;
            if (catalogueDto.getDelReports() != null && catalogueDto.getDelReports().length > 0) {
                //存在需要删除的数据，则执行删除操作
                for (String reportid : catalogueDto.getDelReports()) {
                    i = reportAccInfoMapper.deleteByAccReport(catalogueDto.getAccountid(), reportid);
                    if (i <= 0) {
                        throw new RuntimeException("删除BO账号与报表关联数据失败！");
                    }
                }
            }
            if (catalogueDto.getAddReports() != null && catalogueDto.getAddReports().length > 0) {
                //存在需要新增的数据，则执行新增操作
                List<BOAccInfo> addList = new ArrayList<>();
                BOAccInfo accInfo = null;
                for (String reportid : catalogueDto.getAddReports()) {
                    if (StringUtils.isNotEmpty(reportid) && StringUtils.isNotEmpty(catalogueDto.getAccountid())) {
                        accInfo = new BOAccInfo();
                        accInfo.setAccountid(catalogueDto.getAccountid());
                        accInfo.setReportid(reportid);
                        accInfo.setCreater(SecurityUtil.getLoginid());
                        accInfo.setModifier(SecurityUtil.getLoginid());
                        addList.add(accInfo);
                    }
                }
                i = reportAccInfoMapper.insertAccReportBatch(addList);
                if (i < addList.size()) {
                    throw new RuntimeException("新增BO账号与报表关联数据失败！");
                }
            }
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 新增或删除员工号与报表的关联数据（员工权限与BO账号无关）
     * @Date 2018/12/11 10:38
     * @Param [catalogueDto]
     * @return void
     **/
    @Override
    @Transactional
    public void saveEmpReport(BOCatalogueDto catalogueDto) {
        if (catalogueDto != null) {
            int i;
            if (catalogueDto.getDelReports() != null && catalogueDto.getDelReports().length > 0) {
                //存在需要删除的数据，则执行删除操作
                for (String reportid : catalogueDto.getDelReports()) {
                    i = reportPermissionMapper.deleteByEmpReport(catalogueDto.getEmpnum(), reportid);
                    if (i <= 0) {
                        throw new RuntimeException("删除员工与报表关联数据失败！");
                    }
                }
            }
            if (catalogueDto.getAddReports() != null && catalogueDto.getAddReports().length > 0) {
                //存在需要新增的数据，则执行新增操作
                List<BOPermission> addList = new ArrayList<>();
                BOPermission permission = null;
                for (String reportid : catalogueDto.getAddReports()) {
                    if (StringUtils.isNotEmpty(reportid) && StringUtils.isNotEmpty(catalogueDto.getEmpnum())) {
                        permission = new BOPermission();
                        permission.setEmpnum(catalogueDto.getEmpnum());
                        permission.setReportid(reportid);
                        permission.setCreater(SecurityUtil.getLoginid());
                        permission.setModifier(SecurityUtil.getLoginid());
                        addList.add(permission);
                    }
                }
                i = reportPermissionMapper.insertEmpReportBatch(addList);
                if (i < addList.size()) {
                    throw new RuntimeException("新增BO账号与报表关联数据失败！");
                }
            }
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 根据员工号或员工姓名查询所有已关联的员工信息
     * @Date 2018/12/12 14:22
     * @Param [empnum]
     * @return java.util.List<com.hy.dto.ReportAccadRelationDto>
     **/
    @Override
    public List<BOAccadRelationDto> getAccountEmp(String empnum) {
        List<BOAccadRelation> list = reportAccadRelation.getAccountEmp(empnum);
        return DTOUtil.populateList(list, BOAccadRelationDto.class);
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前员工绑定的BO账号id数据
     * @Date 2018/12/20 14:01
     * @Param [empnum]
     * @return java.util.List<java.lang.String>
     **/
    @Override
    public List<String> getEmpAccounts(String empnum){
        List<BOAccadRelation> list = reportAccadRelation.getEmpAccounts(empnum);
        List<String> returnList = null;
        if(list != null && list.size() >0){
            returnList = new ArrayList<>();
            for(BOAccadRelation relation:list){
                if(StringUtils.isNotEmpty(relation.getAccountid())){
                    returnList.add(relation.getAccountid());
                }
            }
        }
        return returnList;
    }

    /**
     * @Author 钱敏杰
     * @Description 保存人员拥有的BO账号权限变化
     * @Date 2018/12/20 16:05
     * @Param [dto]
     * @return void
     **/
    @Override
    @Transactional
    public void saveEmpAccounts(BOEmpAccountDto dto){
        if(dto != null && StringUtils.isNotEmpty(dto.getEmpnum())){
            //检查是否存在新增的BO账号权限，有则保存
            if(dto.getAddAccounts() != null && dto.getAddAccounts().size() >0){
                List<BOAccadRelation> addList = new ArrayList<>();
                BOAccadRelation relation = null;
                //生成保存对象
                for(String accountid:dto.getAddAccounts()){
                    if(StringUtils.isNotEmpty(accountid)){
                        relation = new BOAccadRelation();
                        relation.setAccountid(accountid);
                        relation.setEmpnum(dto.getEmpnum());
                        relation.setEmpname(dto.getEmpname());
                        relation.setCreater(SecurityUtil.getLoginid());
                        relation.setModifier(SecurityUtil.getLoginid());
                        addList.add(relation);
                    }
                }
                int i = reportAccadRelation.insertBatch(addList);
                if(i <= 0){
                    throw new RuntimeException("保存人员账号权限数据失败");
                }
            }
            //检查是否有删除的BO账号权限，有则删除
            if(dto.getDelAccounts() != null && dto.getDelAccounts().size() >0){
                //循环执行删除操作
                for(String accountid:dto.getDelAccounts()){
                    int i = reportAccadRelation.deleteByAccEmp(accountid, dto.getEmpnum());
                    if(i <= 0){
                        throw new RuntimeException("删除人员账号权限数据失败");
                    }
                }
            }
        }else{
            throw new RuntimeException("数据为空，无法执行");
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 删除BO操作人员及其相关权限
     * @Date 2018/12/20 17:09
     * @Param [empnum]
     * @return void
     **/
    @Override
    @Transactional
    public void deleteEmp(String empnum){
        if(StringUtils.isNotEmpty(empnum)){
            int i = reportAccadRelation.deleteByEmp(empnum);
            if(i <= 0){
                throw new RuntimeException("删除人员全部账号权限数据失败");
            }
            i = reportPermissionMapper.deleteAllByEmp(empnum);
            if(i < 0){
                throw new RuntimeException("删除人员全部报表权限数据失败");
            }
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 查询当前员工所有BO账号下的报表权限树及其选中值
     * @Date 2018/12/12 16:02
     * @Param [empnum]
     * @return java.util.List<com.hy.dto.ReportCatalogueDto>
     **/
    @Override
    public List<BOCatalogueDto> getReportTreeByEmp(String empnum){
        List<BOCatalogueDto> returnList = null;
        //查询出全部目录结构
        List<BOCatalogue> list = reportCatalogueMapper.selectAll();
        //查询出当前人员的BO账号下的全部报表信息
        List<BOInfo> rList = reportInfoMapper.selectAllByEmpAcc(empnum);
        //循环目录结构，生成以id为key的map结构
        if (list != null && list.size() > 0 && rList != null && rList.size() > 0) {
            List<BOCatalogueDto> catalogues = DTOUtil.populateList(list, BOCatalogueDto.class);
            //移除空目录
            removeNullNode(catalogues, rList);
            //已选中的数据
            List<BOInfo> checked = reportInfoMapper.selectOwnByEmp(empnum);
            //整理树的结构
            Map<Integer, BOCatalogueDto> cataMap = this.arrageCatalogue(catalogues, rList, checked);
            returnList = new ArrayList(Arrays.asList(cataMap.values().toArray()));
        }
        return returnList;
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前BO账号下的人员数量
     * @Date 2018/12/13 17:16
     * @Param [accountid]
     * @return int
     **/
    @Override
    public int getAccEmpCount(String accountid){
        List<BOAccadRelation> list = reportAccadRelation.getListByAccountid(null, accountid);
        if(list != null){
            return list.size();
        }else{
            return 0;
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 获取全部报表目录
     * @Date 2018/12/14 9:51
     * @Param []
     * @return java.util.List<com.hy.dto.BOCatalogueDto>
     **/
    @Override
    public List<BOCatalogueDto> getAllCatalogue(){
        List<BOCatalogueDto> returnList = null;
        //查询出全部目录结构
        List<BOCatalogue> list = reportCatalogueMapper.selectAll();
        if(list != null && list.size() >0) {
            List<BOCatalogueDto> catalogues = DTOUtil.populateList(list, BOCatalogueDto.class);
            //list转map
            Map<Integer, BOCatalogueDto> cataMap = catalogues.stream().collect(Collectors.toMap(BOCatalogueDto:: getId, a ->a, (k1, k2)->k1));
            //形成树结构
            this.setListToTree(cataMap, catalogues);
            returnList = new ArrayList(Arrays.asList(cataMap.values().toArray()));
        }
        return returnList;
    }

    /**
     * @Author 钱敏杰
     * @Description 新增或更新目录
     * @Date 2018/12/17 9:35
     * @Param [dto]
     * @return int
     **/
    @Override
    public int updateCatalogue(BOCatalogueDto dto){
        BOCatalogue catalogue = DTOUtil.populate(dto, BOCatalogue.class);
        catalogue.setModifier(SecurityUtil.getLoginid());
        //判断是新增还是执行更新
        if(catalogue.getId() == null){
            catalogue.setCreater(SecurityUtil.getLoginid());
            //调用新增操作
            int i = reportCatalogueMapper.insertSelective(catalogue);
            if(i <= 0){
                throw new RuntimeException("添加目录数据失败！");
            }
            //返回主键
            return catalogue.getId();
        }else{
            //调用更新操作
            int i = reportCatalogueMapper.updateByPrimaryKeySelective(catalogue);
            if(i <= 0){
                throw new RuntimeException("更新目录数据失败！");
            }
            //返回主键
            return catalogue.getId();
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 删除目录
     * @Date 2018/12/17 16:03
     * @Param [id]
     * @return void
     **/
    @Override
    public void deleteCatalogue(String id){
        if(StringUtils.isNotEmpty(id)){
            Integer rid = Integer.parseInt(id);
            int i = reportCatalogueMapper.deleteByPrimaryKey(rid);
            if(i <= 0){
                throw new RuntimeException("删除目录数据失败！");
            }
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前报表id的报表数量
     * @Date 2018/12/18 14:19
     * @Param [reportid]
     * @return java.lang.Integer
     **/
    @Override
    public Integer getInfoCount(String reportid){
        Integer num = reportInfoMapper.countInfoByReportid(reportid);
        return num;
    }

    /**
     * @Author 沈超宇
     * @Description 角色查、删serviceImpl
     * @Date 2018/12/13 10:46
     **/
    @Override
    public List<BORoleDto> getRole(String value) {
        List<BORole> boRolesList = boRoleMapper.selectRole(value);
        return DTOUtil.populateList(boRolesList, BORoleDto.class);
    }

    /**
     * @Author 沈超宇
     * @Description 根据BO账号查询对应的角色信息serviceImpl
     * @Date 2018/12/19 16:35
     **/
    @Override
    public List<BORoleDto> getRoleByAcc(String filters, String accountid){
        List<BORole> boRolesList = boRoleMapper.selectRoleByAcc(filters, accountid);
        return DTOUtil.populateList(boRolesList, BORoleDto.class);
    }

    @Override
    @Transactional
    //删除角色时，若存在人员，不能删除,无人员时，删除角色、角色BO账号关联表、角色报表关联表相关数据
    public boolean delRole(int id) {
        int i = boRoleMapper.deleteRole(id);
        if(i > 0){
            boRoleAccountMapper.deleteRoleAccountAll(id);
            boRoleReportMapper.deleteByRole(id);
            return true;
        }else {
            throw new RuntimeException("删除角色失败！");
        }
    }

    /**
     * @Author 沈超宇
     * @Description 角色、AD员工号关系表增删改查(删包含在改中)，以及角色增、改,角色BO账号增删serviceImpl
     * @Date 2018/12/13 17:49
     **/
    @Override
    public List<HrmResourceDto> getRoleAd(int rid) {
        List<BORoleAd> boRoleAdsList = boRoleAdMapper.selectRoleAd(rid);
        List<HrmResourceDto> result = new ArrayList<>();
        if (boRoleAdsList.size() != 0) {
            for (int i = 0; i < boRoleAdsList.size(); i++) {
                HrmResourceDto dto = new HrmResourceDto();
                dto.setId(boRoleAdsList.get(i).getId());
                dto.setLoginid(boRoleAdsList.get(i).getEmpnum());
                dto.setLastname(boRoleAdsList.get(i).getEmpname());
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRoleAd(SysRolesUserDto dto) {
        try {
            //新增角色名称、描述
            BORole boRole = new BORole();
            boRole.setName(dto.getName());
            boRole.setDescription(dto.getDescription());
            boRole.setCreater(SecurityUtil.getLoginid());
            boRole.setModifier(SecurityUtil.getLoginid());
            int i = boRoleMapper.insertRole(boRole);
            if(i > 0){
                List<BORoleAccount> boRoleAccountList = new ArrayList<>();
                List<BORoleAd> boRoleAds = new ArrayList<>();
                //添加BO账号(存入List)
                for (BORoleAccountDto boRoleAccountDto : dto.getBoRoleAccounts()) {
                    boRoleAccountList.add(new BORoleAccount(boRole.getId(), boRoleAccountDto.getAccountid(),
                            SecurityUtil.getLoginid(), SecurityUtil.getLoginid()));
                }
                if(boRoleAccountList.size() > 0){
                    int addRa = boRoleAccountMapper.insertRoleAccount(boRoleAccountList);
                    if(addRa <= 0){
                        throw new RuntimeException("添加BO账号失败！");
                    }
                }
                //添加人员ad账号(存入List)
                for (HrmResourceDto hrmResourceDto : dto.getnHrmResources()) {
                    boRoleAds.add(new BORoleAd(boRole.getId(), hrmResourceDto.getLoginid(), hrmResourceDto.getLastname(),
                            SecurityUtil.getLoginid(), SecurityUtil.getLoginid()));
                }
                if(boRoleAds.size() > 0){
                    int addRad = boRoleAdMapper.insertRoleAd(boRoleAds);
                    if(addRad <= 0){
                        throw new RuntimeException("添加人员ad账号失败！");
                    }
                }
                return true;
            }else {
                throw new RuntimeException("新增角色失败！");
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setRoleAd(SysRolesUserDto dto) {
        try {
            //修改角色名称、描述
            BORole boRole = new BORole();
            boRole.setId(dto.getRid());
            boRole.setName(dto.getName());
            boRole.setDescription(dto.getDescription());
            boRole.setModifier(SecurityUtil.getLoginid());
            if(!boRole.getName().equals("") || !boRole.getDescription().equals("")){
                int update = boRoleMapper.updateRole(boRole);
                if(update <= 0){
                    throw new RuntimeException("更新角色信息失败！");
                }
            }
            List<BORoleAccount> boRoleAccountList = boRoleAccountMapper.selectRoleAccount(dto.getRid());
            Map<String, String> map = new HashMap<>();
            List<BORoleAccount> boRoleAccounts = new ArrayList<>();
            List<BORoleAd> boRoleAds = new ArrayList<>();
            boolean judgeAdd = false;
            int size = boRoleAccountList.size();
            //添加BO账号(存入List)
            //将开始选中的BO账号与最后的比较，找出要添加的BO账号
            IntStream.range(0, size).forEach(i ->{
                map.put(boRoleAccountList.get(i).getAccountid(), boRoleAccountList.get(i).getAccountid());
            });
            if (dto.getBoRoleAccounts().length > 0) {
                for (BORoleAccountDto accountAddDto : dto.getBoRoleAccounts()) {
                    if (size > 0) {
                        if (map.containsKey(accountAddDto.getAccountid())) {
                            judgeAdd = true;
                        }
                    }
                    if (!judgeAdd) {
                        boRoleAccounts.add(new BORoleAccount(accountAddDto.getRid(), accountAddDto.getAccountid(),
                                SecurityUtil.getLoginid(), SecurityUtil.getLoginid()));
                    }
                    judgeAdd = false;
                }
            }
            if(boRoleAccounts.size() > 0){
                int addRa = boRoleAccountMapper.insertRoleAccount(boRoleAccounts);
                if(addRa <= 0){
                    throw new RuntimeException("新增角色BO账号失败！");
                }
            }
            //删除BO账号
            if(dto.getDelAcc().length > 0){
                int delRa = boRoleAccountMapper.deleteRoleAccount(dto.getDelAcc());
                if(delRa <= 0){
                    throw new RuntimeException("删除角色BO账号失败！");
                }
                //删除当前BO账号下的当前角色已配置的报表(删除BO账号后该账号下的部分已配置的报表权限且不在其他角色所拥有的BO账号下的权限应删除)
                int delRr = boRoleReportMapper.deleteReportByRole(dto.getRid());
                if(delRr < 0){
                    throw new RuntimeException("删除删除当前BO账号下的当前角色已配置的报表失败！");
                }
            }
            //删除人员ad账号
            if(dto.getrHrmResources().length > 0){
                int[] array = new int[dto.getrHrmResources().length];
                int x = 0;
                for (SysRolesUserDelDto delDto : dto.getrHrmResources()) {
                    array[x] = Integer.parseInt(delDto.getUid());
                    x++;
                }
                int delRad = boRoleAdMapper.deleteRoleAd(array);
                if(delRad <= 0){
                    throw new RuntimeException("删除人员ad账号失败！");
                }
            }
            //添加人员ad账号(存入List)
            if(dto.getnHrmResources().length > 0){
                for (HrmResourceDto hrmResourceDto : dto.getnHrmResources()) {
                    boRoleAds.add(new BORoleAd(dto.getRid(), hrmResourceDto.getLoginid(), hrmResourceDto.getLastname(),
                            SecurityUtil.getLoginid(), SecurityUtil.getLoginid()));
                }
            }
            if(boRoleAds.size() > 0){
                int addRad = boRoleAdMapper.insertRoleAd(boRoleAds);
                if(addRad <= 0){
                    throw new RuntimeException("添加人员ad账号失败！");
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }

    }

    /**
     * @Author 沈超宇
     * @Description 角色、BO账号关系表增删查serviceImpl
     * @Date 2018/12/15
     **/
    @Override
    public List<BORoleAccountDto> getRoleAccount(int rid) {
        List<BORoleAccount> boRoleAccountList = boRoleAccountMapper.selectRoleAccount(rid);
        return DTOUtil.populateList(boRoleAccountList, BORoleAccountDto.class);
    }

    /**
     * @Author 沈超宇
     * @Description 角色、BO账号关系表根据角色BO账号删除对应数据serviceImpl
     * @Date 2018/12/19 17:04
     **/
    @Override
    @Transactional
    public boolean delByRidAcc(int rid, String accountid){
        int i = boRoleAccountMapper.deleteByRidAcc(rid, accountid);
        if(i >0){
            //删除当前BO账号下的当前角色已配置的报表(删除BO账号后该账号下的部分已配置的报表权限且不在其他角色所拥有的BO账号下的权限应删除)
            i = boRoleReportMapper.deleteReportByRole(rid);
        }else{
            throw new RuntimeException("删除角色BO账号关联数据失败");
        }
        if(i >= 0){
            return true;
        }else{
            throw new RuntimeException("删除角色已配置权限数据失败");
        }
    }

    /**
     * @Author 沈超宇
     * @Description 角色、报表关系表增删查serviceImpl
     * @Date 2018/12/17 10:33
     **/
    //查询并生成树
    @Override
    public List<BOCatalogueDto> getRoleReportTree(int rid) {
        Map<Integer, BOCatalogueDto> cataMap = null;
        List<BOCatalogueDto> returnList = null;
        //查询全部目录结构
        List<BOCatalogue> catalogues = reportCatalogueMapper.selectAll();
        //查询当前BO账号下的全部报表信息
        List<BOInfo> boInfos = reportInfoMapper.selectAllByRole(rid);
        //循环目录结构，生成以id为key的map结构
        if (catalogues != null && catalogues.size() > 0 && boInfos != null && boInfos.size() > 0) {
            List<BOCatalogueDto> catalogueDtoList = DTOUtil.populateList(catalogues, BOCatalogueDto.class);
            //移除空目录
            removeNullNode(catalogueDtoList, boInfos);
            //已选中的数据
            List<BOInfo> checked = this.getReportInfoByRid(rid);
            //整理树结构
            cataMap = this.arrageCatalogue(catalogueDtoList, boInfos, checked);
        }
        if(cataMap != null){
            returnList= new ArrayList(Arrays.asList(cataMap.values().toArray()));
        }
        return returnList;
    }

    //增删角色报表
    @Override
    @Transactional
    public void saveRoleReport(BOCatalogueDto boCatalogueDto) {
        if (boCatalogueDto != null) {
            int i;
            if (boCatalogueDto.getDelReports() != null && boCatalogueDto.getDelReports().length > 0) {
                //存在需要删除的数据，则执行删除操作
                for (String reportid : boCatalogueDto.getDelReports()) {
                    i = boRoleReportMapper.deleteByRoleReport(boCatalogueDto.getRid(), reportid);
                    if (i < 0) {
                        throw new RuntimeException("删除角色与报表相关数据失败！");
                    }
                }
            }
            if (boCatalogueDto.getAddReports() != null && boCatalogueDto.getAddReports().length > 0) {
                //存在需要新增的数据，则执行新增操作
                List<BORoleReport> addList = new ArrayList<>();
                BORoleReport boRoleReport = null;
                for (String reportid : boCatalogueDto.getAddReports()) {
                    if (StringUtils.isNotEmpty(reportid)) {
                        boRoleReport = new BORoleReport();
                        boRoleReport.setRid(boCatalogueDto.getRid());
                        boRoleReport.setReportid(reportid);
                        boRoleReport.setCreater(SecurityUtil.getLoginid());
                        boRoleReport.setModifier(SecurityUtil.getLoginid());
                        addList.add(boRoleReport);
                    }
                }
                i = boRoleReportMapper.insertRoleReport(addList);
                if (i < addList.size()) {
                    throw new RuntimeException("新增角色与报表关联数据失败");
                }
            }
        }
    }

    /**
     * @Author 沈超宇
     * @Description 查询当前角色所拥有的报表serviceImpl
     * @Date 2018/12/17 11:10
     **/
    @Override
    public List<BOInfo> getReportInfoByRid(int rid) {
        return reportInfoMapper.selectOwnByRole(rid);
    }

    /**
     * @Author 钱敏杰
     * @Description 删除空节点目录
     * @Date 2018/12/10 9:15
     * @Param [catalogues, rList]
     * @return void
     **/
    private void removeNullNode(List<BOCatalogueDto> catalogues, List<BOInfo> rList) {
        //存放需保留的id
        Map<Integer, Boolean> keepMap = new HashMap<>();
        //list转map
        Map<Integer, BOCatalogueDto> cataMap = catalogues.stream().collect(Collectors.toMap(BOCatalogueDto::getId, a -> a, (k1, k2) -> k1));
        for (BOInfo info : rList) {
            //取出报表数据中的目录id
            Integer dirid = info.getDirectoryid();
            keepMap.put(dirid, true);
            BOCatalogueDto catalogue = null;
            int i = 0;
            while (true) {
                //取出当前目录节点数据
                catalogue = cataMap.get(dirid);
                if (catalogue != null) {
                    if (catalogue.getPid() != null) {
                        //若当前节点存在父节点，则需要保留此父节点
                        dirid = catalogue.getPid();
                        keepMap.put(dirid, true);
                        i++;
                    } else {//不存在父节点，则循环结束
                        if (i > 0) {
                            keepMap.put(dirid, true);
                        }
                        break;
                    }
                }else{
                    break;
                }
            }
        }
        //从列表catalogues中删除不需要保留的数据
        Iterator<BOCatalogueDto> iter = catalogues.iterator();
        while (iter.hasNext()) {
            BOCatalogueDto catalogueDto = iter.next();
            if (!keepMap.containsKey(catalogueDto.getId())) {
                iter.remove();
            }
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 整理当前目录与报表数据，合并成树结构map，并判断当前数据是否选中
     * @Date 2018/12/10 14:08
     * @Param [catalogues, rList, checked]
     * @return java.util.Map<java.lang.Integer,com.hy.dto.ReportCatalogueDto>
     **/
    private Map<Integer, BOCatalogueDto> arrageCatalogue(List<BOCatalogueDto> catalogues, List<BOInfo> rList, List<BOInfo> checked) {
        //list转map
        Map<Integer, BOCatalogueDto> cataMap = catalogues.stream().collect(Collectors.toMap(BOCatalogueDto::getId, a -> a, (k1, k2) -> k1));
        Map<Integer, BOInfo> infoMap = checked.stream().collect(Collectors.toMap(BOInfo::getId, a -> a, (k1, k2) -> k1));
        //循环报表信息将报表信息加入map相应key下
        for (BOInfo info : rList) {
            BOCatalogueDto dto = cataMap.get(info.getDirectoryid());
            //将报表数据添加到相应目录下
            if (dto != null) {
                if (dto.getItems() == null) {
                    dto.setItems(new ArrayList<>());
                }
                //将报表对象存入目录dto对象中使用
                if (infoMap.containsKey(info.getId())) {//若为选中项，则设置“checked”属性为true
                    dto.getItems().add(new BOCatalogueDto(info.getId(), info.getName(), info.getDirectoryid(), null, info.getReportid(), info.getType(), true, true));
                } else {
                    dto.getItems().add(new BOCatalogueDto(info.getId(), info.getName(), info.getDirectoryid(), null, info.getReportid(), info.getType(), false, true));
                }
            }
        }
        //形成树结构
        this.setListToTree(cataMap, catalogues);
        return cataMap;
    }

    /**
     * @Author 钱敏杰
     * @Description //TODO
     * @Date 2018/12/14 9:29
     * @Param [cataMap, catalogues]
     * @return void
     **/
    private void setListToTree(Map<Integer, BOCatalogueDto> cataMap, List<BOCatalogueDto> catalogues){
        //循环目录list结构，生成树的格式
        for (BOCatalogueDto cata : catalogues) {
            if (cata.getPid() != null) {
                //将子目录添加到父目录下
                BOCatalogueDto dto = cataMap.get(cata.getPid());
                if (dto != null) {
                    if (dto.getItems() == null) {
                        dto.setItems(new ArrayList<>());
                    }
                    dto.getItems().add(cata);
                }
            }
        }
        //移除非首菜单项（含有父id）
        for (BOCatalogueDto cata : catalogues) {
            if (cata.getPid() != null) {
                cataMap.remove(cata.getId());
            }
        }
    }

}
