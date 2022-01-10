package org.conggroup.vizard.resultreleasecontrol.service.payment;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentCheckService {

    private final RestTemplate restTemplate;

    public ObjectNode check(String hash) {
        String url = String.format(
                "https://api.blockcypher.com/v1/eth/main/txs/%s", hash);
        return restTemplate.getForEntity(url, ObjectNode.class).getBody();
    }
}
