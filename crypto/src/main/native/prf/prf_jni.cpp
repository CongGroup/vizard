#include "common/PRF.h"
#include "prf_jni.h"

#define N_BYTES 16

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

JNIEXPORT jbyteArray JNICALL Java_org_conggroup_vizard_crypto_components_prf_PRF_execGen
        (JNIEnv *env,
         jclass thisClass,
         jbyteArray seed,
         jlong input,
         jint bytes) {

    std::vector<uint8_t> seed_v = as_vector(env, seed);

    jbyteArray jKey = nullptr;

    if(seed_v.size() != N_BYTES) {
        env->ThrowNew(env->FindClass("java/lang/Exception"), "Seed is not 16 bytes long");
        return jKey;
    }

    if(bytes > N_BYTES) {
        env->ThrowNew(env->FindClass("java/lang/Exception"), "Number of bytes should be less or equal than 16");
        return jKey;
    }

    PRF prf = PRF(seed_v);
    auto tmp = prf.Gen(input, bytes);

    jKey = as_jbyte_array(env, tmp);
    return jKey;
}