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
        return hrmResourceMapper.findByLoginId(loginid);
    }
}
