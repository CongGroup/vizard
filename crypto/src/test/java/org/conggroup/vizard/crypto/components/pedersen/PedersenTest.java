package org.conggroup.vizard.crypto.components.pedersen;

public class PedersenTest {

    public static void main(String[] args) {
        Pedersen pedersen = Pedersen.getInstance(64);
        System.out.println("p "+pedersen.p);
        System.out.println("q "+pedersen.q);
        System.out.println("g "+pedersen.g);
        System.out.println("y "+pedersen.y);
    }
}
