package com.example.meeters.fragments;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.meeters.meeters.R;

public class SearchNearbyAdapter extends BaseAdapter
{
    private Context mContext;
    private String[] options;
    private Map<String, Integer> optionValues;

    public SearchNearbyAdapter(Context context, String[] options)
    {
        this.options = options;
        this.mContext = context;
        
        if(optionValues == null){
            optionValues = new HashMap<String, Integer>();
            for(String index : options){
                optionValues.put(index, 0);
            }
        }
    }
    
    public void setValues(Map<String, Integer> optionValues){
        this.optionValues = optionValues;
    }
    
    public Map<String, Integer> getValues(){
        return optionValues;
    }
    

    @Override
    public int getCount()
    {        
        return options.length;
    }

    @Override
    public Object getItem(int position)
    {
        return options[position];
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
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.fragment_search_nearby_listitem, parent, false);
            holder = new ViewHolder();
            holder.option_description = (TextView) 
                    convertView.findViewById(R.id.search_nearby_list_description);
            holder.option_spinner = (Spinner) 
                    convertView.findViewById(R.id.search_nearby_list_spinner);
            
            SearchNearbySpinnerAdapter adapter = new SearchNearbySpinnerAdapter(mContext, position);
            holder.option_spinner.setAdapter(adapter);
            holder.option_spinner.setOnItemSelectedListener(new ItemClickSelectListener(
                    holder.option_spinner));
            convertView.setTag(holder);
         }else{
             holder = (ViewHolder) convertView.getTag();
         } 
        
        String option = options[position];
        holder.option_description.setText(option);
        holder.option_description.setTextColor(Color.WHITE);
        holder.option_spinner.setPrompt(option);
        
        int spinnerPostion = optionValues.get(option);
        holder.option_spinner.setSelection(spinnerPostion);
        
        return convertView;
    }
    
    private class ViewHolder
    {
        TextView option_description;
        Spinner option_spinner;
    }
    
    private class ItemClickSelectListener implements OnItemSelectedListener {
        Spinner option_spinner;

        public ItemClickSelectListener(Spinner option_spinner) {
            this.option_spinner = option_spinner;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                int position, long id) {            
            optionValues.put(option_spinner.getPrompt().toString(), position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }

}
