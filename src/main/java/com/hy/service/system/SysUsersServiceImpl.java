package com.hy.service.system;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.config.ldap.LdapUtil;
import com.hy.dto.SysUserDto;
import com.hy.dto.SysUsersDto;
import com.hy.dto.SysUsersNewDto;
import com.hy.mapper.ms.SysUsersMapper;
import com.hy.model.HrmResource;
import com.hy.model.SysUsers;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class SysUsersServiceImpl implements SysUsersService {

    private static final Logger logger = LoggerFactory.getLogger(SysUsersServiceImpl.class);
    @Autowired
    private SysUsersMapper sysUsersMapper;

    @Override
    public List<SysUsersDto> getUsers(){
        return DTOUtil.populateList(sysUsersMapper.selectUsers(),SysUsersDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUsers(SysUsersNewDto sysUsersNewDto){
        try {
            for (SysUsersDto hr: sysUsersNewDto.getnHrmResources()){
                sysUsersMapper.insertSelective(new SysUsers(hr.getName(), SecurityUtil.getUserInfo().getLoginid(), SecurityUtil.getUserInfo().getLoginid(),hr.getId().toString()));
            }
            return true;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<SysUsersDto> getUsersByUid(int uid) {
        List<SysUsersDto> sysUsersDto=new ArrayList<>();
        List<SysUsers> sysUsers = sysUsersMapper.selectByUid(uid);
        for(SysUsers su:sysUsers){
            sysUsersDto.add(new SysUsersDto(su.getOauserid(),su.getOaloginid(),su.getName()));
        }
        return sysUsersDto;
    }

    @Override
    public List<SysUsersDto> getUsersByLoginid(String loginid){
        List<SysUsersDto> sysUsersDto=new ArrayList<>();
        List<SysUsers> sysUsers = sysUsersMapper.selectByLoginid(loginid);
        for(SysUsers su:sysUsers){
            sysUsersDto.add(new SysUsersDto(su.getOauserid(),su.getOaloginid(),su.getName()));
        }
        return sysUsersDto;
    }

    @Override
    public List<SysUsersDto> getUsersByLike(String lastname){
        return DTOUtil.populateList(sysUsersMapper.selectUsersByLike(lastname),SysUsersDto.class);
    }

    @Override
    public boolean deleteUsers(int id){
        return sysUsersMapper.deleteByPrimaryKey(id)==1;
    }

    @Override
    public List<SysUsersDto> getAllUsers(String filters, int pageNum, int pageSize, String value, String sort, String dir){
        PageHelper.startPage(pageNum, pageSize);
        List<SysUsers> sysUsers=sysUsersMapper.selectAllUsers(filters, value,sort,dir);
        System.out.println(sysUsers.size());
        return DTOUtil.populateList(sysUsers,SysUsersDto.class);
    }


    @Override
    public int getTotalUsers(String filters, String value){
        return sysUsersMapper.selectTotalUsers(filters, value);
    }

    @Override
    public List<HrmResource> getUsersBySearch(String value) {
        List<SysUsers> users =  sysUsersMapper.selectAllUsers(null, value,null,null);
        List<HrmResource> hrm = new LinkedList<>();
        IntStream.range(0, users.size()).forEach(i -> {
            HrmResource hr = new HrmResource();
            hr.setLoginid(users.get(i).getEmployeenumber());
            hr.setLastname(users.get(i).getName());
            hr.setId(users.get(i).getEmployeenumber());
            hrm.add(hr);
        });
        return hrm;
    }

    /**
     * @Author 钱敏杰
     * @Description 根据员工号获取用户信息
     * @Date 2018/11/14 14:13
     * @Param [employeenumber]
     * @return java.util.List<com.hy.dto.SysUsersDto>
     **/
    @Override
    public SysUsersDto getUsersByEmpnum(String employeenumber){
        SysUsers sysUsers = sysUsersMapper.selectByEmpnum(employeenumber);
        SysUsersDto dto = new SysUsersDto(sysUsers.getId(),sysUsers.getName(),sysUsers.getEmployeenumber());
        return dto;
    }

    /**
     * @Author 钱敏杰
     * @Description 根据员工号或员工姓名查询条件查询员工信息
     * @Date 2018/11/16 8:32
     * @Param [value]
     * @return java.util.List<com.hy.dto.SysUserDto>
     **/
    @Override
    public List<SysUserDto> searchUsers(String value){
        List<SysUserDto> resultList = null;
        try {
            if(StringUtils.isNotEmpty(value)){
                //模糊查询员工号与姓名字段
                Filter filter = Filter.create("(|(sAMAccountName="+ value +")(sn="+ value +"))");
                SearchResult searchResult = LdapUtil.searchResult(null, null, filter);
                if (null != searchResult && searchResult.getEntryCount() > 0) {
                    resultList = new ArrayList<>();
                    SysUserDto dto = null;
                    for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                        dto = new SysUserDto();
                        dto.setId(entry.getAttributeValue("sAMAccountName"));
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
     * @Description 根据条件查询单个用户
     * @Date 2018/12/19 18:20
     * @Param [value]
     * @return com.hy.dto.SysUserDto
     **/
    @Override
    public SysUserDto searchUser(String value){
        SearchResult searchResult;
        SysUserDto resultDto = null;
        try {
            //模糊查询员工号与姓名字段
            Filter filter = Filter.create("(|(sAMAccountName="+ value +")(sn="+ value +"))");
            searchResult = LdapUtil.searchResult(null, null, filter);
            if (null != searchResult && searchResult.getEntryCount() > 0) {
                SearchResultEntry entry = searchResult.getSearchEntries().get(0);
                resultDto = new SysUserDto();
                resultDto.setId(entry.getAttributeValue("sAMAccountName"));
                resultDto.setLoginid(entry.getAttributeValue("sAMAccountName"));
                resultDto.setLastname(entry.getAttributeValue("sn"));
            }
        } catch (LDAPException e) {
            logger.error("从ad域查询员工信息异常！", e);
        }
        return resultDto;
    }

    /**
     * @Author 钱敏杰
     * @Description 更新用户信息
     * @Date 2019/1/4 10:05
     * @Param [user]
     * @return boolean
     **/
    @Override
    public boolean updateUser(SysUsersDto user){
        if(user.getId() != null){
            SysUsers sysUsers = DTOUtil.populate(user, SysUsers.class);
            int i = sysUsersMapper.updateByPrimaryKeySelective(sysUsers);
            if(i >0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * @Author 沈超宇
     * @Description 根据账号或姓名精确查询用户
     * @Date 2019/1/21 10:13
     **/
    @Override
    public List<SysUsersDto> getUsersAccurate(String value){
        List<SysUsers> sysUsers = sysUsersMapper.selectUsersAccurate(value);
        return DTOUtil.populateList(sysUsers,SysUsersDto.class);
    }

    @Override
    //查询所有系统用户
    public List<String> getEmpnum(){
        return sysUsersMapper.selectEmpnum();
    }
}
