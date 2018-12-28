package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.SysDictDto;
import com.hy.enums.ResultCode;
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
    public ResultObj getNewsType(){
        return ResultObj.success(sysDictService.getNewsType());
    }
    @PostMapping("/admin/addNewsType")
    //新增新闻类型
    public ResultObj addNewsType(SysDictDto sysDictDto){
        return sysDictService.addNewsType(sysDictDto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }
    @PostMapping("/admin/setNewsType")
    //修改新闻类型
    public ResultObj setNewsType(SysDictDto sysDictDto){
        return sysDictService.setNewsType(sysDictDto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }
    @PostMapping("/admin/delNewsType")
    //删除新闻类型
    public ResultObj delNewsType(int id){
        return sysDictService.delNewsType(id) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }
}
