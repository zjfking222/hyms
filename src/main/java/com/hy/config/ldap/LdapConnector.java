package com.hy.config.ldap;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.migrate.ldapjdk.LDAPSearchConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LdapConnector {

    @Autowired
    private LdapConfig ldapConfig;

    private LDAPConnection connection;

    private static LdapConnector ldapConnector = new LdapConnector();

    public static LdapConnector getInstance(){
        return ldapConnector;
    }

    private LdapConnector(){
        getConnection();
    }

    /**
     * 打开ldap连接
     */
    public LDAPConnection getConnection() {
        if (connection == null) {
            try {
                String ldapHost = ldapConfig.getHost();
                int ldapPort = Integer.valueOf(ldapConfig.getPort());
                String ldapBindDN = ldapConfig.getAccount();
                String ldapPassword = ldapConfig.getPassword();
                connection = new LDAPConnection(ldapHost, ldapPort, ldapBindDN, ldapPassword);
                LDAPSearchConstraints ldsc = new LDAPSearchConstraints();
                ldsc.setMaxResults(Integer.valueOf(ldapConfig.getMaxResult()));
                return connection;
            } catch (LDAPException e) {
                System.out.println("[ERROR]连接LDAP出现错误：\n" + e.getMessage());
                return null;
            }
        }
        else{
            if(!connection.isConnected()){
                String ldapBindDN = ldapConfig.getBaseDn();
                String ldapPassword = ldapConfig.getPassword();
                try {
                    connection.bind(ldapBindDN, ldapPassword);
                } catch (LDAPException e) {
                    e.printStackTrace();
                }
            }
            return connection;
        }
    }
}
