import java.util.*;
import javax.naming.directory.*;
import javax.naming.*;
import javax.naming.ldap.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.security.cert.Certificate;
///https://roufid.com/java-ldap-ssl-authentication/




class LdapTest
{

    // Your program begins with a call to main().
    // Prints "Hello, World" to the terminal window.
    public static void main(String args[])
    {

        // ATTEMPT 2: use code from https://docs.oracle.com/javase/jndi/tutorial/ldap/ext/starttls.html#TLS%20with%20Simple%20Authentication
        // Try to get start tls authentication working
        // RESULT:


        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.PROVIDER_URL, "ldap://ldap.codebiner.com:10389");
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        
        LdapContext ctx = null;
        StartTlsResponse tls = null;
        try {
            // Openning the connection
            ctx = new InitialLdapContext(env, null);

            tls = (StartTlsResponse) ctx.extendedOperation(new StartTlsRequest());
            tls.negotiate();

            // Perform simple client authentication
            ctx.addToEnvironment(Context.SECURITY_AUTHENTICATION, "simple");
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, "cn=admin,dc=codebiner,dc=com");
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, "GoodNewsEveryone");


            // Perform read
            System.out.println(ctx.getAttributes(""));

            // Stop TLS
            tls.close();

            // Use your context here...
            System.out.println("Successfully authenticated??");
            // Close the context when we're done
            ctx.close();
        } catch (javax.naming.AuthenticationException e) {
            e.printStackTrace();
        }  catch (NamingException e) {

            System.out.println("Problem occurs during context initialization !");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }





        //////////////////////////////////////////


        // ATTEMPT 1: replicate issue, force STARTTLS shoudl fail, pass otherwise.
        // RESULT: Success
        // Setting the LDAP connection information
//        Hashtable<String, String> env = new Hashtable<String, String>();
//        env.put(Context.PROVIDER_URL, "ldap://ldap.codebiner.com:10389");
//        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//        env.put(Context.SECURITY_AUTHENTICATION, "simple");
//        env.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=codebiner,dc=com");
//        env.put(Context.SECURITY_CREDENTIALS, "GoodNewsEveryone");
//
//        DirContext ctx = null;
//
//        try {
//            // Openning the connection
//            ctx = new InitialDirContext(env);
//            ctx.getAttributes("");
//            // Use your context here...
//            System.out.println("Successfully authenticated??");
//        } catch (NamingException e) {
//            System.out.println("Problem occurs during context initialization !");
//            e.printStackTrace();
//        }

    }
}

class SampleVerifier implements HostnameVerifier {
    public boolean verify(String hostname, SSLSession session) {
        System.out.println("Checking: " + hostname + " in");
        try {
            Certificate[] cert = session.getPeerCertificates();
            for (int i = 0; i < cert.length; i++) {
                System.out.println(cert[i]);
            }
        } catch (SSLPeerUnverifiedException e) {
            return false;
        }

        return true; 	    // Never do this
    }
}
