package org.conggroup.vizard.dataowner.controller;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.crypto.components.policy.Policy;
import org.conggroup.vizard.crypto.components.policy.PolicyConditionType;
import org.conggroup.vizard.dataowner.service.DPFKeyProducer;
import org.conggroup.vizard.dataowner.service.RawMessageProducer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class MockDataController {

    private final RawMessageProducer rawMessageProducer;

    private final DPFKeyProducer dpfKeyProducer;

    @RequestMapping("/produceMessage")
    public void produceMessage(@RequestParam("c") String cStr) {
        BigInteger c = new BigInteger(cStr);
        VizardMessage message = new VizardMessage(0, 0L, c);
        rawMessageProducer.produce(message);
    }

    @RequestMapping("/produceBatchMessage")
    public void produceBatchMessage(
            @RequestParam("beginSeqIndex") String beginSeqIndexStr,
            @RequestParam("numOfMessage") String numOfMessageStr,
            @RequestParam("beginOwnerId") String beginOwnerIdStr,
            @RequestParam("endOwnerId") String endOwnerIdStr
    ) {
        Long beginSeqIndex = Long.parseLong(beginSeqIndexStr);
        Long numOfMessage = Long.parseLong(numOfMessageStr);
        Integer beginOwnerId = Integer.parseInt(beginOwnerIdStr);
        Integer endOwnerId = Integer.parseInt(endOwnerIdStr);
        for(int ownerId = beginOwnerId; ownerId<=endOwnerId; ownerId++) {
            for (long i=beginSeqIndex; i<beginSeqIndex+numOfMessage; i++) {
                Long seqIndex = i;
                BigInteger c = BigInteger.ONE;
                VizardMessage message = new VizardMessage(ownerId, seqIndex, c);
                rawMessageProducer.produce(message);
            }
        }
    }

    @RequestMapping("/produceDPFKey")
    public void produceDPFKey() {
        String[] conditions = {"default"};
        Policy policy = new Policy(0, conditions, PolicyConditionType.TYPE_SINGLE, false);
        dpfKeyProducer.produceDPFKey(policy);
    }

    @RequestMapping("/produceBatchDPFKey")
    public void produceBatchDPFKey(
            @RequestParam("beginOwnerId") String beginOwnerIdStr,
            @RequestParam("endOwnerId") String endOwnerIdStr,
            @RequestParam("description") String description,
            @RequestParam("conditionType") String conditionType,
            @RequestParam("not") String not) {
        Integer beginOwnerId = Integer.parseInt(beginOwnerIdStr);
        Integer endOwnerId = Integer.parseInt(endOwnerIdStr);
        String[] conditions = description.split(",");
        Boolean isNOTCondition = "true".equals(not);
        for(int ownerId=beginOwnerId; ownerId<=endOwnerId; ownerId++) {
            Policy policy = new Policy(ownerId, conditions, conditionType, isNOTCondition);
            dpfKeyProducer.produceDPFKey(policy);
        }
    }

    @RequestMapping("/produceBatchDPFConditionKey")
    public void produceBatchDPFConditionKey(
            @RequestParam("ownerId") String ownerIdStr,
            @RequestParam("conditionNum") String conditionNumStr,
            @RequestParam("conditionType") String conditionType,
            @RequestParam("not") String not) {
        Integer ownerId = Integer.parseInt(ownerIdStr);
        Integer conditionNum = Integer.parseInt(conditionNumStr);
        Boolean isNOTCondition = "true".equals(not);
        ArrayList<String> conditions = new ArrayList<>(conditionNum);
        for(int i=0; i<conditionNum; i++) {
            String condition = "defaultPolicy"+i;
            conditions.add(condition);
        }
        String[] condArray = conditions.toArray(new String[0]);
        Policy policy = new Policy(ownerId, condArray, conditionType, isNOTCondition);
        dpfKeyProducer.produceDPFKey(policy);
    }

    @RequestMapping("/revokeDPFKey")
    public void revokeDPFKey(@RequestParam("ownerId") String ownerIdStr) {
        Integer ownerId = Integer.parseInt(ownerIdStr);
        dpfKeyProducer.sendRevokeKeyMessage(ownerId);
    }

}
