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

## License

Apache License, Version 2.0
