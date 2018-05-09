package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.OpinionDto;
import com.hy.dto.OpinionForAdminWithPageDto;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qzgz")
public class OpinionController {

    @Autowired
    private OpinionService opinionService;

    @PostMapping("/web/handInOpinion")
    public ResultObj handInOpinion(OpinionDto opinionDto)
    {
        return opinionService.addOpinion(opinionDto) ?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/admin/getOpinion")
    public ResultObj getOpinion(int page, int number,
                                @RequestParam(required = false) String state)
    {
        OpinionForAdminWithPageDto opinion = opinionService.getOpinion(page,number,state);
        return opinion != null ?
                ResultObj.success(opinion):
                ResultObj.error(ResultCode.ERROR_NO_RESOURCE);
    }
    @PostMapping("/admin/setStateOfOpinion")
    public ResultObj setStateOfOpinion(int id, String state)
    {
        return opinionService.setStateOfOpinion(id,state)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
}
