package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.RecruitDto;
import com.hy.dto.RecruitWithTotalPageDto;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    @PostMapping("/getRecruit")
    public ResultObj getRecruit(int page,int number){
        RecruitWithTotalPageDto recruitWithTotalPageDto = recruitService.getRecruit(page, number);
        return recruitWithTotalPageDto == null?
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER):
                ResultObj.success(recruitWithTotalPageDto);
    }

    @PostMapping("/addRecruit")
    public ResultObj addRecruit(RecruitDto recruitDto)
    {
        return recruitService.addRecruit(recruitDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
}
