
package com.example.meeters.constant;


public enum URL
{

    BASE_URL("http://10.0.3.2:9000"),

    LOGIN("/user/login"),
    REGISTER("/user/register"),
    PROFILE_UPDATE("/user/profileUpdate"),
    START_PARTY("/party/create"),
    GET_PARTY("/party/myParty"),
    JOIN_PARTY("/party/join"),
    FIND_PASSWORD("/user/findPassword"),
    SEARCH_PARTY("/party/nearby");

    private String value;

    private URL(String str)
    {
        this.value = str;
    }

    public String getValue()
    {
        return value;
    }

}