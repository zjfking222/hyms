package com.hy.dto;

/**
 *   nHrmResources 新增的角色用户组 n = new
 *   rHrmResources 移除的角色用户组 r = remove
 */

public class CrmBusinesstypeUserDto {

    private int btid;
    private HrmResourceDto[] nHrmResource;
    private CrmBusinesstypeUserDelDto[] rHrmResource;

    public int getBtid() {
        return btid;
    }

    public void setBtid(int btid) {
        this.btid = btid;
    }

    public HrmResourceDto[] getnHrmResource() {
        return nHrmResource;
    }

    public void setnHrmResource(HrmResourceDto[] nHrmResource) {
        this.nHrmResource = nHrmResource;
    }

    public CrmBusinesstypeUserDelDto[] getrHrmResource() {
        return rHrmResource;
    }

    public void setrHrmResource(CrmBusinesstypeUserDelDto[] rHrmResource) {
        this.rHrmResource = rHrmResource;
    }
}
