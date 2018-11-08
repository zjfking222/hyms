package com.hy.dto;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/8 8:21
 * @Description:从sap接收技能等级信息
 */
public class SapSkillLevelDto {

    //员工号
    private String pernr;
    //技能等级类型
    private String z_jnlx;
    //名称
    private String z_jnmc;
    //技能等级
    private String z_jndj;

    public String getPernr() {
        return pernr;
    }

    public void setPernr(String pernr) {
        this.pernr = pernr;
    }

    public String getZ_jnlx() {
        return z_jnlx;
    }

    public void setZ_jnlx(String z_jnlx) {
        this.z_jnlx = z_jnlx;
    }

    public String getZ_jnmc() {
        return z_jnmc;
    }

    public void setZ_jnmc(String z_jnmc) {
        this.z_jnmc = z_jnmc;
    }

    public String getZ_jndj() {
        return z_jndj;
    }

    public void setZ_jndj(String z_jndj) {
        this.z_jndj = z_jndj;
    }
}
