package com.hy.model;

public class LdapDepartment {

    private String id;
    private String name;
    private String delSymbol;
    private String parentId;
    private String parentName;
    private String dn;
    private String newDn;

    public LdapDepartment(){

    }

    public LdapDepartment(String id, String name, String delSymbol, String parentId, String parentName) {
        this.id = id;
        this.name = name;
        this.delSymbol = delSymbol;
        this.parentId = parentId;
        this.parentName = parentName;
    }

    public LdapDepartment(String id, String name, String delSymbol, String parentId, String parentName, String dn) {
        this.id = id;
        this.name = name;
        this.delSymbol = delSymbol;
        this.parentId = parentId;
        this.parentName = parentName;
        this.dn = dn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDelSymbol() {
        return delSymbol;
    }

    public void setDelSymbol(String delSymbol) {
        this.delSymbol = delSymbol;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getNewDn() {
        return newDn;
    }

    public void setNewDn(String newDn) {
        this.newDn = newDn;
    }
}
