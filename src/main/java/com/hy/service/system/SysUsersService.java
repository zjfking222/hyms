package com.hy.service.system;

import com.hy.dto.SysUsersDto;
import com.hy.dto.SysUsersNewDto;

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
}
