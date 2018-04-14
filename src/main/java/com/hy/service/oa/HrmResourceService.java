package com.hy.service.oa;

import com.hy.model.HrmResource;

public interface HrmResourceService {
    HrmResource findByLoginId(String loginid);
}
