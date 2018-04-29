package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.OpinionDto;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpinionController {

    @Autowired
    private OpinionService opinionService;

    @PostMapping("/handInOpinion")
    public ResultObj handInOpinion(OpinionDto opinionDto)
    {
        return opinionService.insertOpinion(opinionDto)
                ? ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
}
