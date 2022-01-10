package org.conggroup.vizard.resultreleasecontrol.controller;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.query.QueryResult;
import org.conggroup.vizard.resultreleasecontrol.service.result.QueryResultService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QueryResultController {

    private final QueryResultService queryResultService;

    @RequestMapping("/getQueryResult")
    public QueryResult getQueryResult(@RequestParam("queryId") String queryIdStr) {
        Long queryId = Long.parseLong(queryIdStr);
        return queryResultService.getResult(queryId);
    }
}
