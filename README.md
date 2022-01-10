# Vizard
This repository contains the artifact for the CCS 2022 submission "Vizard: A Metadata-hiding Data Analytics System with End-to-End Policy Controls".

Vizard is a metadata-hiding analytic system that enables privacy-hardening and enforceable data control for owners. The prototype is interfaced with Apache Kafka, built with a tailored suite of lightweight cryptographic tools and designs that help us to efficiently handle analytic queries over continuous data streams.

This repository is structured as follows. For more information, see the individual README files in the corresponding folders.

## Repository Structure
This repository consists of six modules:
- [**crypto**](crypto): The cryptographic library for Vizard. It contains the implementation of the symmetric homomorphic stream encryption (SHSE) scheme, Distributed Point Function (DPF), Pedersen commitment, Shamir’s secret sharing, and other miscellaneous cryptographic functions.
- [**data-owner**](data-owner): The proxy module for data owner. It provides RESTful APIs for data and DPF-key submission, and for mock data generation.
- [**data-consumer**](data-consumer): The proxy module for data consumer. It provides RESTful APIs for issuing queries to Vizard server.
- [**server**](server): Vizard server implementation that encrypts data submitted by data owners and stores it into back-end database. It also stores DPF keys submitted by data owners, and processes the queries issued by data consumers.
- [**result-release-control**](result-release-control): The module for simulating result-release-control committee. It receives shared secrets from Vizard server and reconstructs the aggregation result.
- [**comparison-test-server**](comparison-test-server): The module that provides a mock server which is used in the comparison test.

Code snippets used for microbenchmarks are put in `test` folders in the source directory of each module.

## Build Vizard Project
To build this project you will need the following prerequisites on your system:
- Maven
- A JDK >= Java version 17
- cmake + build-essential (Optional, required for building DPF and PRF native library)

The project artifacts can be build with maven.
```
mvn clean package
```
It will resolve all dependencies and build the project. Afterwards the project ccontains the following java artifacts
 - **data-owner:** `data-owner/target/data-owner-1.0.jar`
 - **data-consumer:** `data-consumer/target/data-consumer-1.0.jar`
 - **server:** `server/target/server-1.0.jar`
 - **result-release-control:** `result-release-control/target/result-release-control-1.0.jar`
  - **comparison-test-server:** `comparison-test-server/target/comparison-test-server-1.0.jar`

To install the Vizard libraries in the local maven repository run:
```
mvn clean install
```
Pre-built native libraries for DPF and PRF are shipped with the source code. If you wish to build them by yourself, you can switch to the profile `compile-native-lib`:
```
mvn clean package -Pcompile-native-lib
```

## Run Vizard Project
### Prerequisites
To run Vizard project, you will need following prerequisites:
- Hardware:
  - x86-based instances with SSE 4.1 and AES-NI support (can be either physical or virtual machine)
  - Two Kafka clusters for inter-server communication
  - Two back-end databases (i.e. MySQL) for data storage
- Software:
  - A JDK >= Java version 17

### Configuration
Properties required for running Vizard project are defined in `application.properties` in each module. These files serve as the templates for configuration. It's recommended to follow the Spring Framework's guidance to put the filled configuration files in the `/config` subdirectory of the corresponding jar's directory.

See the individual README files in the corresponding modules for details about properties.

### Run it!
After successfully building the project, you can run each module with simply one line of command:

```
java -jar {jar.file.name}.jar
```

## Disclaimer
This implementation is a research prototype and should not be used in production.
