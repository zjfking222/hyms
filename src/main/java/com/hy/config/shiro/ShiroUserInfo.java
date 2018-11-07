package com.hy.config.shiro;

public class ShiroUserInfo {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String depname;
    private String duty;
    private String password;

    public ShiroUserInfo() {
    }

    public ShiroUserInfo(String id, String name, String password, String depname) {
        this(id, name, password, depname, null, null, null);
    }

    public ShiroUserInfo(String id, String name, String password, String depname, String duty, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.depname = depname;
        this.duty = duty;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

}
