/**
 * (C) 2014 TheMobies, LLC. All Rights Reserved. Confidential Information of TheMobies, LLC.
 */
package com.example.meeters.model.domain;

import com.example.meeters.model.common.Address;

import org.joda.time.MutableDateTime;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Aaron
 */
public class Party
{
    private BigInteger partId;
    private BigInteger userId;
    private String theme;
    private String venues;
    private int numOfPeople;
    private Address address;
    private String otherInfo;
    private MutableDateTime startTime;
    private MutableDateTime endTime;
    private Double longitude;
    private Double latigude;
    private BigDecimal distance;


    public BigInteger getPartId()
    {
        return partId;
    }

    public void setPartId(BigInteger partId)
    {
        this.partId = partId;
    }

    public BigInteger getUserId()
    {
        return userId;
    }

    public void setUserId(BigInteger userId)
    {
        this.userId = userId;
    }

    public String getTheme()
    {
        return theme;
    }

    public void setTheme(String theme)
    {
        this.theme = theme;
    }

    public String getVenues()
    {
        return venues;
    }

    public void setVenues(String venues)
    {
        this.venues = venues;
    }

    public int getNumOfPeople()
    {
        return numOfPeople;
    }

    public void setNumOfPeople(int numOfPeople)
    {
        this.numOfPeople = numOfPeople;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public String getOtherInfo()
    {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo)
    {
        this.otherInfo = otherInfo;
    }

    public MutableDateTime getStartTime()
    {
        return startTime;
    }

    public void setStartTime(MutableDateTime startTime)
    {
        this.startTime = startTime;
    }

    public MutableDateTime getEndTime()
    {
        return endTime;
    }

    public void setEndTime(MutableDateTime endTime)
    {
        this.endTime = endTime;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public Double getLatigude()
    {
        return latigude;
    }

    public void setLatigude(Double latigude)
    {
        this.latigude = latigude;
    }

    public BigDecimal getDistance()
    {
        return distance;
    }

    public void setDistance(BigDecimal distance)
    {
        this.distance = distance;
    }



}
