project(vizard-crypto-native)
cmake_minimum_required(VERSION 3.5)

enable_language(C CXX)
set(CMAKE_CXX_STANDARD 14)

find_package(Java REQUIRED)
find_package(JNI REQUIRED)

INCLUDE_DIRECTORIES(${JNI_INCLUDE_DIRS})

if(WIN32)
    set(OS_SPECIFIC_DIR_NAME "windows_64")
elseif(APPLE)
    set(OS_SPECIFIC_DIR_NAME "osx_64")
else()
    set(OS_SPECIFIC_DIR_NAME "linux_64")
endif()

set(TARGET_OUTPUT_DIR ${CMAKE_CURRENT_SOURCE_DIR}/../resources/natives/${OS_SPECIFIC_DIR_NAME})

file(MAKE_DIRECTORY ${TARGET_OUTPUT_DIR})
if(WIN32)
    set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${TARGET_OUTPUT_DIR})
else()
    set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${TARGET_OUTPUT_DIR})
endif()

set(CMAKE_C_FLAGS "-ffunction-sections -msse2 -msse4.1 -maes -mpclmul -Wfatal-errors -pthread -Wno-strict-overflow -fPIC -Wno-ignored-attributes")
set(CMAKE_CXX_FLAGS "${CMAKE_C_FLAGS} -std=c++14")

# Select flags.
SET(CMAKE_CXX_FLAGS_RELEASE "-O3 -DNDEBUG")
SET(CMAKE_CXX_FLAGS_RELWITHDEBINFO "-O2 -g -ggdb -rdynamic")
SET(CMAKE_CXX_FLAGS_DEBUG "-O0 -g -ggdb -DDEBUG")

set(DPFSRCS
        dpf/common/AES.cpp
        dpf/common/Defines.cpp
        dpf/common/PRNG.cpp
        dpf/common/DPF.cpp)

set(PRFSRCS
        prf/common/AES.cpp
        prf/common/PRF.cpp)

add_executable(dpf_test ${DPFSRCS} dpf/test.cpp)
add_library(dpf SHARED ${DPFSRCS} dpf/dpf_jni.cpp)

add_executable(prf_test ${PRFSRCS} prf/test.cpp)
add_library(prf SHARED ${PRFSRCS} prf/prf_jni.cpp)

if(WIN32)
    set_target_properties(dpf PROPERTIES PREFIX "")
    set_target_properties(prf PROPERTIES PREFIX "")
endif()