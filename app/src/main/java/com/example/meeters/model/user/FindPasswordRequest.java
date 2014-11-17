package com.example.meeters.model.user;

import java.io.Serializable;

/**
 * Created by Ying on 11/16/14.
 */
public class FindPasswordRequest implements Serializable{

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
