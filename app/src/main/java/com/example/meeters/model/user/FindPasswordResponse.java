package com.example.meeters.model.user;

import java.io.Serializable;

/**
 * Created by Ying on 11/16/14.
 */
public class FindPasswordResponse extends FindPasswordRequest implements Serializable{

    private String nickName;
    private String password;

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
