#include "common/PRF.h"

#include <iostream>

void prfTest(PRF &prf1, PRF &prf2) {
    auto ans1 = prf1.Gen(1, 8);
    auto ans2 = prf2.Gen(1, 8);
    auto ans3 = prf1.Gen(0, 8);

    std::cout << "End" << std::endl;
}

int main(int argc, char** argv) {
    PRF prf1 = PRF();
    PRF prf2 = PRF();
    prfTest(prf1,prf2);
    return 0;
}
