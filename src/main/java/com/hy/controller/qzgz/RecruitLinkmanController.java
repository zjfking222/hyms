package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.QzgzRecruitLinkmanDto;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.RecruitLinkmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/qzgz")
public class RecruitLinkmanController {
    @Autowired
    private RecruitLinkmanService linkmanService;

    @PostMapping("/admin/getLinkman")
    //查询招聘联系人信息并分页
    public ResultObj getLinkman(@RequestParam(required = false) String value, int page, int pageSize,
                                @RequestParam(required = false) String sort,
                                @RequestParam(required = false) String dir){
        Map<String, Object> map = new HashMap<>();
        map.put("data", linkmanService.getLinkman(page, pageSize, value, sort, dir));
        map.put("total", linkmanService.getLinkmanTotal(value));
        return ResultObj.success(map);
    }

    @PostMapping("/admin/addLinkman")
    //新增招聘联系人信息
    public ResultObj addLinkman(QzgzRecruitLinkmanDto linkmanDto){
        return linkmanService.addLinkman(linkmanDto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @PostMapping("/admin/setLinkman")
    //更新招聘联系人信息
    public ResultObj setLinkman(QzgzRecruitLinkmanDto linkmanDto){
        return linkmanService.setLinkman(linkmanDto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @PostMapping("/admin/delLinkman")
    //删除招聘联系人信息
    public ResultObj delLinkman(int id){
        return linkmanService.delLinkman(id) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }

    @PostMapping("/web/getLinkmanAll")
    //移动端查询招聘联系人信息
    public ResultObj getLinkmanAll(){
        return ResultObj.success(linkmanService.getLinkmanAll());
    }
}
