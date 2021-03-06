package com.hy.service.qzgz;

import com.hy.dto.RecruitDto;
import com.hy.dto.RecruitWithTotalPageDto;

public interface RecruitService {
    RecruitWithTotalPageDto getRecruit(int page, int number);
    Integer getTotalPageOfRecruit(int number);
    boolean addRecruit(RecruitDto recruitDto);
    boolean setRecruit(RecruitDto recruitDto);
    boolean delRecruit(int id);
    RecruitDto getRecruit(int id);
}
