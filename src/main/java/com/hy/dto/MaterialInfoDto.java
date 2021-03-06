package com.hy.dto;

/**
 * @Auther: 钱敏杰
 * @Date: 2019/1/22 10:56
 * @Description:物资信息dto
 */
public class MaterialInfoDto {

    //主键
    private Integer id;
    //申请类别：从数据字典中获取名称保存
    private String applytype;
    //公司名称
    private String companyname;
    //物资类别
    private String mattype;
    //追溯号(批次)
    private String batch;
    //物资名称
    private String materialname;
    //物资描述
    private String materialdes;
    //数量
    private Double amount;
    //单位
    private String unit;
    //业务员员工号
    private String empnum;
    //业务员姓名
    private String empname;
    //申请部门
    private String applydept;
    //申请联系人
    private String applyperson;
    //物资分派日期
    private String dispatchdate;
    //要求到货日期
    private String requireddate;
    //海外到货日期
    private String overseasdate;
    //状态：合同未签订；合同已签订；合同到货；物资装箱；发票到票；已完成；
    private String state;
    //合同号（订单号）
    private String ordernum;
    //供应商名称
    private String supplier;
    //合同签订日期
    private String contractdate;
    //合同到货日期
    private String conarrivaldate;
    //供应商联系人
    private String supplyperson;
    //供应商联系方式
    private String supplycontact;
    //付款方式
    private String payment;
    //付款进度
    private String payprogress;
    //物资到货日期
    private String matarrivaldate;
    //物资未验收原因
    private String unaccreason;
    //物资验收日期
    private String acceptdate;
    //仓库未入库原因
    private String nonstoreason;
    //仓库入库日期
    private String storagedate;
    //装箱日期
    private String packingdate;
    //发票到票日期
    private String invoicedate;
    //备注
    private String remark;
    //已到货数量
    private Double arramount;
    //未到货数量
    private Double unarramount;
    //付款进度1
    private String progress1;
    //付款进度2
    private String progress2;
    //付款进度3
    private String progress3;
    //付款进度4
    private String progress4;
    //付款进度5
    private String progress5;
    //是否完成
    private String complete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplytype() {
        return applytype;
    }

    public void setApplytype(String applytype) {
        this.applytype = applytype;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getMattype() {
        return mattype;
    }

    public void setMattype(String mattype) {
        this.mattype = mattype;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getMaterialname() {
        return materialname;
    }

    public void setMaterialname(String materialname) {
        this.materialname = materialname;
    }

    public String getMaterialdes() {
        return materialdes;
    }

    public void setMaterialdes(String materialdes) {
        this.materialdes = materialdes;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getEmpnum() {
        return empnum;
    }

    public void setEmpnum(String empnum) {
        this.empnum = empnum;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getApplydept() {
        return applydept;
    }

    public void setApplydept(String applydept) {
        this.applydept = applydept;
    }

    public String getApplyperson() {
        return applyperson;
    }

    public void setApplyperson(String applyperson) {
        this.applyperson = applyperson;
    }

    public String getDispatchdate() {
        return dispatchdate;
    }

    public void setDispatchdate(String dispatchdate) {
        this.dispatchdate = dispatchdate;
    }

    public String getRequireddate() {
        return requireddate;
    }

    public void setRequireddate(String requireddate) {
        this.requireddate = requireddate;
    }

    public String getOverseasdate() {
        return overseasdate;
    }

    public void setOverseasdate(String overseasdate) {
        this.overseasdate = overseasdate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getContractdate() {
        return contractdate;
    }

    public void setContractdate(String contractdate) {
        this.contractdate = contractdate;
    }

    public String getConarrivaldate() {
        return conarrivaldate;
    }

    public void setConarrivaldate(String conarrivaldate) {
        this.conarrivaldate = conarrivaldate;
    }

    public String getSupplyperson() {
        return supplyperson;
    }

    public void setSupplyperson(String supplyperson) {
        this.supplyperson = supplyperson;
    }

    public String getSupplycontact() {
        return supplycontact;
    }

    public void setSupplycontact(String supplycontact) {
        this.supplycontact = supplycontact;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPayprogress() {
        return payprogress;
    }

    public void setPayprogress(String payprogress) {
        this.payprogress = payprogress;
    }

    public String getMatarrivaldate() {
        return matarrivaldate;
    }

    public void setMatarrivaldate(String matarrivaldate) {
        this.matarrivaldate = matarrivaldate;
    }

    public String getUnaccreason() {
        return unaccreason;
    }

    public void setUnaccreason(String unaccreason) {
        this.unaccreason = unaccreason;
    }

    public String getAcceptdate() {
        return acceptdate;
    }

    public void setAcceptdate(String acceptdate) {
        this.acceptdate = acceptdate;
    }

    public String getNonstoreason() {
        return nonstoreason;
    }

    public void setNonstoreason(String nonstoreason) {
        this.nonstoreason = nonstoreason;
    }

    public String getStoragedate() {
        return storagedate;
    }

    public void setStoragedate(String storagedate) {
        this.storagedate = storagedate;
    }

    public String getPackingdate() {
        return packingdate;
    }

    public void setPackingdate(String packingdate) {
        this.packingdate = packingdate;
    }

    public String getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(String invoicedate) {
        this.invoicedate = invoicedate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getArramount() {
        return arramount;
    }

    public void setArramount(Double arramount) {
        this.arramount = arramount;
    }

    public Double getUnarramount() {
        return unarramount;
    }

    public void setUnarramount(Double unarramount) {
        this.unarramount = unarramount;
    }

    public String getProgress1() {
        return progress1;
    }

    public void setProgress1(String progress1) {
        this.progress1 = progress1;
    }

    public String getProgress2() {
        return progress2;
    }

    public void setProgress2(String progress2) {
        this.progress2 = progress2;
    }

    public String getProgress3() {
        return progress3;
    }

    public void setProgress3(String progress3) {
        this.progress3 = progress3;
    }

    public String getProgress4() {
        return progress4;
    }

    public void setProgress4(String progress4) {
        this.progress4 = progress4;
    }

    public String getProgress5() {
        return progress5;
    }

    public void setProgress5(String progress5) {
        this.progress5 = progress5;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }
}
