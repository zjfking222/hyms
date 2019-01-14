package com.hy.service.system;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.dto.SysDictDto;
import com.hy.mapper.ms.SysDictMapper;
import com.hy.model.SysDict;
import com.hy.utils.DTOUtil;
import com.microsoft.sqlserver.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    //根据编码查询当前节点
    public SysDictDto getByCode(String code){
        SysDict pdict = sysDictMapper.selectByCode(code);
        return DTOUtil.populate(pdict, SysDictDto.class);
    }

    /**
     * @Author 钱敏杰
     * @Description 根据条件分页查询数据字典
     * @Date 2018/12/30 10:19
     * @Param [pageNum, pageSize, sort, dir, value]
     * @return java.util.List<com.hy.dto.SysDictDto>
     **/
    @Override
    public List<SysDictDto> getDictsPage(Integer pageNum, Integer pageSize,String filters, String sort, String dir, String value){
        PageHelper.startPage(pageNum, pageSize);
        List<SysDict> dicts = sysDictMapper.selectDictsPage(filters, sort, dir, value);
        List<SysDictDto> dictDtos = DTOUtil.populateList(dicts, SysDictDto.class);
        return dictDtos;
    }

    /**
     * @Author 钱敏杰
     * @Description 根据条件统计全部数据量
     * @Date 2018/12/30 10:21
     * @Param [value]
     * @return java.lang.Integer
     **/
    public Integer getDictCount(String filters, String value){
        Integer count = sysDictMapper.countDicts(filters, value);
        return count;
    }

    /**
     * @Author 钱敏杰
     * @Description 在父节点下根据查询条件查询子节点
     * @Date 2018/12/29 14:06
     * @Param [pid, value]
     * @return java.util.List<com.hy.model.SysDict>
     **/
    @Override
    public List<SysDictDto> getDictChildren(Integer pid, String value){
        List<SysDict> dicts = sysDictMapper.selectChildren(pid, value);
        List<SysDictDto> dictDtos = DTOUtil.populateList(dicts, SysDictDto.class);
        return dictDtos;
    }

    /**
     * @Author 钱敏杰
     * @Description 检查当前编码是否重复，若重复则返回true
     * @Date 2018/12/30 14:49
     * @Param [code, id]
     * @return boolean
     **/
    @Override
    public boolean checkCode(String code, Integer id){
        if(StringUtils.isEmpty(code)){//不存在code值，则不会重复
            return false;
        }
        //检查当前数据的code是否已存在，已存在则不能保存（更新时排除当前id的code不变的情况）
        int i = sysDictMapper.countrepeat(code, id);
        if(i >0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 添加或更新数据字典节点数据
     * @Date 2018/12/29 15:35
     * @Param [sysDictDto]
     * @return void
     **/
    @Override
    @Transactional
    public void updateSysDict(SysDictDto sysDictDto){
        SysDict sysDict = DTOUtil.populate(sysDictDto, SysDict.class);
        sysDict.setModifier(SecurityUtil.getLoginid());
        int i = sysDictMapper.updateByPrimaryKey(sysDict);
        if(i <= 0){
            throw new RuntimeException("添加或更新数据字典失败");
        }
        //更新子节点中的父节点名称
        i = sysDictMapper.updatePidname(sysDictDto.getName(), sysDictDto.getId());
        if(i <0){
            throw new RuntimeException("更新数据字典子节点失败");
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 添加数据字典节点数据
     * @Date 2019/1/3 14:12
     * @Param [sysDictDto]
     * @return void
     **/
    @Override
    public void addSysDict(SysDictDto sysDictDto){
        SysDict sysDict = DTOUtil.populate(sysDictDto, SysDict.class);
        sysDict.setModifier(SecurityUtil.getLoginid());
        sysDict.setCreater(SecurityUtil.getLoginid());
        int i = sysDictMapper.insert(sysDict);
        if(i <= 0){
            throw new RuntimeException("添加或更新数据字典失败");
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 删除数据字典数据
     * @Date 2018/12/29 16:21
     * @Param [id]
     * @return void
     **/
    @Override
    public void deleteSysDict(Integer id){
        int i = sysDictMapper.deleteByPrimaryKey(id);
        if(i <= 0){
            throw new RuntimeException("删除数据字典失败");
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 根据编码查询当前节点的全部子节点
     * @Date 2018/12/30 16:35
     * @Param [code]
     * @return java.util.List<com.hy.dto.SysDictDto>
     **/
    @Override
    public List<SysDictDto> selectByCode(String code){
        List<SysDict> list = sysDictMapper.selectChildByCode(code);
        return DTOUtil.populateList(list, SysDictDto.class);
    }
}
