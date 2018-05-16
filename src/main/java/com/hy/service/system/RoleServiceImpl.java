package com.hy.service.system;

import com.hy.common.SecurityHelp;
import com.hy.dto.HrmResourceDto;
import com.hy.dto.SysRolesDto;
import com.hy.dto.SysRolesUserDto;
import com.hy.mapper.ms.SysRolesMapper;
import com.hy.model.SysRoleUser;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private SysRolesMapper rolesMapper;

    @Override
    public List<SysRolesDto> getRoles() {
        return DTOUtil.populateList(rolesMapper.selectRoles(),SysRolesDto.class);
    }

    @Override
    public boolean delRole(int id) {
        return rolesMapper.deleteByPrimaryKey(id) == 1;
    }

    @Override
    public List<SysRolesDto> searchRoles(String name) {
        return DTOUtil.populateList(rolesMapper.selectRolesByLike(name),SysRolesDto.class);
    }

}
