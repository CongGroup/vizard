#include "AES.h"

class PRF{
public:
    PRF();
    explicit PRF(const std::vector<uint8_t> &seed);

    std::vector<uint8_t> Gen(int64_t input, int bytes);
private:
    AES mAES;
};