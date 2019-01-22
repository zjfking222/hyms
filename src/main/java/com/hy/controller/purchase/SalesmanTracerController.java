package com.hy.controller.purchase;

import com.hy.service.purchase.SalesmanTracerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/22 7:59
 * @Description:业务员、跟单员关系表controller
 */
@RestController
@RequestMapping("/purchase")
public class SalesmanTracerController {

    @Autowired
    private SalesmanTracerService salesmanTracerService;

//    @PostMapping("/salesmanTracer/get")
}
