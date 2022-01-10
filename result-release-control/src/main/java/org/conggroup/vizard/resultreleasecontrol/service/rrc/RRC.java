package org.conggroup.vizard.resultreleasecontrol.service.rrc;

import org.conggroup.vizard.crypto.utils.Converter;
import org.conggroup.vizard.crypto.components.operator.Operator;
import org.conggroup.vizard.crypto.components.secretsharing.ShamirSecretSharing;
import org.conggroup.vizard.crypto.components.query.QuerySecrets;

import java.math.BigInteger;
import java.util.ArrayList;

public class RRC {

    private final Integer n_member;

    private final ArrayList<RRCMember> rrcMembers;

    private final ShamirSecretSharing shamirSecretSharing;

    private final Operator operator;

    public RRC(Integer n_member, ShamirSecretSharing shamirSecretSharing, Operator operator) {
        this.n_member = n_member;
        this.rrcMembers = constructRRCMembers(n_member);
        this.shamirSecretSharing = shamirSecretSharing;
        this.operator = operator;
    }

    public void receiveSecret(QuerySecrets secrets, Integer no) {
        for(int i=0; i<n_member; i++) {
            rrcMembers.get(i).receive(secrets.queryId, secrets.sharedSecrets.get(i), no);
        }
    }

    public BigInteger reconstruct(Long queryId) {
        ArrayList<String> sharedSecret0 = new ArrayList<String>(n_member);
        ArrayList<String> sharedSecret1 = new ArrayList<String>(n_member);
        for(RRCMember rrcMember: rrcMembers) {
            ArrayList<String> secret = rrcMember.send(queryId);
            sharedSecret0.add(secret.get(0));
            sharedSecret1.add(secret.get(1));
        }
        BigInteger secret0 = Converter.bytesToBigInteger(shamirSecretSharing.join(sharedSecret0));
        BigInteger secret1 = Converter.bytesToBigInteger(shamirSecretSharing.join(sharedSecret1));
        return operator.add(secret0, secret1);
    }

    private static ArrayList<RRCMember> constructRRCMembers(Integer _n_member) {
        ArrayList<RRCMember> members = new ArrayList<RRCMember>(_n_member);
        for(int i=0; i<_n_member; i++) {
            members.add(new RRCMember());
        }
        return members;
    }

}
