package com.hy.service.qzgz;

import com.hy.dto.RecruitWithTotalPageDto;

public interface RecruitService {
    RecruitWithTotalPageDto getRecruit(int page, int number);
    Integer getTotalPageOfRecruit(int number);
}
