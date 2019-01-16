package com.hy.service.system;

import com.hy.dto.PermissionDto;
import com.hy.dto.SysPermissionDto;
import com.hy.model.SysPermission;

import java.util.List;

public interface PermissionService {
    /**
     * 获取所有启动的权限
     * @return 权限列表
     */
    List<SysPermission> getAll();

    /**
     * 获取用户权限
     * @param userId 用户id
     * @return 权限列表
     */
    List<SysPermission> getByUserId(String userId);
    /**
     * 获取用户的菜单权限列表
     *
     * @param userId 用于ID
     * @return 菜单列表
     */
    List<PermissionDto> getUserMenus(String userId);

    /**
     * 获取菜单 用于角色管理
     * @param
     * @return
     */
    List<SysPermissionDto> getRoleMenus();
    /**
     * 分页获取菜单信息
     *
     * @param pageNum  当前页
     * @param pageSize 当前页面展示数目
     * @return 菜单列表
     */
    List<PermissionDto> getMenus(String filters, int pageNum, int pageSize, String sort, String dir);

    /**
     * 获取菜单数量
     * @return 菜单列表
     */
    int getMenusTotal(String filters);


    /**
     * 获取所有父菜单
     * @return
     */
    List<PermissionDto> getParentMenus();

    /**
     * 添加菜单
     * @param dto
     * @return
     */
    PermissionDto addMenus(PermissionDto dto);

    /**
     * 更新菜单
     * @param dto
     * @return
     */
    int updateMenus(PermissionDto dto);

    /**
     * 删除菜单
     * @param id 需要删除菜单的id
     * @return 删除数量
     */
    int  deleteById(int id);

    /**
     * @Author 钱敏杰
     * @Description 根据域类型获取用户的菜单权限列表
     * @Date 2018/10/17 7:50
     * @Param [userId, fieldType]
     * @return java.util.List<com.hy.dto.PermissionDto>
     **/
    List<PermissionDto> getUserMenus(String userId, int fieldType);
}
