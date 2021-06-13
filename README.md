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


# References
- https://docs.oracle.com/javase/jndi/tutorial/ldap/ext/src/StartTls.java

