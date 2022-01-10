package org.conggroup.vizard.dataconsumer.controller;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.query.Query;
import org.conggroup.vizard.dataconsumer.service.QueryRequestProducer;
import org.conggroup.vizard.dataconsumer.utils.SnowFlakeIdUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QueryController {

    private final QueryRequestProducer queryRequestProducer;

    private final SnowFlakeIdUtil snowFlakeIdUtil;

    @RequestMapping("/produceQuery")
    public Query produceQuery(
            @RequestParam("description") String description,
            @RequestParam("beginSeqIndex") String beginSeqIndexStr,
            @RequestParam("endSeqIndex") String endSeqIndexStr) {
        Query query = new Query();
        query.queryId = snowFlakeIdUtil.nextId();
        query.beginSeqIndex = Long.parseLong(beginSeqIndexStr);
        query.endSeqIndex = Long.parseLong(endSeqIndexStr);
        query.description = description;
        queryRequestProducer.produce(query);
        return query;
    }
}
