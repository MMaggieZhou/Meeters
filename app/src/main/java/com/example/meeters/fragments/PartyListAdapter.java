/** 
 * (C) 2014 TheMobies, LLC. All Rights Reserved. Confidential Information of TheMobies, LLC.
 */

package com.example.meeters.fragments;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meeters.meeters.R;
import com.example.meeters.model.party.*;
public class PartyListAdapter extends BaseAdapter
{
    private ArrayList<StartPartyResponse> mMyParties;
    private Context mContext;
    Random randomGenerator = new Random(16);
    ArrayList<Drawable> drawables = new ArrayList<Drawable>();

    public PartyListAdapter(ArrayList<StartPartyResponse> partyList, Context context)
    {
        this.mMyParties = partyList;
        this.mContext = context;
        Resources res = mContext.getResources();

    }

    @Override
    public int getCount()
    {
        return mMyParties.size();
    }

    @Override
    public Object getItem(int arg0)
    {
        return null;
    }

    @Override
    public long getItemId(int arg0)
    {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_party, parent, false);
        }
        TextView distance = (TextView) convertView.findViewById(R.id.list_item_party_distance_data);
        TextView time = (TextView) convertView.findViewById(R.id.list_item_party_time_data);
        TextView theme = (TextView) convertView.findViewById(R.id.eventTheme);
        TextView description = (TextView) convertView.findViewById(R.id.eventDescription);

        // ImageView img = (ImageView) convertView.findViewById(R.id.list_item_party_image);
        // img.setImageDrawable(drawables.get(mMyParties.get(position).getPartyId().mod(new BigInteger("15")).intValue()));

        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss zzz");
        MutableDateTime startTime = MutableDateTime.parse(mMyParties.get(position).getStartTime() + " UTC",
                dateTimeFormat);
        MutableDateTime endTime = MutableDateTime.parse(mMyParties.get(position).getEndTime() + " UTC", dateTimeFormat);

        DateTimeZone dateTimeZone = DateTimeZone.getDefault();
        startTime.setZone(dateTimeZone);
        endTime.setZone(dateTimeZone);

        DateTimeFormatter dateTimeFormat2 = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
        mMyParties.get(position).setStartTime(startTime.toString(dateTimeFormat2));
        mMyParties.get(position).setEndTime(endTime.toString(dateTimeFormat2));

        DateTimeFormatter simpleDateTimeFormat = DateTimeFormat.forPattern("MM/dd HH:mm");

        String timeString = startTime.toString(dateTimeFormat); // + " to " + endTime.toString(simpleDateTimeFormat);

        BigDecimal dis = mMyParties.get(position).getDistance();
        String disData = "";
        if (dis == null)
        {
            disData = "? mile";
        }
        else if (dis.compareTo(BigDecimal.ONE) <= 0)
        {
            disData = dis.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + " mile";
        }
        else
        {
            disData = dis.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + " miles";
        }
        distance.setText(disData);

        time.setText(timeString);
        theme.setText(mMyParties.get(position).getTheme());
        String str = (mMyParties.get(position).getOtherInfo()== null ? " " : mMyParties.get(position).getOtherInfo());
        description.setText(str);
        return convertView;
    }

    private static class ViewHolder
    {

    }

}
