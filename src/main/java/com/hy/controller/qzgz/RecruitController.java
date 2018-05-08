package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.RecruitDto;
import com.hy.dto.RecruitWithTotalPageDto;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qzgz")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    @PostMapping("/web/getRecruit")
    public ResultObj getRecruit(int page,int number){
        RecruitWithTotalPageDto recruitWithTotalPageDto = recruitService.getRecruit(page, number);
        return recruitWithTotalPageDto == null?
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER):
                ResultObj.success(recruitWithTotalPageDto);
    }

    @PostMapping("/admin/addRecruit")
    public ResultObj addRecruit(RecruitDto recruitDto)
    {
        return recruitService.addRecruit(recruitDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/admin/setRecruit")
    public ResultObj setRecruit(RecruitDto recruitDto)
    {
        return recruitService.setRecruit(recruitDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/admin/delRecruit")
    public ResultObj delRecruit(int id)
    {
        return recruitService.delRecruit(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
}
