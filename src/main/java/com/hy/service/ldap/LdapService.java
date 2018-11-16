package com.hy.service.ldap;

import com.hy.dto.SysUserDto;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/15 15:38
 * @Description:
 */
public interface LdapService {

    /**
     * @Author 钱敏杰
     * @Description 根据员工号或员工姓名查询条件查询员工信息（模糊查询）
     * @Date 2018/11/16 8:32
     * @Param [value]
     * @return java.util.List<com.hy.dto.SysUserDto>
     **/
    List<SysUserDto> searchUsers(String value);
}
