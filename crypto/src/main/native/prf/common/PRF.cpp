#include <cassert>
#include "PRF.h"

PRF::PRF() {
    // default aes key
    uint8_t fixed_key[16] = {36,156,50,234,92,230,49,9,174,170,205,160,98,236,29,243};
    mAES = AES(fixed_key);
}

PRF::PRF(const std::vector<uint8_t> &seed) : mAES(seed) {}

std::vector<uint8_t> PRF::Gen(int64_t input, int bytes) {
    assert(bytes <= 16);
    block in = _mm_set_epi64x(0, input);

    reg_arr_union tmp;
    tmp.reg = mAES.encryptECB_MMO(in);

    std::vector<uint8_t> res(bytes);

    for(int i=0; i<16; i++) {
        res[i%bytes] = res[i%bytes] ^ tmp.arr[i];
    }

    return res;
}