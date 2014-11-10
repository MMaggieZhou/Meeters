package com.example.meeters.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.meeters.meeters.R;

public class SearchNearbySpinnerAdapter extends BaseAdapter
{
    private Context mContext;
    private int optionPostion;
    private String[] values;

    public SearchNearbySpinnerAdapter(Context context, int optionPosition)
    {
        this.mContext = context;

        Resources res = mContext.getResources();
        switch (optionPosition)
        {
            case 0:
                this.values = res.getStringArray(R.array.search_nearby_spinner_distance);
                break;
            case 1:
                this.values = res.getStringArray(R.array.search_nearby_spinner_numOfPeople);
                break;
            case 2:
                this.values = res.getStringArray(R.array.search_nearby_spinner_price);
                break;
            case 3:
                this.values = res.getStringArray(R.array.search_nearby_spinner_time);
                break;
        }
    }

    @Override
    public int getCount()
    {
        return values.length;
    }

    @Override
    public Object getItem(int position)
    {
        return values.length;
    }

    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.fragment_search_nearby_spinner_item, null);
            holder = new ViewHolder();
            
            holder.spinnerValue = (TextView) convertView.findViewById(R.id.search_nearby_spinner_item);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        
        String value = values[position];
        holder.spinnerValue.setText(value);
        
        return convertView;
    }
    
    private class ViewHolder{
        TextView spinnerValue;
    }

}
