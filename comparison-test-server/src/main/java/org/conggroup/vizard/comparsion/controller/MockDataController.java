package org.conggroup.vizard.comparsion.controller;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.comparsion.service.message.MessageProcessor;
import org.conggroup.vizard.comparsion.service.policy.PolicyProcessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class MockDataController {

    private final MessageProcessor messageProcessor;

    private final PolicyProcessor policyProcessor;

    @RequestMapping("/genMessage")
    public void genMessage(@RequestParam("beginSeqIndex") String beginSeqIndexStr,
                               @RequestParam("numOfMessage") String numOfMessageStr,
                               @RequestParam("c") String c,
                               @RequestParam("beginOwnerId") String beginOwnerIdStr,
                               @RequestParam("endOwnerId") String endOwnerIdStr) {
        Long beginSeq = Long.parseLong(beginSeqIndexStr);
        Long numOfMessage = Long.parseLong(numOfMessageStr);
        Integer beginOwnerId = Integer.parseInt(beginOwnerIdStr);
        Integer endOwnerId = Integer.parseInt(endOwnerIdStr);
        messageProcessor.genMessage(beginSeq, numOfMessage, c, beginOwnerId, endOwnerId);
    }

    @RequestMapping("/genPolicy")
    public void genPolicy(@RequestParam("beginOwnerId") String beginOwnerIdStr,
                          @RequestParam("endOwnerId") String endOwnerIdStr,
                          @RequestParam("condition") String condition) {
        Integer beginOwnerId = Integer.parseInt(beginOwnerIdStr);
        Integer endOwnerId = Integer.parseInt(endOwnerIdStr);
        policyProcessor.genPolicy(beginOwnerId, endOwnerId, condition);
    }

}
