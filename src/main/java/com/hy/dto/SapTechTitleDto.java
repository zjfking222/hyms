package com.hy.dto;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/8 8:05
 * @Description:从sap接收职称信息
 */
public class SapTechTitleDto {

    //员工号
    private String pernr;
    //职称类型
    private String z_zclx;
    //名称
    private String z_mc;

    public String getPernr() {
        return pernr;
    }

    public void setPernr(String pernr) {
        this.pernr = pernr;
    }

    public String getZ_zclx() {
        return z_zclx;
    }

    public void setZ_zclx(String z_zclx) {
        this.z_zclx = z_zclx;
    }

    public String getZ_mc() {
        return z_mc;
    }

    public void setZ_mc(String z_mc) {
        this.z_mc = z_mc;
    }
}
