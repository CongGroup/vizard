# Comparison test server
This module provides a mock server which is used in the comparison test. The module itself is not a part of Vizard's architecture.

Similar to Vizard server, it consumes queries from Kafka server and does aggregation computation locally. For generating mock data, RESTful APIs can be found in [MockDataController.java](src/main/java/org/conggroup/vizard/comparsion/controller/MockDataController.java). Schemes used in back-end databases are provided in [vizard-db-test.sql](vizard-db-test.sql).

Configuration properties of this module are listed as following:

```properties
#Spring configurations:
server.port={your.server.port}
spring.application.name=comparison-server
spring.datasource.url={your.datasource.url}
spring.datasource.username={your.datasource.username}
spring.datasource.password={your.datasource.password}
spring.jpa.database={database.type}
spring.jpa.database-platform={database.platform}
spring.jpa.show-sql=false
spring.jpa.hibernate.ddl-auto=update

#Vizard configurations:

#Server number, can be either 0 or 1
vizard.server.serverNo = 0

#Value for the modulator M: 2^mBits, default 64
vizard.crypto.operator.mBits = 64

#Secret master key for PRF
vizard.crypto.prf.aesKey ={your.AES.key}

#Length for PRF ciphertext in byte unit, default 8
vizard.crypto.prf.bytes = 8

#URL for the first kafka broker
vizard.kafka.bootstrap-server.0 = {your.kafka.broker.url.0}

#URL for the second kafka broker
vizard.kafka.bootstrap-server.1 = {your.kafka.broker.url.1}
```