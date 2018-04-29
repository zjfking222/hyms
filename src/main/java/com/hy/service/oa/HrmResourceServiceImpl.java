package com.hy.service.oa;

import com.hy.mapper.oa.HrmResourceMapper;
import com.hy.model.HrmResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "HrmResource")
public class HrmResourceServiceImpl implements HrmResourceService {

    @Autowired
    private HrmResourceMapper hrmResourceMapper;

    @Override
    public HrmResource findByLoginId(String loginid) {
//        测试
        HrmResource hrmResource = new HrmResource();
        hrmResource.setId(1000);
        hrmResource.setLastname("詹继锋");
        hrmResource.setLoginid("zhanjf");
        hrmResource.setPassword("a0462110ce6cfcacc706b775d1ef159e".toUpperCase());
        return hrmResource;
//        return hrmResourceMapper.findByLoginId(loginid);
    }
}
