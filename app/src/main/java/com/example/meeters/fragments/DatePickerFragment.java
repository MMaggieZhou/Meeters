package com.example.meeters.fragments;
import android.app.Activity;
import android.content.DialogInterface;
import android.widget.DatePicker.OnDateChangedListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import java.util.GregorianCalendar;

import com.example.meeters.activities.DataPass;
import com.example.meeters.meeters.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ying on 11/11/14.
 */
public class DatePickerFragment extends DialogFragment{

    DataPass dataPasser;
    private final static String EXTRA_DATE="com.example.meeters.fragments.date";
    private final static String EXTRA_DATE_TAG="com.example.meeters.fragments.dateTag";
    private String tag;
    private Date mDate;

    @Override
    public void onAttach(Activity a)
    {
        super.onAttach(a);
        dataPasser=(DataPass)a;
    }

    public void passData(String data,String tags)
    {
        dataPasser.onDataPass(data,tag);

    }
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState)
    {
        tag=(String)getArguments().getString(EXTRA_DATE_TAG);
        mDate=(Date)getArguments().getSerializable(EXTRA_DATE);
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(mDate);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        View v=getActivity().getLayoutInflater().inflate(R.layout.dialog_date,null);

        final DatePicker datepicker=(DatePicker)v.findViewById(R.id.dialog_datePicker);

        datepicker.init(year, month, day, new OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                mDate = new GregorianCalendar(year, month, day).getTime();
                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle("Choose Date ")
                .setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year=datepicker.getYear();
                        int month=datepicker.getMonth();
                        int day=datepicker.getDayOfMonth();

                        String date=year+"/"+month+"/"+day;
                        passData(date,tag);
                    }
                }).create();
    }

    public static DatePickerFragment newInstance(Date date,String tag)
    {
        Bundle args=new Bundle();
        args.putSerializable(EXTRA_DATE,date);
        args.putString(EXTRA_DATE_TAG,tag);
        DatePickerFragment fragment=new DatePickerFragment();
        fragment.setArguments(args);
        return  fragment;
    }
}
