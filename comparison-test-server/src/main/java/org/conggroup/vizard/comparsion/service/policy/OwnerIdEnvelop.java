package org.conggroup.vizard.comparsion.service.policy;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.ArrayList;

@Data
public class OwnerIdEnvelop {

    public Long queryId;

    @JsonDeserialize(as=ArrayList.class, contentAs = Integer.class)
    public ArrayList<Integer> ownerIdList;

    public OwnerIdEnvelop() {
        queryId = 0L;
        ownerIdList = new ArrayList<>();
    }
}
