package com.hy.service.system;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityHelp;
import com.hy.dto.HrmResourceDto;
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
                sysUsersMapper.insertSelective(new SysUsers(hr.getName(),SecurityHelp.getUserId(),SecurityHelp.getUserId(),
                       hr.getOaloginid(),hr.getId()));
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
            hr.setLoginid(users.get(i).getOaloginid());
            hr.setLastname(users.get(i).getName());
            hr.setId(users.get(i).getOauserid());
            hrm.add(hr);
        });
        return hrm;
    }
}
