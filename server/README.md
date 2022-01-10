# Server
This module is the implementation of Vizard Server that encrypts data submitted by data owners and stores it into back-end database. It also stores DPF keys submitted by data owners, and processes the queries issued by data consumers. 

The server module does not contain standalone RESTful APIs. It interacts with other modules through Kafka clusters. Schemes used in back-end databases are provided in [vizard-db.sql](vizard-db.sql).

Configuration properties of this module are listed as following:

```properties
#Spring configurations:
server.port={your.server.port}
spring.application.name=server
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

#Value for beaver triples
#Default value for server 0:
#2944828295, 784461936, 3650874859
vizard.crypto.beaver.a = 2944828295
vizard.crypto.beaver.b = 784461936
vizard.crypto.beaver.c = 3650874859

#First secret AES Key for DPF
vizard.crypto.dpf.aesKeyL= {your.dpf.AES.key.L}

#Second secret AES Key for DPF
vizard.crypto.dpf.aesKeyR= {your.dpf.AES.key.R}

#Property `v` for `Type OR` DPF operation
#Default for server 0: -65534
vizard.crypto.dpf.v = -65534

#Secret master key for PRF
vizard.crypto.prf.aesKey ={your.AES.key}

#Length for PRF ciphertext in byte unit, default 8
vizard.crypto.prf.bytes = 8

#Value for the modulator M: 2^mBits, default 64
vizard.crypto.operator.mBits = 64

#URL for the first kafka broker
vizard.kafka.bootstrap-server.0 = {your.kafka.broker.url.0}

#URL for the second kafka broker
vizard.kafka.bootstrap-server.1 = {your.kafka.broker.url.1}

#Number of members in result-release-control committee, default 30
vizard.rrc.num-of-members = 30

#Ratio for required parts/total parts in Shamir's Secret Sharing, default 0.67
vizard.crypto.sss.requiredPartsRatio = 0.67
```