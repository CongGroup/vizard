# Result release control
This module simulates result-release-control committee. It receives shared secrets from Vizard server and reconstructs the aggregation result. RESTful API can be found in [QueryResultController.java](src/main/java/org/conggroup/vizard/resultreleasecontrol/controller/QueryResultController.java) and [PaymentCheckController.java](src/main/java/org/conggroup/vizard/resultreleasecontrol/controller/PaymentCheckController.java).

Configuration properties of this module are listed as following:

```properties
#Spring configurations:
server.port={your.server.port}
spring.application.name=result-release-control

#Vizard configurations:

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