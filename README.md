# Prefect Audience : Fluent Logger for Java

## Overview

Many web/mobile applications generate huge amount of event logs (c,f. login, logout, purchase, follow, etc).  Analyzing these event logs can be quite valuable for improving services.  However, collecting these logs easily and reliably is a challenging task.

Fluentd solves the problem by having: easy installation, small footprint, pluginsreliable buffering, log forwarding, etc.

**fluent-logger-java** is a Java library (based on fluent-logger-java from [https://github.com/fluent]), to record events via Fluentd, from your Java application.


## Main changes from the original fluent-logger-java
  * Use SL4J (Version 1.7.5)
  * New Reconnector - ConstantDelayReconnector - based on a constant value
  * Update reconnection logic to force a close()/connect() after an outage (Network issues, Fluentd server restart, ...)

## Requirements

Java >= 1.6

## Install from Maven2 repository

Fluent Logger for Java is released on Fluent Maven2 repository.  You can configure your pom.xml as follows to use it:

    <dependencies>
      ...
      <dependency>
        <groupId>com.perfectaudience</groupId>
        <artifactId>pa-fluent-logger</artifactId>
        <version>${logger.version}</version>
      </dependency>
      ...
    </dependencies>

## Links
  * **Perfect Audience**  
    * Website: [http://www.perfectaudience.com](http://www.perfectaudience.com)  
    * Github: [https://github.com/PerfectAudience](https://github.com/PerfectAudience)  

  * **Fluent**  
    * Website: [http://github.com/fluent/fluentd](http://github.com/fluent/fluentd)  
    * Github: [https://github.com/fluent](https://github.com/fluent)  

### Install from Github repository

You can get latest source code using git.

    $ git clone git@github.com:fluent/fluent-logger-java.git
    $ cd fluent-logger-java
    $ mvn assembly:assembly

You will get the fluent logger jar file in fluent-logger-java/target 
directory.  File name will be fluent-logger-${logger.version}-jar-with-dependencies.jar.
For more detail, see pom.xml.

**Replace ${logger.version} with the current version of Fluent Logger for Java.**

## Quickstart

The following program is a small example of Fluent Logger for Java.

    import java.util.HashMap;
    import java.util.Map;
    import org.fluentd.logger.FluentLogger;

    public class Main {
        private static FluentLogger LOG = FluentLogger.getLogger("app");

        public void doApplicationLogic() {
            // ...
            Map<String, String> data = new HashMap<String, String>();
            data.put("from", "userA");
            data.put("to", "userB");
            LOG.log("follow", data);
            // ...
        }
    }

To create Fluent Logger instances, users need to invoke getLogger method in 
FluentLogger class like org.slf4j, org.log4j logging libraries.  The method 
should be called only once.  By default, the logger assumes fluent daemon is 
launched locally.  You can also specify remote logger by passing the following 
options.  

    // for remote fluentd
    private static FluentLogger LOG = FluentLogger.getLogger("app", "remotehost", port);

Then, please create the events like this.  This will send the event to fluentd, 
with tag 'app.follow' and the attributes 'from' and 'to'.

Close method in FluentLogger class should be called explicitly when application 
is finished.  The method close socket connection with the fluentd.

    FluentLogger.close();

## License

Apache License, Version 2.0
