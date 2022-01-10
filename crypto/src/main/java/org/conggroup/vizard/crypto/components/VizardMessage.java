package org.conggroup.vizard.crypto.components;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.conggroup.vizard.crypto.utils.deserializer.BigIntegerDeserializer;
import org.conggroup.vizard.crypto.utils.serializer.BigIntegerSerializer;

import java.math.BigInteger;

@Data
public class VizardMessage implements Comparable<VizardMessage> {

    public Integer ownerId;
    public Long seqIndex;

    @JsonSerialize(converter = BigIntegerSerializer.class)
    @JsonDeserialize(converter = BigIntegerDeserializer.class)
    public BigInteger c;

    public VizardMessage() {
        this.seqIndex = 0L;
        this.ownerId = 0;
        this.c = BigInteger.ZERO;
    }

    public VizardMessage(Integer ownerId, Long seqIndex, BigInteger c) {
        this.seqIndex = seqIndex;
        this.ownerId = ownerId;
        this.c = c;
    }

    @Override
    public int compareTo(VizardMessage message) {
        long comp = this.seqIndex - message.seqIndex;
        if(comp<0) {
            return -1;
        }else if(comp==0) {
            return 0;
        }else {
            return 1;
        }
    }
}
