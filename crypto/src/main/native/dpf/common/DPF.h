#pragma once

#include <cstdlib>
#include <vector>

#include "Defines.h"
#include "AES.h"

class DPF {
public:
    DPF();
    DPF(const std::vector<uint8_t> &keyL, const std::vector<uint8_t> &keyR);

    std::pair<std::vector<uint8_t>, std::vector<uint8_t>> Gen(uint64_t alpha, int n);
    int Eval(const std::vector<uint8_t>& key, int b, uint64_t x, int n);

private:
    AES mAesL;
    AES mAesR;

    block clr(block in) { return in & ~MSBBlock; }
    bool getT(block in) { return !is_zero(in & MSBBlock); }
    block ConvertBlock(block in) { return mAesL.encryptECB(in); }

};
