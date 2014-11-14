package com.example.meeters.model.party;

import com.example.meeters.model.common.Address;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Mengqi on 11/12/2014.
 */
public class SearchPartyResponse implements Serializable {
    private boolean join;
    private String theme;
    private Double longitude;
    private Double latitude;
    private BigInteger partyId;
    private BigDecimal distance;
    private String startTime;
    private String endTime;
    private String location;
    private String description;

    public boolean getJoin() {
        return join;
    }

    public void setJoin(boolean join) {
        this.join = join;
    }
    public BigInteger getPartyId()
    {
        return partyId;
    }

    public void setPartyId(BigInteger partyId)
    {
        this.partyId = partyId;
    }

    public BigDecimal getDistance()
    {
        return distance;
    }

    public void setDistance(BigDecimal distance)
    {
        this.distance = distance;
    }

    public String getTheme()
    {
        return theme;
    }

    public void setTheme(String theme)
    {
        this.theme = theme;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
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

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

}
