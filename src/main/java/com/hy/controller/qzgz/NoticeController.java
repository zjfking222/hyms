package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.NoticeInfoDto;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.NoticeService;
import com.sun.org.apache.xml.internal.security.signature.reference.ReferenceSubTreeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qzgz")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    private final int GROUNDING=1;

    @PostMapping("/web/getNotice")
    public ResultObj getNotice(){

        return  ResultObj.success(noticeService.getNotice(GROUNDING));
    }
    @PostMapping("/admin/insertNotice")
    public ResultObj insertNotice(NoticeInfoDto noticeInfoDto){
        return ResultObj.success(noticeService.insertNotice(noticeInfoDto));
    }
    @PostMapping("/admin/deleteNotice")
    public ResultObj deleteNotice(int id){
        return ResultObj.success(noticeService.deleteNotice(id));
    }
    @PostMapping("/admin/updateNotice")
    public ResultObj updateNotice(NoticeInfoDto noticeInfoDto){
        return noticeService.updateNotice(noticeInfoDto)?ResultObj.success()
                :ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/admin/selectByCreater")
    public ResultObj selectByCreater(int creater){
        return ResultObj.success(noticeService.selectByCreater(creater));
    }
    @PostMapping("/admin/totalPage")
    public ResultObj totalPage(){
        return ResultObj.success(noticeService.totalPage());
    }
}

