package com.example.meeters.model.party;

import java.util.ArrayList;

/**
 * Created by Mengqi on 11/12/2014.
 */
public class SearchResponse {
    ArrayList<SearchPartyResponse> myParties;

    public ArrayList<SearchPartyResponse> getMyParties()
    {
        return myParties;
    }

    public void setMyParties(ArrayList<SearchPartyResponse> myParties)
    {
        this.myParties = myParties;
    }
}
