package org.conggroup.vizard.dataconsumer.ringer.config;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.pedersen.Pedersen;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;

@Configuration
@RequiredArgsConstructor
public class PedersenProvider {

    @Bean
    public Pedersen pedersen() {
        BigInteger p = new BigInteger("20461323899232065519");
        BigInteger q = new BigInteger("10230661949616032759");
        BigInteger g = new BigInteger("7167227173707595926");
        BigInteger y = new BigInteger("10939128848210874778");
        return new Pedersen(p,q,g,y);
    }
}
