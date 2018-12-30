package com.hy.service.system;

import com.hy.common.SecurityUtil;
import com.hy.dto.SysDictDto;
import com.hy.mapper.ms.SysDictMapper;
import com.hy.model.SysDict;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/27 14:57
 * @Description:基础信息数据字典表serviceImpl
 */
@Service
public class SysDictServiceImpl implements SysDictService{
    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    //查询衢州新闻类型（未删除）
    public List<SysDictDto> getNewsType(){
        int pid = sysDictMapper.selectQzgzNewsTypeId().getId();
        List<SysDict> sysDict = sysDictMapper.selectQzgzNewsType(pid);
        return DTOUtil.populateList(sysDict, SysDictDto.class);
    }
    //新增衢州新闻类型
    public boolean addNewsType(SysDictDto sysDictDto){
        SysDict sysDict = DTOUtil.populate(sysDictDto, SysDict.class);
        int pid = sysDictMapper.selectQzgzNewsTypeId().getId();
        sysDict.setPid(pid);
        sysDict.setCreater(SecurityUtil.getLoginid());
        sysDict.setModifier(SecurityUtil.getLoginid());
        return sysDictMapper.insertQzgzNewsType(sysDict) == 1;
    }
    //修改衢州新闻类型
    public boolean setNewsType(SysDictDto sysDictDto){
        SysDict sysDict = DTOUtil.populate(sysDictDto, SysDict.class);
        sysDict.setModifier(SecurityUtil.getLoginid());
        return sysDictMapper.updateQzgzNewsType(sysDict) == 1;
    }
    //删除衢州新闻类型
    public boolean delNewsType(int id){
        return sysDictMapper.deleteQzgzNewsType(id) == 1;
    }
}
