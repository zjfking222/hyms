package com.hy.controller.oa;

import com.hy.common.ResultObj;
import com.hy.service.oa.HrmResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oa")
public class HrmResourceController {

    @Autowired
    private HrmResourceService hrmResourceService;

    @PostMapping("/hr/getSearch")
    public ResultObj getSearchHr(String search){
        return ResultObj.success(hrmResourceService.selectHrByLike(search,search));
    }
}
