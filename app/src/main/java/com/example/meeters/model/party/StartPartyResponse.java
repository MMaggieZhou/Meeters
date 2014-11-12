/**
 * (C) 2014 TheMobies, LLC. All Rights Reserved. Confidential Information of TheMobies, LLC.
 */
package com.example.meeters.model.party;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import com.example.meeters.model.common.Address;

/**
 * Aaron
 */
public class StartPartyResponse extends StartPartyRequest implements Serializable
{
    private BigInteger partyId;
    private BigDecimal distance;
    private Double longitude;
    private Double latitude;
    private String topic;
    private Address address;
    private Date date;
    private String description;

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
    
    public String getTopic()
    {
        return topic;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date=date;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){

    }

}
