package com.hy.dto;

import java.util.List;

public class RecruitWithTotalPageDto {
    private List<RecruitDto> recruits;
    private int totalPage;

    public List<RecruitDto> getRecruits() {
        return recruits;
    }

    public void setRecruits(List<RecruitDto> recruits) {
        this.recruits = recruits;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
