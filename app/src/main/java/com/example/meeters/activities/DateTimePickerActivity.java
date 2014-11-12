package com.example.meeters.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.meeters.base.*;
import com.example.meeters.meeters.R;
import com.example.meeters.view.*;
import com.example.meeters.view.DatePicker.DateWatcher;
import com.example.meeters.view.TimePicker.*;
import org.joda.time.MutableDateTime;

import java.util.Calendar;

public class DateTimePickerActivity extends BaseActivity implements DateWatcher, TimeWatcher
{
    private static final String TAG = DateTimePickerActivity.class.getSimpleName();

    private Button mBtnPicker;
    private MutableDateTime mDateTime;

    public DateTimePickerActivity()
    {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_picker);
        initData();
        initViews();
        initEvents();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.date_time_picker_pick_btn:
                pickDateTime();
                break;
            default:
                return;
        }
    }
    
    private void pickDateTime(){
        
        Intent intent = new Intent();
        intent.putExtra("DateTime", mDateTime);
        if (getParent() == null) {
            setResult(Activity.RESULT_OK, intent);
        } else {
            getParent().setResult(Activity.RESULT_OK, intent);
        }
        
        finish();
    }
    
    
    
    public MutableDateTime getmDateTime()
    {
        return mDateTime;
    }

    public void setmDateTime(MutableDateTime mDateTime)
    {
        this.mDateTime = mDateTime;
    }

    private void setDate(Calendar cal)
    {
        mDateTime.setMonthOfYear(cal.get(Calendar.MONTH) + 1);
        mDateTime.setDayOfMonth(cal.get(Calendar.DAY_OF_MONTH));
        mDateTime.setYear(cal.get(Calendar.YEAR));
        Log.i(TAG, "setDate: " + mDateTime.toString());

    }

    private void setTime(Calendar cal)
    {
        mDateTime.setHourOfDay(cal.get(Calendar.HOUR) + cal.get(Calendar.AM_PM) * 12);
        mDateTime.setMinuteOfHour(cal.get(Calendar.MINUTE));
        Log.i(TAG, "setTime: " + mDateTime.toString());

    }

    @Override
    public void onDateChanged(Calendar cal)
    {
        Log.i(TAG, "" + cal.get(Calendar.MONTH) + " " + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.YEAR));
        setDate(cal);
    }

    @Override
    public void onTimeChanged(Calendar cal)
    {
        Log.i(TAG, "" + cal.get(Calendar.HOUR) + " " + cal.get(Calendar.MINUTE) + " " + cal.get(Calendar.AM_PM));

        setTime(cal);

    }

    @Override
    public void onTimeChanged(int h, int m, int am_pm)
    {
        Log.i(TAG, "" + h + " " + m + " " + am_pm);
    }

    @Override
    protected void initViews()
    {
        mBtnPicker = (Button) findViewById(R.id.date_time_picker_pick_btn);

    }

    @Override
    protected void initEvents()
    {
        mBtnPicker.setOnClickListener(this);
    }

    protected void initData(){
        Calendar currentCal = Calendar.getInstance();
        Log.i(TAG, "currentCal: " + currentCal.toString());

        DatePicker d = (DatePicker) findViewById(R.id.date_time_picker_date);
        d.setCal(currentCal);
        d.setDateChangedListener(this);

        try
        {
            d.setStartYear(2014);
            d.setEndYear(2018);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }

        TimePicker t = (TimePicker) findViewById(R.id.date_time_picker_time);
        t.setCal(currentCal);
        t.setTimeChangedListener(this);
        t.setCurrentTimeFormate(TimePicker.HOUR_12);
        t.setAMPMVisible(true);

        mDateTime = new MutableDateTime();

        setDate(currentCal);
        setTime(currentCal);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void finish(){
        Log.i(TAG, "Finish date time picker activity!");
        super.finish();
    }
}
