package com.hy.config.ldap;

import com.hy.model.LdapDepartment;
import com.hy.model.LdapStaff;
import com.unboundid.ldap.sdk.*;
import com.unboundid.util.LDAPTestUtils;
import com.unboundid.util.ssl.SSLUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import javax.annotation.PostConstruct;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

@Component
public class LdapUtil {
    private static final Logger logger = LoggerFactory.getLogger(LdapUtil.class);
    @Autowired
    private static LdapConfig ldapConfig;
    private static String baseDn;
    private static String uName;

    @Autowired
    public void setLdapConfig(LdapConfig config) {
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
    public static List<LdapDepartment> getDepartment() {
        List<LdapDepartment> resultList = new ArrayList<>();
        Filter filter = Filter.createEqualityFilter("objectClass", "organizationalUnit");
        SearchRequest searchRequest = new SearchRequest(baseDn, SearchScope.SUB, filter);
        SearchResult searchResult;
        try {
            searchResult = LdapConnector.getConnection().search(searchRequest);
            if (null != searchResult && searchResult.getEntryCount() > 0) {
                for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                    resultList.add(new LdapDepartment(entry.getAttributeValue("description"),
                            entry.getAttributeValue("ou"),
                            entry.getAttributeValue(""),
                            entry.getAttributeValue(""),
                            entry.getAttributeValue(""),
                            entry.getAttributeValue("distinguishedName")));
                }
            }
        } catch (LDAPException e) {
            e.printStackTrace();
            System.out.println("[ERROR]部门信息获取失败!");
        }
        return resultList;
    }

    /**
     * 获取用户信息
     */
    public static List<LdapStaff> getStaff() {
        List<LdapStaff> resultList = new ArrayList<>();
        Filter filter = Filter.createEqualityFilter("objectClass", "person");
        SearchRequest searchRequest = new SearchRequest(baseDn, SearchScope.SUB, filter);
        SearchResult searchResult;
        try {
            searchResult = LdapConnector.getConnection().search(searchRequest);
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
        } catch (LDAPException e) {
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
            SearchResultEntry entry = LdapConnector.getConnection().getEntry(entryDN);

            if (entry == null) {
                ArrayList<Attribute> attributes = new ArrayList<>();
                attributes.add(new Attribute("objectClass", "top", "organizationalUnit"));
                attributes.add(new Attribute("ou", name));
                attributes.add(new Attribute("description", id));
                LdapConnector.getConnection().add(entryDN, attributes);
                System.out.println("[ADD]组织[" + name + "(" + department.getId() + ")]创建成功！");
            } else {
                System.out.println("[ADD]组织[" + name + "(" + department.getId() + ")]已存在！");
            }
        } catch (Exception e) {
            System.out.println("[ERROR]创建组织[" + name + "(" + department.getId() + ")]创建失败！");
            e.printStackTrace();
        }
    }

    /**
     * 新增用户
     */
    public static void addStaff(LdapStaff staff) {
        String entryDN = staff.getDn() + "," + baseDn;
        String name = staff.getName();
        String id = staff.getId();
        try {
            SearchResultEntry entry = LdapConnector.getConnection().getEntry(entryDN);

            if (entry == null) {
                ArrayList<Attribute> attributes = new ArrayList<>();
                attributes.add(new Attribute("objectClass", "organizationalPerson", "person", "user", "top"));

                if (null != staff.getName() && !staff.getName().equals("")) {
                    attributes.add(new Attribute("sn", name));
                    attributes.add(new Attribute("description", name));
                } else {
                    attributes.add(new Attribute("sn", "(空)"));
                    attributes.add(new Attribute("description", "(空)"));
                }
                attributes.add(new Attribute("cn", id));
                attributes.add(new Attribute("sAMAccountName", id));

                if (null != staff.getPhone() && !staff.getPhone().equals("")) {
                    attributes.add(new Attribute("mobile", staff.getPhone()));
                }
                if (null != staff.getDuty() && !staff.getDuty().equals("")) {
                    attributes.add(new Attribute("title", staff.getDuty()));
                }
                if (null != staff.getEmail() && !staff.getEmail().equals("")) {
                    attributes.add(new Attribute("mail", staff.getEmail()));
                }
                attributes.add(new Attribute("userPrincipalName", id + uName));

                LdapConnector.getConnection().add(entryDN, attributes);
                System.out.println("[ADD]用户[" + name + "(" + staff.getId() + ")]创建成功！");
            } else {
                System.out.println("[ADD]用户[" + name + "(" + staff.getId() + ")]已存在！");
            }
        } catch (Exception e) {
            System.out.println("[ERROR]用户[" + name + "(" + staff.getId() + ")]创建失败");
            e.printStackTrace();
        }
    }

    /**
     * 修改用户的组织架构
     */
    public static void moveStaff(LdapStaff staff) {

        try {
            SearchResultEntry entry = LdapConnector.getConnection().getEntry(staff.getDn());
            String newDn;
            if (Math.abs(staff.getNewDn().length() - staff.getId().length()) == 3) {
                newDn = baseDn;
            } else {
                newDn = staff.getNewDn().substring(4 + staff.getId().length()) + "," + baseDn;
            }
            LdapConnector.getConnection().modifyDN(staff.getDn(), "CN=" + staff.getId(),
                    true, newDn);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR]用户[" + staff.getName() + "(" + staff.getId() + ")]组织架构修改失败！");
        }
    }

    /**
     * 修改用户的信息
     */
    public static void setStaff(LdapStaff staff) {
        try {
            SearchResultEntry entry = LdapConnector.getConnection().getEntry(staff.getDn());
            //修改用户：AD域电话号码不再同步
//            if(null != staff.getPhone() && !staff.getPhone().equals("")){
//                connection.modify(staff.getDn(), new Modification(ModificationType.REPLACE, "mobile", staff.getPhone()));
//            }
            if (null != staff.getDuty() && !staff.getDuty().equals("")) {
                LdapConnector.getConnection().modify(staff.getDn(), new Modification(ModificationType.REPLACE, "title", staff.getDuty()));
            }
            if (null != staff.getEmail() && !staff.getEmail().equals("")) {
                LdapConnector.getConnection().modify(staff.getDn(), new Modification(ModificationType.REPLACE, "mail", staff.getEmail()));
            }
            if (null != staff.getName() && !staff.getName().equals("")) {
                LdapConnector.getConnection().modify(staff.getDn(), new Modification(ModificationType.REPLACE, "sn", staff.getName()));
                LdapConnector.getConnection().modify(staff.getDn(), new Modification(ModificationType.REPLACE, "description", staff.getName()));
            } else {
                LdapConnector.getConnection().modify(staff.getDn(), new Modification(ModificationType.REPLACE, "sn", "(空)"));
                LdapConnector.getConnection().modify(staff.getDn(), new Modification(ModificationType.REPLACE, "description", "(空)"));
            }
            System.out.println("[MODIFIED]用户[" + staff.getName() + "(" + staff.getId() + ")]修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR]用户[" + staff.getName() + "(" + staff.getId() + ")]修改失败！");
        }
    }

    /**
     * 删除用户
     */
    public static void delStaff(LdapStaff staff) {
        try {
            SearchResultEntry entry = LdapConnector.getConnection().getEntry(staff.getDn());
            LdapConnector.getConnection().delete(staff.getDn());
            System.out.println("[DELETE]用户[" + staff.getName() + "(" + staff.getId() + ")]删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR]用户[" + staff.getName() + "(" + staff.getId() + ")]删除失败！");
        }
    }

    /**
     * 删除部门
     */
    public static void delDepartment(LdapDepartment department) {
        try {
            SearchResultEntry entry = LdapConnector.getConnection().getEntry(department.getDn());
            LdapConnector.getConnection().delete(department.getDn());
            System.out.println("[DELETE]部门[" + department.getName() + "(" + department.getId() + ")]删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ERROR]部门[" + department.getName() + "(" + department.getId() + ")]删除失败！");
        }
    }

    /**
     * 通过完整的姓名来搜索工号等基本信息（用于AD域改密平台）
     */
    public static List<LdapStaff> searchStaffByName(String name) {

        Filter filter = Filter.createEqualityFilter("sn", name);
        SearchRequest searchRequest = new SearchRequest(baseDn, SearchScope.SUB, filter);
        SearchResultEntry searchResultEntry;
        SearchResult searchResult;
        List<LdapStaff> resultList = new ArrayList<>();
        try {
            searchResult = LdapConnector.getConnection().search(searchRequest);
            if (null != searchResult && searchResult.getEntryCount() > 0) {
                for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                    String[] depStr = entry.getAttributeValue("distinguishedName").split("OU=");
                    resultList.add(new LdapStaff(entry.getAttributeValue("sAMAccountName"),
                            entry.getAttributeValue("sn"),
                            "",
                            entry.getAttributeValue("mail"),
                            entry.getAttributeValue("mobile"),
                            "",
                            depStr[1].substring(0, depStr[1].length() - 1),
                            entry.getAttributeValue("title"),
                            entry.getAttributeValue("distinguishedName")));
                }
            }
        } catch (LDAPException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * 通过员工号获取员工信息
     */
    public static LdapStaff getStaffByUid(String uid) {

        Filter filter = Filter.createEqualityFilter("sAMAccountName", uid);
        SearchRequest searchRequest = new SearchRequest(baseDn, SearchScope.SUB, filter);
        SearchResultEntry searchResultEntry;
        SearchResult searchResult;
        LdapStaff ldapStaff = null;
        try {
            searchResult = LdapConnector.getConnection().search(searchRequest);
            if (null != searchResult && searchResult.getEntryCount() > 0) {
                for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                    String[] depStr = entry.getAttributeValue("distinguishedName").split("OU=");
                    ldapStaff = new LdapStaff(entry.getAttributeValue("sAMAccountName"),
                            entry.getAttributeValue("sn"),
                            "",
                            entry.getAttributeValue("mail"),
                            entry.getAttributeValue("mobile"),
                            "",
                            depStr[1].substring(0, depStr[1].length() - 1),
                            entry.getAttributeValue("title"),
                            entry.getAttributeValue("distinguishedName"));
                }
            }
        } catch (LDAPException e) {
            e.printStackTrace();
        }
        return ldapStaff;
    }

    public static String getBaseDn() {
        return baseDn;
    }

    /**
     * @Author 钱敏杰
     * @Description 去ad域验证账号密码
     * @Date 2018/11/14 15:54
     * @Param [name, password]
     * @return boolean
     **/
    public static boolean checkAuthentication(String name, String password){
        boolean flag = true;
        LDAPConnection connection = null;
        try {
            //根据账号密码连接ad域，若能建立连接，即表示账号密码正确
            connection = new LDAPConnection(ldapConfig.getHost(), Integer.valueOf(ldapConfig.getPort()), name+ldapConfig.getuName(), password);
        } catch (LDAPException e) {
            logger.error("AD域密码验证失败！", e);
            flag= false;
        }finally {
            //关闭连接
            if(connection != null){
                connection.close();
            }
        }
        return flag;
    }

    /**
     * @Author 钱敏杰
     * @Description 查询AD域数据
     * @Date 2018/11/16 9:08
     * @Param [base, scope, filter]
     * @return com.unboundid.ldap.sdk.SearchResult
     **/
    public static SearchResult searchResult(String base, SearchScope scope, Filter filter) throws LDAPException{
        //若dn为空，则使用默认值
        if(StringUtils.isEmpty(base)){
            base = baseDn;
        }
        //若范围为空，则使用默认值
        if(scope == null){
            scope = SearchScope.SUB;
        }
        SearchRequest searchRequest = new SearchRequest(base, scope, filter);
        SearchResult searchResult = LdapConnector.getConnection().search(searchRequest);
        return searchResult;
    }

    /**
     * @Author 钱敏杰
     * @Description 修改相应dn下的单条属性
     * @Date 2018/11/19 9:23
     * @Param [base, name, value]
     * @return boolean true：成功；false：失败
     **/
    public static void updateAttribute(String dn, String name, String value) throws LDAPException{
        Modification modification = new Modification(ModificationType.REPLACE, name, value);
        LDAPResult result =  LdapConnector.getConnection().modify(dn, modification);
        //检查是否返回值，失败则抛异常
        LDAPTestUtils.assertResultCodeEquals(result, ResultCode.SUCCESS);
    }

    /**
     * @Author 钱敏杰
     * @Description 用管理员权限直接更改AD域密码，需要有证书
     * @Date 2018/11/26 9:07
     * @Param [dn, newPassword]
     * @return void
     **/
    public static void resetPassword(String dn, String newPassword) throws Exception{
        LDAPConnection connection = null;
        InputStream in = null;
        try {
            //初始化秘钥库
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null);
            //解析证书文件
            File file = ResourceUtils.getFile("classpath:files" + File.separator + ldapConfig.getCer());
            in = new FileInputStream(file);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate cert = cf.generateCertificate(in);
            //秘钥库使用证书
            trustStore.setCertificateEntry("cert", cert);
            //使用X509证书格式解析
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
            tmf.init(trustStore);
            TrustManager[] trustManagers = tmf.getTrustManagers();
            //初始化ssl工具类
            SSLUtil sslUtil = new SSLUtil(trustManagers);
            //使用ssl建立连接，端口写死了
            SSLSocketFactory socketFactory = sslUtil.createSSLSocketFactory();
            connection = new LDAPConnection(socketFactory,ldapConfig.getHost(), 636);
            //绑定管理员账号
            connection.bind(ldapConfig.getAccount(), ldapConfig.getPassword());
            //生成更改密码的修改操作对象
            newPassword = "\"" + newPassword + "\"";//密码格式必须加引号
            Modification mod = new Modification(ModificationType.REPLACE,"unicodePwd", newPassword.getBytes("UTF-16LE"));
            //执行修改密码操作
            LDAPResult result =  connection.modify(dn, mod);
            //检查返回值，不成功会抛出异常
            LDAPTestUtils.assertResultCodeEquals(result, ResultCode.SUCCESS);
        }catch (Exception e) {
            throw e;
        }finally {
            //关闭
            if(in != null){
                in.close();
            }
            if(connection != null && connection.isConnected()){
                connection.close();
            }
        }
    }

}
