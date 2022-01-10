package org.conggroup.vizard.resultreleasecontrol.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.resultreleasecontrol.service.payment.PaymentCheckService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentCheckController {

    private final PaymentCheckService paymentCheckService;

    @RequestMapping("/paymentCheck")
    public ObjectNode paymentCheck(@RequestParam("tx_hash") String hash) {
        return paymentCheckService.check(hash);
    }
}
