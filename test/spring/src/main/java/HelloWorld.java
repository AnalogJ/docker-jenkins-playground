import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.test.LdapTestUtils;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.ldap.core.support.DefaultTlsDirContextAuthenticationStrategy;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
public class HelloWorld {
    public static final Log LOG = LogFactory.getLog(HelloWorld.class);

    public void sayHello() {

        // ATTEMPT 3: use code from
        // https://www.programcreek.com/java-api-examples/?code=apache%2Franger%2Franger-master%2Fsecurity-admin%2Fsrc%2Fmain%2Fjava%2Forg%2Fapache%2Franger%2Fsecurity%2Fhandler%2FRangerAuthenticationProvider.java
        // https://github.com/spring-cloud/spring-cloud-dataflow/issues/963
        //
        // Try to get non-STARTTLS authentication working with Spring-LDAP/Spring-Security
        //  > AbstractTlsDirContextAuthenticationStrategy does LDAP with StartTLS (usually on port 389)
        // > DefaultTlsDirContextAuthenticationStrategy
        // RESULT: STARTTLS shows up in the log files, "TLS confidentiality required" is returned if DefaultTlsDirContextAuthenticationStrategy is commented out

        try {
            LdapContextSource ldapContextSource = new DefaultSpringSecurityContextSource("ldap://ldap.codebiner.com:10389");
            ldapContextSource.setUserDn("cn=admin,dc=codebiner,dc=com");
            ldapContextSource.setPassword("GoodNewsEveryone");
            //        ldapContextSource.setReferral(rangerLdapReferral);
            ldapContextSource.setCacheEnvironmentProperties(false);
            ldapContextSource.setAnonymousReadOnly(true);
            ldapContextSource.setPooled(true);
            if (true) {
                ldapContextSource.setPooled(false);
                ldapContextSource.setAuthenticationStrategy(new DefaultTlsDirContextAuthenticationStrategy());
            }
            ldapContextSource.afterPropertiesSet();


            //set user filter
            //from https://github.com/jenkinsci/ldap-plugin/blob/9b10ce430e5bf26845cb7aa028943d79a1da0de5/src/main/java/jenkins/security/plugins/ldap/LDAPConfiguration.java#L582
            FilterBasedLdapUserSearch ldapUserSearch = new FilterBasedLdapUserSearch("ou=people,dc=codebiner,dc=com", "uid={0}", ldapContextSource);
            ldapUserSearch.setSearchSubtree(true);
            String[] attrs = ["*", "+"]
            ldapUserSearch.setReturningAttributes(attrs);

            //Set user dn
            BindAuthenticator bindAuthenticator = new BindAuthenticator(ldapContextSource);
            String[] userDnPatterns = [ "ou=people,dc=codebiner,dc=com" ];
            bindAuthenticator.setUserDnPatterns(userDnPatterns);

            //set group dn

            LdapAuthenticationProvider ldapAuthenticationProvider = new LdapAuthenticationProvider(bindAuthenticator, new DefaultLdapAuthoritiesPopulator(ldapContextSource, "ou=people,dc=codebiner,dc=com"));
//            LdapAuthenticationProvider ldapAuthenticationProvider = new LdapAuthenticationProvider(bindAuthenticator, defaultLdapAuthoritiesPopulator);
            ldapAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken("fry", "fry"));

            LOG.info("Hello World!");
        } catch (Exception e) {
            LOG.debug("AD Authentication Failed:", e);
        }
    }
}
