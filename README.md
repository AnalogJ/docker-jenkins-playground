```
Jenkins.instance.pluginManager.plugins.collect {
    "${it.getShortName()}:${it.getVersion()}"
}.sort().each{println(it)}
```

# What I've Tried

| Jenkins Version | LDAP Plugin Version | Other Changes | Results |
| --- | --- | --- | --- |
| 2.277.4-lts | 2.7 | | fails force STARTTLS |
| latest | 2.7 | | fails force STARTTLS |

| 2.277.4-lts | 2.7 | adding certificate to the  JVM | fails force STARTTLS |
| - | - | export the Java Truststore, check if the certificate matches | stupidly difficult to test this |
| - | - | write a POC Java class to test hardcoded LDAP auth | working (replicates issue, force STARTTLS fails, passes otherwise) |
| - | - | write a POC Java class to test StartTLS hardcoded LDAP auth | working, force STARTTLS passes, passes otherwise as well :party: |


# References
- https://docs.oracle.com/javase/jndi/tutorial/ldap/ext/src/StartTls.java
- https://docs.oracle.com/javase/jndi/tutorial/ldap/ext/starttls.html#TLS%20with%20Simple%20Authentication
