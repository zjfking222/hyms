package com.hy.dto;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/8 8:11
 * @Description:从sap接收资格证书信息
 */
public class SapQuaCertificateDto {

    //员工号
    private String pernr;
    //资格证书类型
    private String z_zglx;
    //资格证书名称
    private String z_zgmc;
    //资格证书到期日
    private String z_zsdq;

    public String getPernr() {
        return pernr;
    }

    public void setPernr(String pernr) {
        this.pernr = pernr;
    }

    public String getZ_zglx() {
        return z_zglx;
    }

    public void setZ_zglx(String z_zglx) {
        this.z_zglx = z_zglx;
    }

    public String getZ_zgmc() {
        return z_zgmc;
    }

    public void setZ_zgmc(String z_zgmc) {
        this.z_zgmc = z_zgmc;
    }

    public String getZ_zsdq() {
        return z_zsdq;
    }

    public void setZ_zsdq(String z_zsdq) {
        this.z_zsdq = z_zsdq;
    }
}
