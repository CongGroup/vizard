package org.conggroup.vizard.crypto.components.beaver;

import java.math.BigInteger;
import java.util.ArrayList;

public class BeaverTest {

    public static void main(String[] args) {
        TripletGenerator tripletGenerator = new TripletGenerator(BigInteger.ONE.shiftLeft(64));
        ArrayList<BeaverTriplet> beaverTriplets = tripletGenerator.generateTriplets();
        System.out.println(beaverTriplets);
    }
}
