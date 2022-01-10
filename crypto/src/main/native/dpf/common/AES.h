#pragma once

#include <cstdint>

#include "Defines.h"

#include <wmmintrin.h>

class AES {
public:
    AES();
    explicit AES(const uint8_t* key);
    explicit AES(const std::vector<uint8_t> &key);

    void setKey(const block& key);
    void setKey(const uint8_t* key);

    void encryptECB(const block& plaintext, block& ciphertext) const;
    block encryptECB(const block& plaintext) const {
        block tmp;
        encryptECB(plaintext, tmp);
        return tmp;
    }
    void encryptECB_MMO(const block& plaintext, block& ciphertext) const;
    block encryptECB_MMO(const block& plaintext) const {
        block tmp;
        encryptECB_MMO(plaintext, tmp);
        return tmp;
    }

    void encryptCTR(uint64_t baseIdx, uint64_t blockLength, block * ciphertext) const;
    block key;
private:
    block mRoundKeysEnc[11];
};

