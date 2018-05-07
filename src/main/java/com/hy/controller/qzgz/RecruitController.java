package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecruitController {

    @PostMapping("/getRecruit")
    public ResultObj getRecruit(int page,int number){
        return null;
    }
}
