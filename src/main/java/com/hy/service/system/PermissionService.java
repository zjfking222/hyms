package com.hy.service.system;

import com.hy.dto.PermissionDto;

import java.util.List;

public interface PermissionService {
    /**
     * 获取用户的菜单权限列表
     *
     * @param userId 用于ID
     * @return 菜单列表
     */
    List<PermissionDto> getUserMenus(int userId);

    /**
     * 获取
     * @param userId
     * @return
     */
    List<PermissionDto> getRoleMenus(int userId);
    /**
     * 分页获取菜单信息
     *
     * @param pageNum  当前页
     * @param pageSize 当前页面展示数目
     * @return 菜单列表
     */
    List<PermissionDto> getMenus(int pageNum, int pageSize);

    /**
     * 获取菜单数量
     * @return 菜单列表
     */
    int getMenusTotal();


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
}
