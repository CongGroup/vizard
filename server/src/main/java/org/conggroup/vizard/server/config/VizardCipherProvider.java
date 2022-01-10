package org.conggroup.vizard.server.config;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.cipher.VizardCipher;
import org.conggroup.vizard.crypto.components.beaver.Beaver;
import org.conggroup.vizard.crypto.components.operator.Operator;
import org.conggroup.vizard.crypto.components.prf.PRF;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class VizardCipherProvider {

    private final Beaver beaver;

    private final Operator operator;

    private final PRF prf;

    @Bean
    public VizardCipher vizardCipher() {
        return new VizardCipher(prf, operator, beaver);
    }

}
