package org.conggroup.vizard.crypto.components.query;

import lombok.Data;

@Data
public class Query {

    public Long queryId;

    public String description;
    public Long beginSeqIndex;
    public Long endSeqIndex;

    public Query() {
        this.queryId = -1L;

        this.description = "defaultDescription";
        this.beginSeqIndex = 0L;
        this.endSeqIndex = 0L;
    }

    public Query(Long queryId,
                 String description,
                 Long beginSeqIndex,
                 Long endSeqIndex) {
        this.queryId = queryId;

        this.description = description;
        this.beginSeqIndex = beginSeqIndex;
        this.endSeqIndex = endSeqIndex;
    }
}
