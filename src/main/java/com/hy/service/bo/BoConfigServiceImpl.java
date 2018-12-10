package com.hy.service.bo;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.config.ldap.LdapUtil;
import com.hy.dto.*;
import com.hy.mapper.ms.ReportAccadRelationMapper;
import com.hy.mapper.ms.ReportAccountMapper;
import com.hy.mapper.ms.ReportCatalogueMapper;
import com.hy.mapper.ms.ReportInfoMapper;
import com.hy.model.ReportAccadRelation;
import com.hy.model.ReportAccount;
import com.hy.model.ReportCatalogue;
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

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private ReportCatalogueMapper reportCatalogueMapper;

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

    /**
     * @Author 钱敏杰
     * @Description 获取全部目录及报表树，以及当前BO账号选中的报表
     * @Date 2018/12/10 14:21
     * @Param [accountid]
     * @return java.util.List<com.hy.dto.ReportCatalogueDto>
     **/
    @Override
    public List<ReportCatalogueDto> getAllReportTree(String accountid){
        Map<Integer, ReportCatalogueDto> cataMap = null;
        //查询出全部目录结构
        List<ReportCatalogue> list = reportCatalogueMapper.selectAll();
        //查询出全部报表信息
        List<ReportInfo> rList = reportInfoMapper.selectReport(null, null, null);
        //循环目录结构，生成以id为key的map结构
        if(list != null && list.size() >0 && rList != null && rList.size() >0){
            List<ReportCatalogueDto> catalogues = DTOUtil.populateList(list,ReportCatalogueDto.class );
            //已选中的数据
            List<ReportInfo> checked = this.getReportInfoByAcc(accountid);
            //整理树的结构
            cataMap = this.arrageCatalogue(catalogues, rList, checked);
        }
        List<ReportCatalogueDto> returnList = new ArrayList(Arrays.asList(cataMap.values().toArray()));
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
    public List<ReportInfo> getReportInfoByAcc(String accountid){
        List<ReportInfo> list = reportInfoMapper.selectByAccountid(accountid);
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
    public List<ReportCatalogueDto> getAccReportTree(String accountid, String empnum){
        Map<Integer, ReportCatalogueDto> cataMap = null;
        //查询出全部目录结构
        List<ReportCatalogue> list = reportCatalogueMapper.selectAll();
        //查询出当前BO账号下的全部报表信息
        List<ReportInfo> rList = reportInfoMapper.selectByAccountid(accountid);
        //循环目录结构，生成以id为key的map结构
        if(list != null && list.size() >0 && rList != null && rList.size() >0){
            List<ReportCatalogueDto> catalogues = DTOUtil.populateList(list,ReportCatalogueDto.class );
            //移除空目录
            removeNullNode(catalogues, rList);
            //已选中的数据
            List<ReportInfo> checked = this.getReportInfoByEmp(empnum);
            //整理树的结构
            cataMap = this.arrageCatalogue(catalogues, rList, checked);
        }
        List<ReportCatalogueDto> returnList = new ArrayList(Arrays.asList(cataMap.values().toArray()));
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
    public List<ReportInfo> getReportInfoByEmp(String empnum){
        List<ReportInfo> list = reportInfoMapper.selectByEmp(empnum);
        return list;
    }

    /**
     * @Author 钱敏杰
     * @Description 删除空节点目录
     * @Date 2018/12/10 9:15
     * @Param [catalogues, rList]
     * @return void
     **/
    private void removeNullNode(List<ReportCatalogueDto> catalogues, List<ReportInfo> rList){
        //存放需保留的id
        Map<Integer, Boolean> keepMap = new HashMap<>();
        //list转map
        Map<Integer, ReportCatalogueDto> cataMap = catalogues.stream().collect(Collectors.toMap(ReportCatalogueDto :: getId, a ->a, (k1,k2)->k1));
        for(ReportInfo info:rList){
            //取出报表数据中的目录id
            Integer dirid = info.getDirectoryid();
            keepMap.put(dirid, true);
            ReportCatalogueDto catalogue = null;
            int i = 0;
            while(true){
                //取出当前目录节点数据
                catalogue = cataMap.get(dirid);
                if(catalogue != null){
                    if(catalogue.getPid() != null){
                        //若当前节点存在父节点，则需要保留此父节点
                        dirid = catalogue.getPid();
                        keepMap.put(dirid, true);
                        i++;
                    }else{//不存在父节点，则循环结束
                        if(i >0){
                            keepMap.put(dirid, true);
                        }
                        break;
                    }
                }
            }
        }
        //从列表catalogues中删除不需要保留的数据
        Iterator<ReportCatalogueDto> iter = catalogues.iterator();
        while(iter.hasNext()){
            ReportCatalogueDto catalogueDto = iter.next();
            if(!keepMap.containsKey(catalogueDto.getPid())){
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
    private Map<Integer, ReportCatalogueDto> arrageCatalogue(List<ReportCatalogueDto> catalogues, List<ReportInfo> rList, List<ReportInfo> checked){
        //list转map
        Map<Integer, ReportCatalogueDto> cataMap = catalogues.stream().collect(Collectors.toMap(ReportCatalogueDto :: getId, a ->a, (k1,k2)->k1));
        Map<Integer, ReportInfo> infoMap = checked.stream().collect(Collectors.toMap(ReportInfo :: getId, a ->a, (k1,k2)->k1));
        //循环报表信息将报表信息加入map相应key下
        for(ReportInfo info:rList){
            ReportCatalogueDto dto = cataMap.get(info.getDirectoryid());
            //将报表数据添加到相应目录下
            if(dto != null){
                if(dto.getItems() == null){
                    dto.setItems(new ArrayList<>());
                }
                //将报表对象存入目录dto对象中使用
                if(infoMap.containsKey(info.getId())){//若为选中项，则设置“checked”属性为true
                    dto.getItems().add(new ReportCatalogueDto(info.getId(), info.getName(), info.getDirectoryid(), null, info.getReportid(), info.getType(), true, true));
                }else {
                    dto.getItems().add(new ReportCatalogueDto(info.getId(), info.getName(), info.getDirectoryid(), null, info.getReportid(), info.getType(), false, true));
                }
            }
        }
        //循环目录list结构，生成树的格式
        for(ReportCatalogueDto cata:catalogues){
            if(cata.getPid() != null){
                //将子目录添加到父目录下
                ReportCatalogueDto dto = cataMap.get(cata.getPid());
                if(dto != null){
                    if(dto.getItems() == null){
                        dto.setItems(new ArrayList<>());
                    }
                    dto.getItems().add(cata);
                }
            }
        }
        //移除非首菜单项（含有父id）
        for(ReportCatalogueDto cata:catalogues){
            if(cata.getPid() != null){
                cataMap.remove(cata.getId());
            }
        }
        return cataMap;
    }
}
