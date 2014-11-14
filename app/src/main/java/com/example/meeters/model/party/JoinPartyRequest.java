package com.example.meeters.model.party;

/**
 * Created by fox on 2014/11/13.
 */
import java.math.BigInteger;
public class JoinPartyRequest{
    private BigInteger PartyId;
    private BigInteger UserId;

    public BigInteger getPartyId() {
        return PartyId;
    }
    public void setPartyId(BigInteger partyId) {
        PartyId = partyId;
    }
    public BigInteger getUserId() {
        return UserId;
    }
    public void setUserId(BigInteger userId) {
        UserId = userId;
    }
}
