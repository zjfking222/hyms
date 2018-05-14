package com.hy.service.system;

import com.hy.common.SecurityHelp;
import com.hy.dto.HrmResourceDto;
import com.hy.dto.SysRolesUserDto;
import com.hy.mapper.ms.SysRoleUserMapper;
import com.hy.mapper.ms.SysRolesMapper;
import com.hy.model.SysRoleUser;
import com.hy.model.SysRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleUserServiceImpl implements RoleUserService{

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private SysRolesMapper sysRolesMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setRoleUser(SysRolesUserDto sysRolesUserDto) {

        try{
            SysRoles sysRoles = new SysRoles();
            sysRoles.setModifier(SecurityHelp.getUserId());
            sysRoles.setName(sysRolesUserDto.getName());
            sysRoles.setId(sysRolesUserDto.getRid());
            sysRolesMapper.updateByPrimaryKey(sysRoles);

            for (HrmResourceDto hr: sysRolesUserDto.getnHrmResources()){
                sysRoleUserMapper.insertSelective(new SysRoleUser(SecurityHelp.getUserId(),SecurityHelp.getUserId(),sysRolesUserDto.getRid(),
                        hr.getId(),hr.getLoginid(),hr.getLastname()));
            }

            for (int hr_id: sysRolesUserDto.getrHrmResources()){
                sysRoleUserMapper.deleteByPrimaryKey(hr_id);
            }
            return true;

        }catch (Exception e){
              throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRoleUser(SysRolesUserDto sysRolesUserDto) {
        try{
            SysRoles sysRoles = new SysRoles();
            sysRoles.setCreater(SecurityHelp.getUserId());
            sysRoles.setModifier(SecurityHelp.getUserId());
            sysRoles.setEnable(true);
            sysRoles.setName(sysRolesUserDto.getName());
            sysRolesMapper.insertSelective(sysRoles);
            System.out.println(sysRoles.getId());

            for (HrmResourceDto hr: sysRolesUserDto.getnHrmResources()){
                sysRoleUserMapper.insertSelective(new SysRoleUser(SecurityHelp.getUserId(),SecurityHelp.getUserId(),sysRoles.getId(),
                        hr.getId(),hr.getLoginid(),hr.getLastname()));
            }
            return true;
        }catch (Exception e){
            throw e;
        }
    }
}
