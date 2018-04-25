package com.hy.dto;

import java.util.List;

public class OpinionForAdminWithPageDto {
    private List<OpinionForAdminDto> opinions;
    private int TotalPage;

    public OpinionForAdminWithPageDto(List<OpinionForAdminDto> opinions, int totalPage) {
        this.opinions = opinions;
        TotalPage = totalPage;
    }

    public List<OpinionForAdminDto> getOpinions() {
        return opinions;
    }

    public void setOpinions(List<OpinionForAdminDto> opinions) {
        this.opinions = opinions;
    }

    public int getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(int totalPage) {
        TotalPage = totalPage;
    }
}
