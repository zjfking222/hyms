package com.hy.service.oa;

import com.hy.model.HrmResource;

import java.util.List;

public interface HrmResourceService {
    HrmResource findByLoginId(String loginid);
    List<HrmResource> selectHrByLike(String loginid, String lastname);
    List<HrmResource> selectHrLike(String loginid,String lastname);
}
