package com.hy.config.ldap;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.migrate.ldapjdk.LDAPSearchConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LdapConnector {

    @Autowired
    private static LdapConfig ldapConfig;
    private static LDAPConnection connection;

    @Autowired
    public void setLdapConfig(LdapConfig ldapConfig) {
        LdapConnector.ldapConfig = ldapConfig;
    }

    public static synchronized LDAPConnection getConnection() throws LDAPException {
        if (connection == null || !connection.isConnected()) {
            connection = new LDAPConnection(ldapConfig.getHost(), Integer.valueOf(ldapConfig.getPort()), ldapConfig.getAccount(), ldapConfig.getPassword());
            LDAPSearchConstraints ldsc = new LDAPSearchConstraints();
            ldsc.setMaxResults(Integer.valueOf(ldapConfig.getMaxResult()));
        }
        return connection;
    }
}
