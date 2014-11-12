package com.example.meeters.model.user;

import java.math.BigInteger;

/**
 * Created by fox on 2014/11/11.
 */
public class ProfileUpdateRequest {

    private BigInteger userId;
    private int type;
    private String value;

    public BigInteger getUserId()
    {
        return userId;
    }

    public void setUserId(BigInteger userId)
    {
        this.userId = userId;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}
