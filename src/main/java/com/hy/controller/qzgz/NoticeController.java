package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.service.qzgz.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    private final int GROUNDING=1;

    @PostMapping("/getNotice")
    public ResultObj getNotice(){
        return  ResultObj.success(noticeService.getNotice(GROUNDING));
    }
}
