package com.hy.service.oa;

import com.hy.dto.SysUsersDto;
import com.hy.mapper.oa.HrmResourceMapper;
import com.hy.model.HrmResource;
import com.hy.service.system.SysUsersService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

@Service(value = "HrmResource")
public class HrmResourceServiceImpl implements HrmResourceService {

    @Autowired
    private HrmResourceMapper hrmResourceMapper;
    @Autowired
    private SysUsersService sysUsersService;

    @Override
    public HrmResource findByLoginId(String loginid) {
//        测试
//        HrmResource hrmResource = new HrmResource();
//        hrmResource.setId(1000);
//        hrmResource.setLastname("詹继锋");
//        hrmResource.setLoginid("zhanjf");
//        hrmResource.setPassword("a0462110ce6cfcacc706b775d1ef159e".toUpperCase());
//        return hrmResource;
        return hrmResourceMapper.findByLoginId(loginid);
    }

    @Override
    public List<HrmResource> selectHrByLike(String loginid, String lastname) {
        return hrmResourceMapper.selectHrByLike(loginid, lastname);
    }

    @Override
    public List<HrmResource> selectHrLike(String loginid, String lastname) {
        HashMap<String, SysUsersDto> map = new HashMap<>();
        List<HrmResource> list = hrmResourceMapper.selectHrByLike(loginid, lastname);
        List<SysUsersDto> dto = sysUsersService.getUsers();
        List<HrmResource> newList=new ArrayList<>();
        IntStream.range(0, dto.size()).forEach(i ->
                map.put(dto.get(i).getOaloginid(), dto.get(i))
        );
        IntStream.range(0, list.size()).forEachOrdered(i->{
            if (map.containsKey(list.get(i).getLoginid())){

            }else {
                newList.add(list.get(i));
            }
        });
        System.out.println(list.size());
        return newList;
    }

}
