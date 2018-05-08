package com.hy.service.system;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityHelp;
import com.hy.dto.PermissionDto;
import com.hy.mapper.ms.SysPermissionMapper;
import com.hy.model.SysPermission;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    public List<PermissionDto> getUserMenus(int userId) {
        List<SysPermission> list = sysPermissionMapper.selectUserMenus();

        Map<Integer, PermissionDto> map = new LinkedHashMap<Integer, PermissionDto>();
        for (int i = 0; i < list.size(); i++) {
            PermissionDto dto = DTOUtil.populate(list.get(i), PermissionDto.class);
            map.put(dto.getId(), dto);
        }
        for (PermissionDto d : map.values()) {
            if (map.containsKey(d.getParentid())) {
                PermissionDto parent = map.get(d.getParentid());
                if (parent.getPermissionDtoList() == null)
                    parent.setPermissionDtoList(new ArrayList<PermissionDto>());
                parent.getPermissionDtoList().add(d);
            }
        }
        List<PermissionDto> listdto = new ArrayList<PermissionDto>();
        for (PermissionDto d : map.values()) {
            if (d.getPermissionDtoList() != null)
                listdto.add(d);
        }
        return listdto;
    }

    @Override
    public List<PermissionDto> getMenus(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysPermission> list = sysPermissionMapper.selectMenus(false);

        return DTOUtil.populateList(list, PermissionDto.class);
    }

    @Override
    public int getMenusTotal() {
        return sysPermissionMapper.selectMenusTotal();
    }

    @Override
    public List<PermissionDto> getParentMenus() {
        List<SysPermission> list = sysPermissionMapper.selectMenus(true);
        return DTOUtil.populateList(list, PermissionDto.class);
    }

    @Override
    public PermissionDto addMenus(PermissionDto dto) {
        SysPermission sysPermission = DTOUtil.populate(dto, SysPermission.class);
        sysPermission.setCreater(SecurityHelp.getUserId());
        sysPermission.setModifier(SecurityHelp.getUserId());
        int id = sysPermissionMapper.insertSelective(sysPermission);
        sysPermission = sysPermissionMapper.selectByPrimaryKey(sysPermission.getId());
        return DTOUtil.populate(sysPermission, PermissionDto.class);
    }

    @Override
    public int updateMenus(PermissionDto dto) {
        SysPermission sysPermission = DTOUtil.populate(dto, SysPermission.class);
        sysPermission.setModifier(SecurityHelp.getUserId());
        return sysPermissionMapper.updateByPrimaryKeySelective(sysPermission);
    }

    @Override
    public int deleteById(int id) {
        return sysPermissionMapper.deleteByPrimaryKey(id);
    }
}