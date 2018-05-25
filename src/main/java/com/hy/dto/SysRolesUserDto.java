package com.hy.dto;

/**
 *   nHrmResources 新增的角色用户组 n = new
 *   rHrmResources 移除的角色用户组 r = remove
 */
public class SysRolesUserDto {

//    private int id;
    private int rid;
    private String name;
    private HrmResourceDto[] nHrmResources;
    private SysRolesUserDelDto[] rHrmResources;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HrmResourceDto[] getnHrmResources() {
        return nHrmResources;
    }

    public void setnHrmResources(HrmResourceDto[] nHrmResources) {
        this.nHrmResources = nHrmResources;
    }

    public SysRolesUserDelDto[] getrHrmResources() {
        return rHrmResources;
    }

    public void setrHrmResources(SysRolesUserDelDto[] rHrmResources) {
        this.rHrmResources = rHrmResources;
    }
}
