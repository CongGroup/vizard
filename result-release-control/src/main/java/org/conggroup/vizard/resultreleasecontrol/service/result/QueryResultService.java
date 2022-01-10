package org.conggroup.vizard.resultreleasecontrol.service.result;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.crypto.components.query.QueryResult;
import org.conggroup.vizard.resultreleasecontrol.service.rrc.RRC;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class QueryResultService {

    private final RRC rrc;

    public QueryResult getResult(Long queryId) {
        BigInteger res = rrc.reconstruct(queryId);
        return new QueryResult(queryId, res);
    }
}
