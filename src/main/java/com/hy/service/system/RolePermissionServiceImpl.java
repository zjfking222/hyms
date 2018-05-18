package com.hy.service.system;

import com.hy.dto.PermissionDto;
import com.hy.dto.SysPermissionDto;
import com.hy.dto.SysRolePermissionDto;
import com.hy.dto.SysRolePermissionTreeDto;
import com.hy.mapper.ms.SysPermissionMapper;
import com.hy.mapper.ms.SysRolePermissionMapper;
import com.hy.model.SysRolePermission;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class RolePermissionServiceImpl implements RolePermissionService{

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private SysRolePermissionMapper rolePermissionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysRolePermissionTreeDto> getRolePermission(Integer rid) {

        try{
            List<SysPermissionDto> permissions = permissionService.getRoleMenus();
            List<SysRolePermission> sysRolePermissions =
                    rolePermissionMapper.selectRolePermissionByRid(rid);
            List<SysRolePermissionTreeDto> trees = new LinkedList<>();
            List<SysRolePermissionTreeDto> branches = new ArrayList<>();

            //将根节点放入trees
            for (SysPermissionDto pd: permissions) {
                if(pd.getParentid() == 0 ) {
                    trees.add(new SysRolePermissionTreeDto(pd.getId(), pd.getName(),
                            false,0,true, new LinkedList<>()));
                }
            }
            //因为是二层结构 归出叶子节点 并赋值是否勾选状态
            SysRolePermissionTreeDto leaf;
            boolean isOwn = false;
            int flag = -1;
            for (SysPermissionDto pd: permissions){
                if(sysRolePermissions.size()!=0){
                    for (SysRolePermission rp: sysRolePermissions){
                        if(pd.getParentid() != 0){
                            if(rp.getMid() == pd.getId()){
                                leaf = new SysRolePermissionTreeDto(pd.getId(), pd.getName(),
                                        true, pd.getParentid(),true, new LinkedList<>());
                            }
                            else {
                                leaf =  new SysRolePermissionTreeDto(pd.getId(), pd.getName(),
                                        false, pd.getParentid(),true, new LinkedList<>());
                            }
                            for(int i = 0 ; i < branches.size() ; i++){
                                if (branches.get(i).getId() == leaf.getId()){
                                    isOwn = true;
                                    flag = i;
                                }
                            }
                            if(isOwn && leaf.isChecked()){
                                branches.get(flag).setChecked(true);
                            }
                            else if(isOwn && !leaf.isChecked()){

                            }
                            else {
                                branches.add(leaf);
                            }
                            isOwn = false;
                        }
                    }
                }
                else {
                    if(pd.getParentid() != 0){
                        branches.add(new SysRolePermissionTreeDto(pd.getId(), pd.getName(),
                                false, pd.getParentid(),true, new LinkedList<>()));
                    }
                }
            }
            //将叶子节点归入根节点
            for (SysRolePermissionTreeDto t: trees){
                for (SysRolePermissionTreeDto b: branches){
                    if(b.getPid() == t.getId()){
                        t.getItems().add(b);
                    }
                }
            }
            return trees;

        }catch (Exception e){
            throw e;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean setRolePermission(Integer[] mid, Integer rid) {
        //先做删除所有操作
        rolePermissionMapper.deleteRolePermissionByRid(rid);
        //添加
        List<SysRolePermission> sysRolePermissions;
        for (Integer mid_i: mid){
            System.out.println(mid_i);
        }

//        rolePermissionMapper.insertSelective()
        return false;
    }


}
