# Crypto
The cryptographic library for Vizard. It contains the implementation of the symmetric homomorphic stream encryption (SHSE) scheme, Distributed Point Function (DPF), Pedersen commitment, Shamir’s secret sharing, and other miscellaneous cryptographic functions.

Pre-built native libraries for DPF and PRF are shipped with the source code. If you wish to build them by yourself, you can switch to the profile `compile-native-lib`:
```
mvn clean package -Pcompile-native-lib
```