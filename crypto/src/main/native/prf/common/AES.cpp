#include "AES.h"

inline block toBlock(const uint8_t* in) { return _mm_set_epi64x(((uint64_t*)in)[1], ((uint64_t*)in)[0]);}

block keyGenHelper(block key, block keyRcon)
{
    keyRcon = _mm_shuffle_epi32(keyRcon, _MM_SHUFFLE(3, 3, 3, 3));
    key = _mm_xor_si128(key, _mm_slli_si128(key, 4));
    key = _mm_xor_si128(key, _mm_slli_si128(key, 4));
    key = _mm_xor_si128(key, _mm_slli_si128(key, 4));
    return _mm_xor_si128(key, keyRcon);
}

AES::AES() {
    uint8_t zerokey[] = {0,0,0,0, 0,0,0,0, 0,0,0,0 , 0,0,0,0};
    setKey(toBlock(zerokey));
}

AES::AES(const uint8_t* key) {
    setKey(key);
}

AES::AES(const std::vector<uint8_t> &key) {
    setKey(key.data());
}

void AES::setKey(const block& key) {
    mRoundKeysEnc[0] = key;
    mRoundKeysEnc[1] = keyGenHelper(mRoundKeysEnc[0], _mm_aeskeygenassist_si128(mRoundKeysEnc[0], 0x01));
    mRoundKeysEnc[2] = keyGenHelper(mRoundKeysEnc[1], _mm_aeskeygenassist_si128(mRoundKeysEnc[1], 0x02));
    mRoundKeysEnc[3] = keyGenHelper(mRoundKeysEnc[2], _mm_aeskeygenassist_si128(mRoundKeysEnc[2], 0x04));
    mRoundKeysEnc[4] = keyGenHelper(mRoundKeysEnc[3], _mm_aeskeygenassist_si128(mRoundKeysEnc[3], 0x08));
    mRoundKeysEnc[5] = keyGenHelper(mRoundKeysEnc[4], _mm_aeskeygenassist_si128(mRoundKeysEnc[4], 0x10));
    mRoundKeysEnc[6] = keyGenHelper(mRoundKeysEnc[5], _mm_aeskeygenassist_si128(mRoundKeysEnc[5], 0x20));
    mRoundKeysEnc[7] = keyGenHelper(mRoundKeysEnc[6], _mm_aeskeygenassist_si128(mRoundKeysEnc[6], 0x40));
    mRoundKeysEnc[8] = keyGenHelper(mRoundKeysEnc[7], _mm_aeskeygenassist_si128(mRoundKeysEnc[7], 0x80));
    mRoundKeysEnc[9] = keyGenHelper(mRoundKeysEnc[8], _mm_aeskeygenassist_si128(mRoundKeysEnc[8], 0x1B));
    mRoundKeysEnc[10] = keyGenHelper(mRoundKeysEnc[9], _mm_aeskeygenassist_si128(mRoundKeysEnc[9], 0x36));
}

void AES::setKey(const uint8_t* key) {
   setKey(toBlock(key));
}

void AES::encryptECB_MMO(const block& plaintext, block& ciphertext) const {
    ciphertext = _mm_xor_si128(plaintext, mRoundKeysEnc[0]);
    ciphertext = _mm_aesenc_si128(ciphertext, mRoundKeysEnc[1]);
    ciphertext = _mm_aesenc_si128(ciphertext, mRoundKeysEnc[2]);
    ciphertext = _mm_aesenc_si128(ciphertext, mRoundKeysEnc[3]);
    ciphertext = _mm_aesenc_si128(ciphertext, mRoundKeysEnc[4]);
    ciphertext = _mm_aesenc_si128(ciphertext, mRoundKeysEnc[5]);
    ciphertext = _mm_aesenc_si128(ciphertext, mRoundKeysEnc[6]);
    ciphertext = _mm_aesenc_si128(ciphertext, mRoundKeysEnc[7]);
    ciphertext = _mm_aesenc_si128(ciphertext, mRoundKeysEnc[8]);
    ciphertext = _mm_aesenc_si128(ciphertext, mRoundKeysEnc[9]);
    ciphertext = _mm_aesenclast_si128(ciphertext, mRoundKeysEnc[10]);
    ciphertext = _mm_xor_si128(ciphertext, plaintext);
}
