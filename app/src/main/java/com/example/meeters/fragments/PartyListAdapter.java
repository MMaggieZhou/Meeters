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
        Drawable drawable0 = res.getDrawable(R.drawable.icon_food_0);
        Drawable drawable1 = res.getDrawable(R.drawable.icon_food_1);
        Drawable drawable2 = res.getDrawable(R.drawable.icon_food_2);
        Drawable drawable3 = res.getDrawable(R.drawable.icon_food_3);
        Drawable drawable4 = res.getDrawable(R.drawable.icon_food_4);
        Drawable drawable5 = res.getDrawable(R.drawable.icon_food_5);
        Drawable drawable6 = res.getDrawable(R.drawable.icon_food_6);
        Drawable drawable7 = res.getDrawable(R.drawable.icon_food_7);
        Drawable drawable8 = res.getDrawable(R.drawable.icon_food_8);
        Drawable drawable9 = res.getDrawable(R.drawable.icon_food_9);
        Drawable drawable10 = res.getDrawable(R.drawable.icon_food_10);
        Drawable drawable11 = res.getDrawable(R.drawable.icon_food_11);
        Drawable drawable12 = res.getDrawable(R.drawable.icon_food_12);
        Drawable drawable13 = res.getDrawable(R.drawable.icon_food_13);
        Drawable drawable14 = res.getDrawable(R.drawable.icon_food_14);
        Drawable drawable15 = res.getDrawable(R.drawable.icon_food_15);
        drawables.add(drawable0);
        drawables.add(drawable1);
        drawables.add(drawable2);
        drawables.add(drawable3);
        drawables.add(drawable4);
        drawables.add(drawable5);
        drawables.add(drawable6);
        drawables.add(drawable7);
        drawables.add(drawable8);
        drawables.add(drawable9);
        drawables.add(drawable10);
        drawables.add(drawable11);
        drawables.add(drawable12);
        drawables.add(drawable13);
        drawables.add(drawable14);
        drawables.add(drawable15);

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
        TextView theme = (TextView) convertView.findViewById(R.id.list_item_party_theme_data);

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
        return convertView;
    }

    private static class ViewHolder
    {

    }

}
