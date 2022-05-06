
If we run a Java application with heap configuration options (for example, -Xms and -Xmx), then there a few other tricks to find the specified values.

For instance, here's how jps reports those values:

> jps -lvm 

> jcmd 1 VM.command_line

> java -XX:+PrintFlagsFinal -version | grep -Ei "maxheapsize|maxram"

> java -XX:+UnlockDiagnosticVMOptions -XX:+PrintFlagsFinal -version

-Xmixed           mixed mode execution (default)
-Xint             interpreted mode execution only
-Xbootclasspath:  set search path for bootstrap classes and resources -Xbootclasspath/a:                   append to end of bootstrap class path -Xbootclasspath/p:                   prepend in front of bootstrap class path -Xnoclassgc       disable class garbage collection -Xloggc:    log GC status to a file with time stamps
-Xbatch           disable background compilation
-Xms              set initial Java heap size
-Xmx              set maximum Java heap size
-Xss              set java thread stack size
-Xprof            output cpu profiling data
-Xfuture          enable strictest checks, anticipating future default
-Xrs              reduce use of OS signals by Java/VM (see documentation)
-Xdock:name=      override default application name displayed in dock -Xdock:icon=                   override default icon displayed in dock -Xcheck:jni       perform additional checks for JNI functions -Xshare:off	      do not attempt to use shared class data -Xshare:auto      use shared class data if possible (default) -Xshare:on	      require using shared class data, otherwise fail.  The -X options are non-standard and subject to change without notice. 

https://alvinalexander.com/blog/post/java/java-xmx-xms-memory-heap-size-control/

https://solr.apache.org/guide/8_3/monitoring-solr-with-prometheus-and-grafana.html

https://www.convertir-unites.info/convertisseur-d-unites.php?type=bytes
