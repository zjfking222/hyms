package com.hy.service.bo;

import com.hy.dto.BOInfoDto;
import com.hy.mapper.ms.BOAccadRelationMapper;
import com.hy.mapper.ms.BOInfoMapper;
import com.hy.mapper.ms.BORoleAdMapper;
import com.hy.model.BOInfo;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/25 10:17
 * @Description:BO报表登录及其主页相关方法实现
 */
@Service
public class BoIndexServiceImpl implements BoIndexService{

    @Autowired
    private BOAccadRelationMapper accadRelationMapper;
    @Autowired
    private BORoleAdMapper roleAdMapper;
    @Autowired
    private BOInfoMapper infoMapper;

    /**
     * @Author 钱敏杰
     * @Description 检查当前用户是否具有登录BO报表的权限
     * @Date 2018/12/25 11:09
     * @Param [empnum]
     * @return boolean true 有权限；false 无权限
     **/
    @Override
    public boolean checkPermission(String empnum){
        //检查当前用户是否已配置BO账号
        Integer acount = accadRelationMapper.countAccByEmp(empnum);
        //检查当前用户是否已配置角色
        Integer rcount = roleAdMapper.countRoleByEmp(empnum);
        //拥有其一就可
        if(acount >0 || rcount >0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 查询当前员工在当前目录下所拥有的报表
     * @Date 2018/12/26 13:50
     * @Param [directoryid, empnum]
     * @return java.util.List<com.hy.dto.BOInfoDto>
     **/
    @Override
    public List<BOInfoDto> getDisplayReport(String directoryid, String empnum){
        List<BOInfo> list = infoMapper.selectEmpReport(directoryid, empnum);
        return DTOUtil.populateList(list, BOInfoDto.class);
    }

}
