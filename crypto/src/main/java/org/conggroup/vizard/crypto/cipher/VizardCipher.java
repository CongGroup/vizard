package org.conggroup.vizard.crypto.cipher;

import org.conggroup.vizard.crypto.components.VizardMessage;
import org.conggroup.vizard.crypto.components.beaver.Beaver;
import org.conggroup.vizard.crypto.components.beaver.BeaverDiff;
import org.conggroup.vizard.crypto.components.operator.Operator;
import org.conggroup.vizard.crypto.components.prf.PRF;
import org.conggroup.vizard.crypto.components.prf.PRFKey;

import java.math.BigInteger;
import java.util.ArrayList;

public class VizardCipher {

    private final PRF prf;
    private final Operator operator;
    private final Beaver beaver;

    public VizardCipher(PRF prf, Operator operator, Beaver beaver) {
        this.prf = prf;
        this.operator = operator;
        this.beaver = beaver;
    }

    public VizardMessage encrypt(VizardMessage plainMessage, Long prevSeqIndex) {
        PRFKey prfKey1 = prf.gen(plainMessage.seqIndex);
        PRFKey prfKey2 = prf.gen(prevSeqIndex);
        BigInteger cipherText = operator.encrypt(plainMessage.c, prfKey1.key, prfKey2.key);
        return new VizardMessage(plainMessage.ownerId, plainMessage.seqIndex, cipherText);
    }

    public BigInteger add(BigInteger b1, BigInteger b2) {
        return operator.add(b1, b2);
    }

    public VizardMessage add(VizardMessage m1, VizardMessage m2) {
        BigInteger sum = operator.add(m1.c, m2.c);
        return new VizardMessage(m1.ownerId, m1.seqIndex, sum);
    }

    public VizardMessage sumMessages(ArrayList<VizardMessage> messages) {
        Integer ownerId = messages.get(0).ownerId;
        BigInteger c = messages.get(0).c;
        for(int i=1; i<messages.size(); i++) {
            c = c.add(messages.get(i).c);
        }
        c = operator.modM(c);
        return new VizardMessage(ownerId, 0L, c);
    }

    public VizardMessage C_bi(
            BigInteger T_bi,
            ArrayList<VizardMessage> messages) {
        VizardMessage message = this.sumMessages(messages);
        message.c = operator.multiply(T_bi, message.c);
        return message;
    }

    public BigInteger K_b(
            Long beginSeqIndex,
            Long endSeqIndex) {
        PRFKey prfKey1 = prf.gen(beginSeqIndex);
        PRFKey prfKey2 = prf.gen(endSeqIndex);
        return operator.subtract(prfKey1.key, prfKey2.key);
    }

    public BigInteger sumT_b(ArrayList<BigInteger> T_b) {
        return operator.sum(T_b);
    }

    public BeaverDiff generateBeaverDiff(
            Long queryId,
            BigInteger T_b,
            BigInteger K_b) {
        return beaver.generateDiff(queryId, T_b, K_b);
    }

    public BigInteger D_b(
            BeaverDiff local,
            BeaverDiff received) {
        return beaver.reconstruct(local, received, operator);
    }

}
