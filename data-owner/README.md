# Data owner
This is the proxy module for data owner. It provides RESTful APIs for data and DPF-key submission, and for mock data generation. RESTful APIs can be found in [DataProviderController.java](src/main/java/org/conggroup/vizard/dataowner/controller/DataProviderController.java) and [MockDataController.java](src/main/java/org/conggroup/vizard/dataowner/controller/MockDataController.java).

Configuration properties of this module are listed as following:

```properties
#Spring configurations:
server.port={your.server.port}
spring.application.name=data-owner

#Vizard configurations:

#First secret AES Key for DPF
vizard.crypto.dpf.aesKeyL= {your.dpf.AES.key.L}

#Second secret AES Key for DPF
vizard.crypto.dpf.aesKeyR= {your.dpf.AES.key.R}

#URL for the first kafka broker
vizard.kafka.bootstrap-server.0 = {your.kafka.broker.url.0}

#URL for the second kafka broker
vizard.kafka.bootstrap-server.1 = {your.kafka.broker.url.1}
```