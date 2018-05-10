package com.hy.service.qzgz;

import com.hy.dto.OpinionDto;
import com.hy.dto.OpinionForAdminWithPageDto;

public interface OpinionService {
    boolean addOpinion(OpinionDto opinionDto);
    OpinionForAdminWithPageDto getOpinion(int page, int number, String state);
    int getTotalPageOfOpinion(int number, String state);
    boolean setStateOfOpinion(int id, String state);
}
