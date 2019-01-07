package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.SysDictDto;
import com.hy.service.system.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/27 15:26
 * @Description:衢州新闻类型controller
 */
@RestController
@RequestMapping("/qzgz")
public class NewsTypeController {
    @Autowired
    private SysDictService sysDictService;

    @PostMapping("/admin/getNewsType")
    //查询新闻类型
    public ResultObj getNewsType(String code){
        return ResultObj.success(sysDictService.selectByCode(code));
    }

    @PostMapping("/admin/getByCode")
    //根据编码查询当前节点
    public ResultObj getByCode(String code){
        SysDictDto dictDto = sysDictService.getByCode(code);
        return ResultObj.success(dictDto);
    }

    @PostMapping("/admin/addNewsType")
    //新增新闻类型
    public ResultObj addNewsType(SysDictDto sysDictDto){
        sysDictService.addSysDict(sysDictDto);
        return ResultObj.success();
    }

    @PostMapping("/admin/setNewsType")
    //修改新闻类型
    public ResultObj setNewsType(SysDictDto sysDictDto){
        sysDictService.updateSysDict(sysDictDto);
        return ResultObj.success();
    }
    @PostMapping("/admin/delNewsType")
    //删除新闻类型
    public ResultObj delNewsType(int id){
        sysDictService.deleteSysDict(id);
        return ResultObj.success();
    }

    @PostMapping("/web/getNewsTypeWeb")
    //查询新闻类型
    public ResultObj getNewsTypeWeb(String code){
        return ResultObj.success(sysDictService.selectByCode(code));
    }
}
