package org.conggroup.vizard.crypto.components.prf;

public class PRFTest {

    public static void main(String[] args) {
        _PRFTest();
    }

    public static void _PRFTest() {
        PRF prf1 = new PRF();
        PRF prf2 = new PRF();

        PRFKey ans1 = prf1.gen(1L);
        PRFKey ans2 = prf2.gen(1L);
        PRFKey ans3 = prf1.gen(0L);

        System.out.println("Ans1: " + ans1.key.toString());
        System.out.println("Ans2: " + ans2.key.toString());
        System.out.println("Ans3: " + ans3.key.toString());

    }
}
