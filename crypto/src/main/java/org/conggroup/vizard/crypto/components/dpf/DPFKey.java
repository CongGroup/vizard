package org.conggroup.vizard.crypto.components.dpf;

import lombok.Data;

@Data
public class DPFKey {

    public Integer ownerId;
    public Integer keyIndex;
    public byte[] k;
    public String conditionType;
    public Boolean not_flag;

    public DPFKey() {
        this.ownerId = 0;
        this.keyIndex = 0;
        this.k = new byte[0];
        this.conditionType = "single";
        this.not_flag = false;
    }

    public DPFKey(Integer ownerId, Integer keyIndex, byte[] k, String conditionType, Boolean not_flag) {
        this.ownerId = ownerId;
        this.keyIndex = keyIndex;
        this.k = k;
        this.conditionType = conditionType;
        this.not_flag = not_flag;
    }
}
