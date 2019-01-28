package com.hy.controller.system;

import com.hy.common.ResultObj;
import com.hy.dto.SysDictDto;
import com.hy.enums.ResultCode;
import com.hy.service.system.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/28 13:34
 * @Description:数据字典操作controller
 */
@RestController
@RequestMapping("/sysdict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    /**
     * @Author 钱敏杰
     * @Description 查询当前父节点下子节点
     * @Date 2018/12/29 14:02
     * @Param [pid]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/getDictChildren")
    public ResultObj getDictChildren(Integer pid, String value){
        List<SysDictDto> dictDtos = sysDictService.getDictChildren(pid, value);
        return ResultObj.success(dictDtos);
    }

    /**
     * @Author 钱敏杰
     * @Description 添加或更新数据字典数据
     * @Date 2018/12/29 15:49
     * @Param [sysDictDto]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/updateSysDict")
    public ResultObj updateSysDict(SysDictDto sysDictDto){
        if(sysDictService.checkCode(sysDictDto.getCode(), sysDictDto.getId())){
            return ResultObj.error(ResultCode.ERROR_DATA_REPEAT);
        }else{
            if(sysDictDto.getId() == null){//新增
                sysDictService.addSysDict(sysDictDto);
            }else{//更新
                sysDictService.updateSysDict(sysDictDto);
            }
            return ResultObj.success();
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 删除数据字典数据
     * @Date 2018/12/29 16:22
     * @Param [id]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/deleteSysDict")
    public ResultObj deleteSysDict(Integer id){
        //判断是否存在子节点，若存在，则不予删除
        List<SysDictDto> list = sysDictService.getDictChildren(id, null);
        if(list != null && list.size() >0){
            return ResultObj.error(ResultCode.ERROR_DELETE_SUBNODE);
        }else{
            sysDictService.deleteSysDict(id);
            return ResultObj.success();
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 分页查询数据字典数据
     * @Date 2018/12/30 9:34
     * @Param [page, pageSize, value, sort, dir]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/getDictsPage")
    public ResultObj getDictsPage(Integer page, Integer pageSize, String filters, String sort, String dir, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", sysDictService.getDictsPage(page, pageSize,filters, sort, dir, value));
        map.put("total", sysDictService.getDictCount(filters, value));
        return ResultObj.success(map);
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前code节点下的所有子节点数据
     * @Date 2019/1/23 15:41
     * @Param [code]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/getDictChildrenBycode")
    public ResultObj getDictChildrenBycode(String code){
        List<SysDictDto> dictDtos = sysDictService.selectByCode(code);
        return ResultObj.success(dictDtos);
    }

    /**
     * @Author 钱敏杰
     * @Description 根据当前code获取当前节点数据
     * @Date 2019/1/28 10:09
     * @Param [code]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/getDictBycode")
    public ResultObj getDictBycode(String code){
        SysDictDto dictDto = sysDictService.getByCode(code);
        return ResultObj.success(dictDto);
    }
}
