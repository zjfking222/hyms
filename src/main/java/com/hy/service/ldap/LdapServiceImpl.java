package com.hy.service.ldap;

import com.hy.config.ldap.LdapConnector;
import com.hy.config.ldap.LdapUtil;
import com.hy.dto.SysUserDto;
import com.hy.model.LdapStaff;
import com.unboundid.ldap.sdk.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/15 15:39
 * @Description:
 */
@Service
public class LdapServiceImpl implements LdapService {

    private static final Logger logger = LoggerFactory.getLogger(LdapServiceImpl.class);

    /**
     * @Author 钱敏杰
     * @Description 根据员工号或员工姓名查询条件查询员工信息（模糊查询）
     * @Date 2018/11/16 8:32
     * @Param [value]
     * @return java.util.List<com.hy.dto.SysUserDto>
     **/
    @Override
    public List<SysUserDto> searchUsers(String value){
        SearchResult searchResult;
        List<SysUserDto> resultList = null;
        try {
            //模糊查询员工号与姓名字段
            Filter filter = Filter.create("(|(sAMAccountName=*"+ value +"*)(sn=*"+ value +"*))");
            searchResult = LdapUtil.searchResult(null, null, filter);
            if (null != searchResult && searchResult.getEntryCount() > 0) {
                resultList = new ArrayList<>();
                SysUserDto dto = null;
                for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                    dto = new SysUserDto();
                    dto.setId(entry.getAttributeValue("sAMAccountName"));
                    dto.setLoginid(entry.getAttributeValue("sAMAccountName"));
                    dto.setLastname(entry.getAttributeValue("sn"));
                    resultList.add(dto);
                }
            }
        } catch (LDAPException e) {
            logger.error("从ad域查询员工信息异常！", e);
        }
        return resultList;
    }

}
