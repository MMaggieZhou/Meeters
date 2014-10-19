
package com.example.meeters.model.user;


public class LoginRequest
{

    private String loginAccount;
    private String password;
    private String regId;
    private Double longitude;
    private Double latitude;

    public String getLoginAccount()
    {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount)
    {
        this.loginAccount = loginAccount;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRegId()
    {
        return regId;
    }

    public void setRegId(String regId)
    {
        this.regId = regId;
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
}
