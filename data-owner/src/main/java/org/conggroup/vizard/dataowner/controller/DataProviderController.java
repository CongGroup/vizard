package org.conggroup.vizard.dataowner.controller;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.crypto.components.policy.Policy;
import org.conggroup.vizard.dataowner.service.DPFKeyProducer;
import org.conggroup.vizard.dataowner.service.RawMessageProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DataProviderController {

    private final RawMessageProducer rawMessageProducer;

    private final DPFKeyProducer dpfKeyProducer;

    @PostMapping("/produceMessage")
    public void produceMessage(VizardMessage message) {
        rawMessageProducer.produce(message);
    }

    @PostMapping("/produceDPFKey")
    public void produceDPFKey(Policy policy) {
        dpfKeyProducer.produceDPFKey(policy);
    }

    @RequestMapping("/revokeDPFKey")
    public void revokeDPFKey(@RequestParam("ownerId") String ownerIdStr) {
        Integer ownerId = Integer.parseInt(ownerIdStr);
        dpfKeyProducer.sendRevokeKeyMessage(ownerId);
    }

}
