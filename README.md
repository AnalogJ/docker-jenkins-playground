```
Jenkins.instance.pluginManager.plugins.collect {
    "${it.getShortName()}:${it.getVersion()}"
}.sort().each{println(it)}
```
