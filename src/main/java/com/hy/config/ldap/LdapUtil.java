package com.hy.config.ldap;

import com.hy.model.LdapDepartment;
import com.hy.model.LdapStaff;
import com.unboundid.ldap.sdk.*;
import com.unboundid.ldap.sdk.migrate.ldapjdk.LDAPSearchConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class LdapUtil {

    private static LDAPConnection connection = null;

    private static LdapConfig ldapConfig;
    private static String baseDn;
    private static String uName;

    @Autowired
    public void setLdapConfig(LdapConfig config){
        ldapConfig = config;
    }
    @PostConstruct
    public void init() {
        baseDn = ldapConfig.getBaseDn();
        uName = ldapConfig.getuName();
    }

//    static {
//
//    }
    /**
     * 获取部门信息
     */
    public static List<LdapDepartment> getDepartment(){
        List<LdapDepartment> resultList = new ArrayList<>();
        Filter filter = Filter.createEqualityFilter("objectClass","organizationalUnit");
        SearchRequest searchRequest = new SearchRequest(baseDn, SearchScope.SUB,filter);
        SearchResult searchResult;
        try {
            searchResult = connection.search(searchRequest);
            if (null != searchResult && searchResult.getEntryCount()>0) {
                for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                    resultList.add(new LdapDepartment(entry.getAttributeValue("description"),
                            entry.getAttributeValue("ou"),
                            entry.getAttributeValue(""),
                            entry.getAttributeValue(""),
                            entry.getAttributeValue(""),
                            entry.getAttributeValue("distinguishedName")));
                }
            }
        } catch (LDAPSearchException e) {
            e.printStackTrace();
            System.out.println("[ERROR]部门信息获取失败!");
        }
        return resultList;
    }
    /**
     * 获取用户信息
     */
    public static List<LdapStaff> getStaff(){
        List<LdapStaff> resultList = new ArrayList<>();
        Filter filter = Filter.createEqualityFilter("objectClass","person");
        SearchRequest searchRequest = new SearchRequest(baseDn,SearchScope.SUB,filter);
        SearchResult searchResult;
        try {
            searchResult=connection.search(searchRequest);
            if (null != searchResult && searchResult.getEntryCount() > 0) {
                for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                    resultList.add(new LdapStaff(entry.getAttributeValue("sAMAccountName"),
                            entry.getAttributeValue("sn"),
                            "",
                            entry.getAttributeValue("mail"),
                            entry.getAttributeValue("mobile"),
                            "",
                            "",
                            entry.getAttributeValue("title"),
                            entry.getAttributeValue("distinguishedName")));
                }
            }
        } catch (LDAPSearchException e) {
            e.printStackTrace();
            System.out.println("[ERROR]用户信息获取失败!");
        }
        return resultList;
    }
    /**
     * 新增部门
     */
    public static void addDepartment(LdapDepartment department) {

        String entryDN = department.getDn() + "," + baseDn;
        String name = department.getName();
        String id = department.getId();
        try {
            SearchResultEntry entry = connection.getEntry(entryDN);

            if (entry == null) {
                ArrayList<Attribute> attributes = new ArrayList<>();
                attributes.add(new Attribute("objectClass", "top", "organizationalUnit"));
                attributes.add(new Attribute("ou", name));
                attributes.add(new Attribute("description", id));
                connection.add(entryDN, attributes);
                System.out.println("[ADD]组织[" + name + "(" + department.getId() + ")]创建成功！");
            } else {
                System.out.println("[ADD]组织[" + name + "(" + department.getId() + ")]已存在！");
            }
        } catch (Exception e) {
            System.out.println("[ERROR]创建组织["+ name + "("+ department.getId()+ ")]创建失败！");
            e.printStackTrace();
        }
    }
    /**
     * 新增用户
     */
    public static void addStaff(LdapStaff staff){
        String entryDN = staff.getDn() + "," + baseDn;
        String name = staff.getName();
        String id = staff.getId();
        try {
            SearchResultEntry entry = connection.getEntry(entryDN);

            if (entry == null) {
                ArrayList<Attribute> attributes = new ArrayList<>();
                attributes.add(new Attribute("objectClass","organizationalPerson","person","user","top"));

                if(null != staff.getName() && !staff.getName().equals("")){
                    attributes.add(new Attribute("sn",name));
                    attributes.add(new Attribute("description",name));
                }
                else{
                    attributes.add(new Attribute("sn","(空)"));
                    attributes.add(new Attribute("description","(空)"));
                }
                attributes.add(new Attribute("cn",id));
                attributes.add(new Attribute("sAMAccountName",id));

                if(null != staff.getPhone() && !staff.getPhone().equals("")){
                    attributes.add(new Attribute("mobile",staff.getPhone()));
                }
                if(null != staff.getDuty() && !staff.getDuty().equals("")){
                    attributes.add(new Attribute("title",staff.getDuty()));
                }
                if(null != staff.getEmail() && !staff.getEmail().equals("")){
                    attributes.add(new Attribute("mail",staff.getEmail()));
                }
                attributes.add(new Attribute("userPrincipalName",id + uName));

                connection.add(entryDN, attributes);
                System.out.println("[ADD]用户[" + name +"("+ staff.getId()+")]创建成功！");
            } else {
                System.out.println("[ADD]用户[" + name + "(" +staff.getId() + ")]已存在！");
            }
        } catch (Exception e) {
            System.out.println("[ERROR]用户["+ name + "(" +staff.getId() + ")]创建失败");
            e.printStackTrace();
        }
    }
    /**
     *  修改用户的组织架构
     */
    public static void moveStaff(LdapStaff staff){

        try{
            SearchResultEntry entry = connection.getEntry(staff.getDn());
            String newDn;
            if(Math.abs(staff.getNewDn().length() - staff.getId().length()) ==  3){
                newDn = baseDn;
            }
            else{
                newDn = staff.getNewDn().substring(4 + staff.getId().length())+"," + baseDn;
            }
            connection.modifyDN(staff.getDn(), "CN="+staff.getId(),
                    true, newDn);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("[ERROR]用户[" + staff.getName()+ "(" +staff.getId() + ")]组织架构修改失败！");
        }
    }
    /**
     *  修改用户的信息
     */
    public static void setStaff(LdapStaff staff){
        try{
            SearchResultEntry entry = connection.getEntry(staff.getDn());
            //修改用户：AD域电话号码不再同步
//            if(null != staff.getPhone() && !staff.getPhone().equals("")){
//                connection.modify(staff.getDn(), new Modification(ModificationType.REPLACE, "mobile", staff.getPhone()));
//            }
            if(null != staff.getDuty() && !staff.getDuty().equals("")){
                connection.modify(staff.getDn(), new Modification(ModificationType.REPLACE, "title", staff.getDuty()));
            }
            if(null != staff.getEmail() && !staff.getEmail().equals("")){
                connection.modify(staff.getDn(), new Modification(ModificationType.REPLACE, "mail", staff.getEmail()));
            }
            if(null != staff.getName() && !staff.getName().equals("")){
                connection.modify(staff.getDn(), new Modification(ModificationType.REPLACE, "sn", staff.getName()));
                connection.modify(staff.getDn(), new Modification(ModificationType.REPLACE, "description", staff.getName()));
            }else{
                connection.modify(staff.getDn(), new Modification(ModificationType.REPLACE, "sn", "(空)"));
                connection.modify(staff.getDn(), new Modification(ModificationType.REPLACE, "description", "(空)"));
            }
            System.out.println("[MODIFIED]用户[" + staff.getName()+ "(" +staff.getId() + ")]修改成功！");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("[ERROR]用户[" + staff.getName()+ "(" +staff.getId() + ")]修改失败！");
        }
    }
    /**
     *  删除用户
     */
    public static void delStaff(LdapStaff staff){
        try {
            SearchResultEntry entry = connection.getEntry(staff.getDn());
            connection.delete(staff.getDn());
            System.out.println("[DELETE]用户[" + staff.getName()+ "(" +staff.getId() + ")]删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR]用户[" + staff.getName()+ "(" +staff.getId() + ")]删除失败！");
        }
    }

    /**
     *  删除部门
     */
    public static void delDepartment(LdapDepartment department){
        try {
            SearchResultEntry entry = connection.getEntry(department.getDn());
            connection.delete(department.getDn());
            System.out.println("[DELETE]部门[" + department.getName()+ "(" +department.getId() + ")]删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR]部门[" + department.getName()+ "(" +department.getId() + ")]删除失败！");
        }
    }

    /**
     *  通过完整的姓名来搜索工号等基本信息（用于AD域改密平台）
     */
    public static List<LdapStaff> searchStaffByName(String name){

        Filter filter = Filter.createEqualityFilter("sn", name);
        SearchRequest searchRequest = new SearchRequest(baseDn,SearchScope.SUB,filter);
        SearchResultEntry searchResultEntry;
        SearchResult searchResult;
        List<LdapStaff> resultList = new ArrayList<>();
        try {
            searchResult=connection.search(searchRequest);
            if (null != searchResult && searchResult.getEntryCount() > 0) {
                for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                    String[] depStr = entry.getAttributeValue("distinguishedName").split("OU=");
                    resultList.add(new LdapStaff(entry.getAttributeValue("sAMAccountName"),
                            entry.getAttributeValue("sn"),
                            "",
                            entry.getAttributeValue("mail"),
                            entry.getAttributeValue("mobile"),
                            "",
                            depStr[1].substring(0,depStr[1].length()-1),
                            entry.getAttributeValue("title"),
                            entry.getAttributeValue("distinguishedName")));
                }
            }
        } catch (LDAPSearchException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     *  通过员工号获取员工信息
     */
    public static LdapStaff getStaffByUid(String uid){

        Filter filter = Filter.createEqualityFilter("sAMAccountName", uid);
        SearchRequest searchRequest = new SearchRequest(baseDn,SearchScope.SUB,filter);
        SearchResultEntry searchResultEntry;
        SearchResult searchResult;
        LdapStaff ldapStaff = null;
        try {
            searchResult=connection.search(searchRequest);
            if (null != searchResult && searchResult.getEntryCount() > 0) {
                for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                    String[] depStr = entry.getAttributeValue("distinguishedName").split("OU=");
                    ldapStaff = new LdapStaff(entry.getAttributeValue("sAMAccountName"),
                            entry.getAttributeValue("sn"),
                            "",
                            entry.getAttributeValue("mail"),
                            entry.getAttributeValue("mobile"),
                            "",
                            depStr[1].substring(0,depStr[1].length()-1),
                            entry.getAttributeValue("title"),
                            entry.getAttributeValue("distinguishedName"));
                }
            }
        } catch (LDAPSearchException e) {
            e.printStackTrace();
        }
        return ldapStaff;
    }

    public static String getBaseDn() {
        return baseDn;
    }
}
