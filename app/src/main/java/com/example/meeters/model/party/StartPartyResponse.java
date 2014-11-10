/**
 * (C) 2014 TheMobies, LLC. All Rights Reserved. Confidential Information of TheMobies, LLC.
 */
package com.example.meeters.model.party;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Aaron
 */
public class StartPartyResponse extends StartPartyRequest implements Serializable
{
    private BigInteger partyId;
    private BigDecimal distance;
    private Double longitude;
    private Double latitude;
    private String theme;
    private String venues;

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

}
