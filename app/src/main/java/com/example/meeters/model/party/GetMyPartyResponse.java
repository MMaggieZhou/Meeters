/**
 * (C) 2014 TheMobies, LLC. All Rights Reserved. Confidential Information of TheMobies, LLC.
 */
package com.example.meeters.model.party;

import java.util.ArrayList;

/**
 * Aaron
 */
public class GetMyPartyResponse
{
    ArrayList<StartPartyResponse> myParties;

    public ArrayList<StartPartyResponse> getMyParties()
    {
        return myParties;
    }

    public void setMyParties(ArrayList<StartPartyResponse> myParties)
    {
        this.myParties = myParties;
    }
}
