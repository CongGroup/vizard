#include "Defines.h"
#include "AES.h"

class PRG {
public:
    PRG(const PRG&) = delete;
    static block get(const block& seed, const AES& mAES) { return mAES.encryptECB_MMO(seed); }

private:
    PRG() = default;
};