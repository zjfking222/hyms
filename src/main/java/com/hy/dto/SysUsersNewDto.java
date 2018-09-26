package com.hy.dto;

public class SysUsersNewDto {
    private int uid;
    private String name;
    private SysUsersDto[] nHrmResources;
    private SysRolesUserDelDto[] rHrmResources;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SysUsersDto[] getnHrmResources() {
        return nHrmResources;
    }

    public void setnHrmResources(SysUsersDto[] nHrmResources) {
        this.nHrmResources = nHrmResources;
    }

    public SysRolesUserDelDto[] getrHrmResources() {
        return rHrmResources;
    }

    public void setrHrmResources(SysRolesUserDelDto[] rHrmResources) {
        this.rHrmResources = rHrmResources;
    }
}
