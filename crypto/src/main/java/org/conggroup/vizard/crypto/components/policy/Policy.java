package org.conggroup.vizard.crypto.components.policy;

import lombok.Data;

import java.util.Arrays;
import java.util.HashSet;

@Data
public class Policy {

    public Integer ownerId;

    public String[] conditions;
    public String conditionType;
    public Boolean not;

    public Policy() {
        this.ownerId = 0;
        this.conditions = new String[]{"defaultPolicy"};
        this.conditionType = "single";
        this.not = false;
    }

    public Policy(Integer ownerId, String[] conditions, String conditionType, Boolean not) {
        this.ownerId = ownerId;
        if(conditions.length>50) {
            throw new RuntimeException("Too many conditions");
        }
        //Remove duplicates
        this.conditions = new HashSet<String>(Arrays.asList(conditions)).toArray(new String[0]);
        this.conditionType = conditionType;
        this.not = not;
    }
}
