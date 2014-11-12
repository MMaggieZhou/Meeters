package com.example.meeters.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meeters.base.BaseActivity;

import java.util.Date;

import com.example.meeters.fragments.DatePickerFragment;
import com.example.meeters.meeters.R;
import com.example.meeters.model.domain.Event;

/**
 * Created by Ying on 11/11/14.
 */
public class AddEventsActivity extends BaseActivity implements DataPass{

    private final static String DIALOG_DATE="date";
    private final static String START_DATE="START_DATE";
    private final static String END_DATE="END_DATE";
    Event event=new Event();

    private TextView eventName;
    private EditText eventTheme;
    private Button startDateButton;
    private Button endDateButton;
    private EditText eventLocation;
    private EditText availableSlots;
    private EditText eventDescription;
    private Button postButton;


    @Override
    public void onDataPass(String data,String tag)
    {
        if(tag==START_DATE)
            startDateButton.setText(data);
        if(tag==END_DATE)
            endDateButton.setText(data);
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        eventName=(TextView)findViewById(R.id.eventNameTV);
        eventTheme=(EditText)findViewById(R.id.addEventThemeText);

        Date date=new Date();

        startDateButton=(Button)findViewById(R.id.addEventStartDate);
        startDateButton.setText(date.toString());
        event.setStartDate(date);
        endDateButton=(Button)findViewById(R.id.addEventEndDate);
        endDateButton.setText(date.toString());
        event.setEndDate(date);

        eventLocation=(EditText)findViewById(R.id.addEventLocation);
        availableSlots=(EditText)findViewById(R.id.addAvailableSlots);
        eventDescription=(EditText)findViewById(R.id.addEventDescription);
        postButton=(Button)findViewById(R.id.addEventPost);
    }

    @Override
    protected void initEvents() {
        startDateButton.setOnClickListener(this);
        endDateButton.setOnClickListener(this);
        postButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addEventStartDate:
                FragmentManager fm=getFragmentManager();
                DatePickerFragment dialog=new DatePickerFragment().newInstance(event.getStartDate(),
                        START_DATE);
                dialog.show(fm,DIALOG_DATE);
                break;
            case R.id.addEventEndDate:
                FragmentManager fmE=getFragmentManager();
                DatePickerFragment dialogE=new DatePickerFragment().newInstance(event.getStartDate(),
                        END_DATE);
                dialogE.show(fmE,DIALOG_DATE);
                break;
            case R.id.addEventPost:
                break;
            default:return;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
