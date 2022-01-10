# Data consumer
This is the proxy module for data consumer. It provides RESTful APIs for issuing queries to Vizard server. RESTful APIs can be found in [QueryController.java](src/main/java/org/conggroup/vizard/dataconsumer/controller/QueryController.java).

Configuration properties of this module are listed as following:

```properties
#Spring configurations:
server.port={your.server.port}
spring.application.name=data-consumer

#Vizard configurations:

#Value for the modulator M: 2^mBits, default 64
vizard.crypto.operator.mBits = 64

#URL for the first kafka broker
vizard.kafka.bootstrap-server.0 = {your.kafka.broker.url.0}

#URL for the second kafka broker
vizard.kafka.bootstrap-server.1 = {your.kafka.broker.url.1}
```