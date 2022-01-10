#include "common/DPF.h"

#include <iostream>

int dpfEval(DPF &dpf, std::pair<std::vector<uint8_t>, std::vector<uint8_t>> &keys, uint64_t x, int n) {
    auto a = keys.first;
    auto b = keys.second;
    int ans0 = dpf.Eval(a, 0, x, n);
    int ans1 = dpf.Eval(b, 1, x, n);
    std::cout << "Ans0: " << ans0 << std::endl;
    std::cout << "Ans1: " << ans1 << std::endl;
    return ans0 + ans1;
}

int main(int argc, char** argv) {
    int N = 24;
    DPF dpf = DPF();
    auto keys = dpf.Gen(114514, N);
    std::cout << dpfEval(dpf, keys, 114514, N) << std::endl;
    std::cout << dpfEval(dpf, keys, 255, N) << std::endl;
    std::cout << dpfEval(dpf, keys, 1, N) << std::endl;
    std::cout << dpfEval(dpf, keys, 0, N) << std::endl;
    return 0;
}