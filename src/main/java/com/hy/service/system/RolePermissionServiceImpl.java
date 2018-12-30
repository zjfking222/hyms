package com.hy.service.system;

import com.hy.common.SecurityUtil;
import com.hy.dto.*;
import com.hy.mapper.ms.SysRolePermissionMapper;
import com.hy.model.SysRolePermission;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
            //校验根节点的叶子节点为空
            for (SysRolePermissionTreeDto t: trees){
                if (t.getItems().size() == 0){
                    System.out.println(t.getId());
                    for (SysRolePermission rp: sysRolePermissions){
                        if (t.getId() == rp.getMid()){
                            t.setChecked(true);
                        }
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
    public boolean setRolePermission(SysRolePermissionWithRidDto sysRolePermissionWithRidDto) {

        try{
            //先做删除所有操作
            rolePermissionMapper.deleteRolePermissionByRid(sysRolePermissionWithRidDto.getRid());

            List<SysRolePermissionDto> sysRolePermissionDtos = Arrays.asList(sysRolePermissionWithRidDto.getRolePermission());
//            List<SysRolePermissionDto> sysRolePermissionFilter = sysRolePermissions.stream().filter(sysRolePermissionDto
//                    -> sysRolePermissionDto.getMid() ).collect(Collectors.toList());

            List<SysRolePermission> sysRolePermissions = DTOUtil.populateList(sysRolePermissionDtos,SysRolePermission.class);
            for (SysRolePermission s: sysRolePermissions){
                s.setCreater(SecurityUtil.getLoginid());
                s.setModifier(SecurityUtil.getLoginid());
                rolePermissionMapper.insertSelective(s);
            }
            return true;
        }catch (Exception e){
            throw e;
        }
    }


}
