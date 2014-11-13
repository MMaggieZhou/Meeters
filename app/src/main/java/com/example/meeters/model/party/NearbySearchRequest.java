/**
 * (C) 2014 TheMobies, LLC. All Rights Reserved. Confidential Information of TheMobies, LLC.
 */
package com.example.meeters.model.party;

import java.math.BigInteger;

/**
 * @author Maggie
 * 
 */
public class NearbySearchRequest
{
    private BigInteger userId;
    private Double longitude;
    private Double latitude;
    private int distanceOption;

    public BigInteger getUserId()
    {
        return userId;
    }

    public void setUserId(BigInteger userId)
    {
        this.userId = userId;
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

    public int getDistanceOption()
    {
        return distanceOption;
    }

    public void setDistanceOption(int distanceOption)
    {
        this.distanceOption = distanceOption;
    }

}
