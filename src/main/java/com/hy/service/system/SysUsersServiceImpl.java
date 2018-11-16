package com.hy.service.system;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.dto.SysUsersDto;
import com.hy.dto.SysUsersNewDto;
import com.hy.mapper.ms.SysUsersMapper;
import com.hy.model.HrmResource;
import com.hy.model.SysUsers;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class SysUsersServiceImpl implements SysUsersService {
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
    public List<SysUsersDto> getAllUsers(int pageNum, int pageSize, String value, String sort, String dir){
        PageHelper.startPage(pageNum, pageSize);
        List<SysUsers> sysUsers=sysUsersMapper.selectAllUsers(value,sort,dir);
        System.out.println(sysUsers.size());
        return DTOUtil.populateList(sysUsers,SysUsersDto.class);
    }


    @Override
    public int getTotalUsers(String value){
        return sysUsersMapper.selectTotalUsers(value);
    }

    @Override
    public List<HrmResource> getUsersBySearch(String value) {
        List<SysUsers> users =  sysUsersMapper.selectAllUsers(value,null,null);
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
}
