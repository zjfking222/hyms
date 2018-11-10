package com.hy.dto;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/8 8:01
 * @Description:从sap接收合同信息
 */
public class SapContractInfoDto {

    //员工号
    private String pernr;
    //合同类型
    private String zhtype;
    //合同截止日期
    private String zhtjz;

    public String getPernr() {
        return pernr;
    }

    public void setPernr(String pernr) {
        this.pernr = pernr;
    }

    public String getZhtype() {
        return zhtype;
    }

    public void setZhtype(String zhtype) {
        this.zhtype = zhtype;
    }

    public String getZhtjz() {
        return zhtjz;
    }

    public void setZhtjz(String zhtjz) {
        this.zhtjz = zhtjz;
    }
}
