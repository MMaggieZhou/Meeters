package com.example.meeters.model.user;

/**
 * Created by fox on 2014/11/11.
 */
public class ProfileUpdateResponse extends LoginResponse {
    private boolean flag;

    public boolean getFlag(){
        return flag;
    }

    public void setFlag(boolean flag){
        this.flag=flag;
    }
}
