package com.hy.controller.ms;

import com.hy.common.ResultObj;
import com.hy.service.ms.BusinessTravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessTravelController {

    @Autowired
    private BusinessTravelService businessTravelService;

    @PostMapping("/getBusinessTravel")
    public ResultObj getBusinessTravel()
    {
        return ResultObj
                .success(businessTravelService.getBusinessTravel());
    }
}
