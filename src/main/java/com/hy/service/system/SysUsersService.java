package com.hy.service.system;

import com.hy.dto.SysUserDto;
import com.hy.dto.SysUsersDto;
import com.hy.dto.SysUsersNewDto;
import com.hy.model.HrmResource;
import com.hy.model.SysUsers;

import java.util.List;

public interface SysUsersService {

    List<SysUsersDto> getUsers();
    List<SysUsersDto> getUsersByUid(int oauserid);
    List<SysUsersDto> getUsersByLoginid(String oaloginid);
    List<SysUsersDto> getUsersByLike(String name);
    boolean addUsers(SysUsersNewDto sysUsersNewDto);
    boolean deleteUsers(int id);
    List<SysUsersDto> getAllUsers(String filters, int pageNum,int pageSize, String value, String sort, String dir);
    int getTotalUsers(String filters, String value);

    /**
     * 获取系统用户列表 （角色管理使用）
     * @param value 搜索内容
     * @return List
     */
    List<HrmResource> getUsersBySearch(String value);
    /**
     * @Author 钱敏杰
     * @Description 根据员工号获取用户信息
     * @Date 2018/11/14 14:13
     * @Param [employeenumber]
     * @return java.util.List<com.hy.dto.SysUsersDto>
     **/
    SysUsersDto getUsersByEmpnum(String employeenumber);

    /**
     * @Author 钱敏杰
     * @Description 根据员工号或员工姓名查询条件查询员工信息
     * @Date 2018/11/16 8:32
     * @Param [value]
     * @return java.util.List<com.hy.dto.SysUserDto>
     **/
    List<SysUserDto> searchUsers(String value);

    /**
     * @Author 钱敏杰
     * @Description 根据条件查询单个用户
     * @Date 2018/12/19 18:20
     * @Param [value]
     * @return com.hy.dto.SysUserDto
     **/
    SysUserDto searchUser(String value);

    /**
     * @Author 钱敏杰
     * @Description 更新用户信息
     * @Date 2019/1/4 10:05
     * @Param [user]
     * @return boolean
     **/
    boolean updateUser(SysUsersDto user);

    /**
     * @Author 沈超宇
     * @Description 根据账号或姓名精确查询用户
     * @Date 2019/1/21 10:09
     **/
    List<SysUsersDto> getUsersAccurate(String value);

    //查询所有系统用户
    List<String> getEmpnum();
}
