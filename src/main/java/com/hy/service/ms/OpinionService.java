package com.hy.service.ms;

import com.hy.dto.OpinionDto;
import com.hy.dto.OpinionForAdminWithPageDto;

public interface OpinionService {
    boolean insertOpinion(OpinionDto opinionDto);
    OpinionForAdminWithPageDto getOpinion(int page, int number, String state);
    int getTotalPageOfOpinion(int number, String state);
    boolean setStateOfOpinion(int id, String state);
}
