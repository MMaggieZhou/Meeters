/**
 * (C) 2014 TheMobies, LLC. All Rights Reserved. Confidential Information of TheMobies, LLC.
 */
package com.example.meeters.model.party;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

import com.example.meeters.model.common.*;
/**
 * Aaron
 */
public class StartPartyRequest implements Serializable
{
    private BigInteger userId;
    private String theme;
    private String venues;
    private int numOfPeople;
    private BigDecimal price;
    private Address address;
    private String otherInfo;
    private String startTime;
    private String endTime;
    private Double longitude;
    private Double latitude;

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

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
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

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

   

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

}
