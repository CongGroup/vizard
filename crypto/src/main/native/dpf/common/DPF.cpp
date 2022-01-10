#include "DPF.h"
#include "Defines.h"
#include "PRNG.h"
#include "PRG.hpp"

#include <cassert>
#include <iostream>

DPF::DPF() {
    // default aes keys
    uint8_t fixed_key[16] = {36,156,50,234,92,230,49,9,174,170,205,160,98,236,29,243};
    uint8_t fixed_key2[16] = {209,12,199,173,29,74,44,128,194,224,14,44,2,201,110,28};
    mAesL = AES(fixed_key);
    mAesR = AES(fixed_key2);
}

DPF::DPF(const std::vector<uint8_t> &keyL, const std::vector<uint8_t> &keyR) : mAesL(keyL), mAesR(keyR) {}

std::pair<std::vector<uint8_t>, std::vector<uint8_t>> DPF::Gen(uint64_t alpha, int n) {
    assert(n <= 63);
    assert(alpha < (1ULL << n));
    std::vector<uint8_t> ka, kb, CW;
    PRNG p = PRNG::getTestPRNG();
    block s0, s1;
    uint8_t t0, t1;
    p.get((uint8_t *) &s0, sizeof(s0));
    p.get((uint8_t *) &s1, sizeof(s1));
    t0 = getT(s0);
    t1 = !t0;

    s0 = clr(s0);
    s1 = clr(s1);

    ka.insert(ka.end(), (uint8_t*)&s0, ((uint8_t*)&s0) + sizeof(s0));
    ka.push_back(t0);
    kb.insert(kb.end(), (uint8_t*)&s1, ((uint8_t*)&s1) + sizeof(s1));
    kb.push_back(t1);

    for(int i = 0; i < n; i++) {

        block s0L = PRG::get(s0, mAesL);
        uint8_t t0L = getT(s0L);
        s0L = clr(s0L);
        block s0R = PRG::get(s0, mAesR);
        uint8_t t0R = getT(s0R);
        s0R = clr(s0R);

        block s1L = PRG::get(s1, mAesL);
        uint8_t t1L = getT(s1L);
        s1L = clr(s1L);
        block s1R = PRG::get(s1, mAesR);
        uint8_t t1R = getT(s1R);
        s1R = clr(s1R);

        if (alpha & (1ULL << (n-1-i))) {
            //KEEP = R, LOSE = L
            block sCW = s0L ^ s1L;
            uint8_t tLCW = t0L ^ t1L;
            uint8_t tRCW = t0R ^ t1R ^ 1;
            CW.insert(CW.end(), (uint8_t *) &sCW, ((uint8_t *) &sCW) + sizeof(sCW));
            CW.push_back(tLCW);
            CW.push_back(tRCW);

            if(t0) {
                s0 = s0R ^ sCW;
                t0 = t0R ^ tRCW;
            } else {
                s0 = s0R;
                t0 = t0R;
            }

            if(t1) {
                s1 = s1R ^ sCW;
                t1 = t1R ^ tRCW;
            } else {
                s1 = s1R;
                t1 = t1R;
            }

        } else {
            //KEEP = L, LOSE = R
            block sCW = s0R ^ s1R;
            uint8_t tLCW = t0L ^ t1L ^ 1;
            uint8_t tRCW = t0R ^ t1R;
            CW.insert(CW.end(), (uint8_t *) &sCW, ((uint8_t *) &sCW) + sizeof(sCW));
            CW.push_back(tLCW);
            CW.push_back(tRCW);

            if(t0) {
                s0 = s0L ^ sCW;
                t0 = t0L ^ tLCW;
            } else {
                s0 = s0L;
                t0 = t0L;
            }

            if(t1) {
                s1 = s1L ^ sCW;
                t1 = t1L ^ tLCW;
            } else {
                s1 = s1L;
                t1 = t1L;
            }
        }

    }

    block finalblock = 1 - ConvertBlock(s0) + ConvertBlock(s1);
    if (t1) {
        finalblock *= -1;
    }
    CW.insert(CW.end(), (uint8_t*)&finalblock, ((uint8_t*)&finalblock) + sizeof(finalblock));

    ka.insert(ka.end(), CW.begin(), CW.end());
    kb.insert(kb.end(), CW.begin(), CW.end());

    return std::make_pair(ka, kb);
}

int DPF::Eval(const std::vector<uint8_t>& key, int b, uint64_t x, int n) {
    assert(n <= 63);
    assert(x < (1ULL << n));
    block s;
    memcpy(&s, key.data(), 16);
    uint8_t t = key[16];

    for(int i = 0; i < n; i++) {
        block sL = PRG::get(s, mAesL);
        uint8_t tL = getT(sL);
        sL = clr(sL);
        block sR = PRG::get(s, mAesR);
        uint8_t tR = getT(sR);
        sR = clr(sR);
        if(t) {
            block sCW;
            memcpy(&sCW, key.data() + 17 + i*18, 16);
            uint8_t tLCW = key[17+i*18+16];
            uint8_t tRCW = key[17+i*18+17];
            tL^=tLCW;
            tR^=tRCW;
            sL^=sCW;
            sR^=sCW;
        }
        if(x & (1ULL<<(n - 1 - i))) {
            s = sR;
            t = tR;
        } else {
            s = sL;
            t = tL;
        }
    }

    block tmp = ConvertBlock(s);
    if (t) {
        block CW;
        memcpy(&CW, key.data()+key.size()-16, 16);
        tmp += CW;
    }

    int ans = _mm_extract_epi32(tmp, 0);
    int sign = (b==0)?1:-1;

    return sign*ans;
}