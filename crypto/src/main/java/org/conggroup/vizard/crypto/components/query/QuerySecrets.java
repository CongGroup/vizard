package org.conggroup.vizard.crypto.components.query;

import lombok.Data;

import java.util.ArrayList;

@Data
public class QuerySecrets {

    public Long queryId;

    public ArrayList<String> sharedSecrets;

    public QuerySecrets() {
        this.queryId = -1L;
        this.sharedSecrets = new ArrayList<String>();
    }

    public QuerySecrets(Long queryId, ArrayList<String> sharedSecrets) {
        this.queryId = queryId;
        this.sharedSecrets = sharedSecrets;
    }
}
