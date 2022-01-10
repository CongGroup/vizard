#include "common/DPF.h"
#include "dpf_jni.h"

#include <cstdint>

#define _logN 33
#define AES_KEY_BYTES 16

jbyteArray as_jbyte_array(JNIEnv *env, const std::vector<uint8_t> &v) {
    int len = v.size();
    jbyteArray arr = env->NewByteArray(len);
    env->SetByteArrayRegion (arr, 0, len, (jbyte *)v.data());
    return arr;
}

std::vector<uint8_t> as_vector(JNIEnv *env, jbyteArray arr) {
    jsize len = env->GetArrayLength(arr);
    std::vector<uint8_t> v(len);
    env->GetByteArrayRegion (arr, 0, len, (jbyte *)v.data());
    return v;
}

JNIEXPORT jbyteArray JNICALL Java_org_conggroup_vizard_crypto_components_dpf_DPF_execGen
        (JNIEnv *env,
         jclass thisClass,
         jlong hash,
         jbyteArray aesKeyL,
         jbyteArray aesKeyR) {

    std::vector<uint8_t> aesKey1 = as_vector(env, aesKeyL);
    std::vector<uint8_t> aesKey2 = as_vector(env, aesKeyR);

    jbyteArray jKey = nullptr;

    if(aesKey1.size() != AES_KEY_BYTES) {
        env->ThrowNew(env->FindClass("java/lang/Exception"), "AESKeyL is not 16 bytes long");
        return jKey;
    }

    if(aesKey2.size() != AES_KEY_BYTES) {
        env->ThrowNew(env->FindClass("java/lang/Exception"), "AESKeyR is not 16 bytes long");
        return jKey;
    }

    DPF dpf = DPF(aesKey1, aesKey2);
    auto alpha = (uint64_t)hash;
    auto keys = dpf.Gen(alpha, _logN);
    auto ka = keys.first;
    auto kb = keys.second;

    std::vector<uint8_t> _keys;
    _keys.reserve(ka.size()+kb.size());
    _keys.insert(_keys.end(),ka.begin(),ka.end());
    _keys.insert(_keys.end(),kb.begin(),kb.end());

    jKey = as_jbyte_array(env, _keys);
    return jKey;
}

JNIEXPORT jint JNICALL Java_org_conggroup_vizard_crypto_components_dpf_DPF_execEval
        (JNIEnv *env,
         jclass thisClass,
         jlong hash,
         jbyteArray aesKeyL,
         jbyteArray aesKeyR,
         jbyteArray jKey,
         jint b) {

    std::vector<uint8_t> aesKey1 = as_vector(env, aesKeyL);
    std::vector<uint8_t> aesKey2 = as_vector(env, aesKeyR);
    std::vector<uint8_t> key = as_vector(env, jKey);

    if(aesKey1.size() != AES_KEY_BYTES) {
        env->ThrowNew(env->FindClass("java/lang/Exception"), "AESKeyL is not 16 bytes long");
        return -1;
    }

    if(aesKey2.size() != AES_KEY_BYTES) {
        env->ThrowNew(env->FindClass("java/lang/Exception"), "AESKeyR is not 16 bytes long");
        return -1;
    }

    DPF dpf = DPF(aesKey1, aesKey2);
    return (jint)dpf.Eval(key, b, hash, _logN);
}
