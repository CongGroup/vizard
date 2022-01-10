#pragma once

#include <cstdint>
#include <vector>

#include <immintrin.h>
#include <wmmintrin.h>

typedef  __m128i block;
typedef  union {
    __m128i reg;
    uint8_t arr[16];
} reg_arr_union;

class AES {
public:
    AES();
    explicit AES(const uint8_t* key);
    explicit AES(const std::vector<uint8_t> &key);

    void setKey(const block& key);
    void setKey(const uint8_t* key);

    void encryptECB_MMO(const block& plaintext, block& ciphertext) const;
    block encryptECB_MMO(const block& plaintext) const {
        block tmp;
        encryptECB_MMO(plaintext, tmp);
        return tmp;
    }
private:
    block mRoundKeysEnc[11];
};

