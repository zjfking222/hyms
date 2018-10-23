package com.hy.service.system;

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
    List<SysUsersDto> getAllUsers(int pageNum,int pageSize, String value, String sort, String dir);
    int getTotalUsers(String value);

    /**
     * 获取系统用户列表 （角色管理使用）
     * @param value 搜索内容
     * @return List
     */
    List<HrmResource> getUsersBySearch(String value);

}
