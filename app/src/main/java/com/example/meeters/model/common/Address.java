package com.example.meeters.model.common;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class Address implements Serializable
{
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String cityName;
    private String postalCode;
    private String stateProvinceCode;
    private String countryCode;
    private String countyCode;
    private boolean isPrivateAddress;
    private boolean isDefault;

    public String getAddressLine1()
    {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1)
    {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2()
    {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2)
    {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3()
    {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3)
    {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4()
    {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4)
    {
        this.addressLine4 = addressLine4;
    }

    public String getAddressLine5()
    {
        return addressLine5;
    }

    public void setAddressLine5(String addressLine5)
    {
        this.addressLine5 = addressLine5;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public String getStateProvinceCode()
    {
        return stateProvinceCode;
    }

    public void setStateProvinceCode(String stateProvinceCode)
    {
        this.stateProvinceCode = stateProvinceCode;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getCountyCode()
    {
        return countyCode;
    }

    public void setCountyCode(String countyCode)
    {
        this.countyCode = countyCode;
    }

    public boolean isPrivateAddress()
    {
        return isPrivateAddress;
    }

    public void setPrivateAddress(boolean isPrivateAddress)
    {
        this.isPrivateAddress = isPrivateAddress;
    }

    public boolean isDefault()
    {
        return isDefault;
    }

    public void setDefault(boolean isDefault)
    {
        this.isDefault = isDefault;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        String space = " ";
        String comma = ", ";

        if (StringUtils.isNotBlank(addressLine1))
        {
            sb.append(addressLine1);
            sb.append(comma);
        }

        if (StringUtils.isNotBlank(cityName))
        {
            sb.append(cityName);
            sb.append(comma);
        }

        if (StringUtils.isNotBlank(stateProvinceCode))
        {
            sb.append(stateProvinceCode);
        }
        if (StringUtils.isNotBlank(postalCode))
        {
            sb.append(space);
            sb.append(postalCode);
        }
        sb.append(comma);

        if (StringUtils.isNotBlank(countryCode))
        {
            sb.append(countryCode);
            sb.append(space);
        }

        sb.setLength(Math.max(sb.length() - 1, 0));
        return sb.toString();

    }
}
