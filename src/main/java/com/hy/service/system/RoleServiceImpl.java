package com.hy.service.system;

import com.hy.dto.SysRolesDto;
import com.hy.mapper.ms.SysRolesMapper;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private SysRolesMapper rolesMapper;

    @Override
    public List<SysRolesDto> getRoles() {
        return DTOUtil.populateList(rolesMapper.selectRoles(),SysRolesDto.class);
    }
}
