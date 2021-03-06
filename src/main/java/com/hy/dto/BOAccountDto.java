package com.hy.dto;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 9:26
 * @Description:BO账号dto
 */
public class BOAccountDto {

    //主键
    private Integer id;
    //账号id
    private String accountid;
    //账号类别
    private String type;
    //账号密码
    private String password;
    //描述
    private String description;
    //是否已选中：true 选中；false 未选中
    private boolean checked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
