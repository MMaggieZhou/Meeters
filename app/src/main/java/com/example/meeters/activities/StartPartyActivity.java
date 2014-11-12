/**
 * (C) 2014 TheMobies, LLC. All Rights Reserved. Confidential Information of TheMobies, LLC.
 */
package com.example.meeters.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.meeters.base.*;
import com.example.meeters.meeters.R;
import com.example.meeters.constant.*;
import com.example.meeters.model.common.*;
import com.example.meeters.model.domain.*;
import com.example.meeters.model.party.*;
import com.example.meeters.utils.JSONUtils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

/**
 * Aaron Liang
 */
public class StartPartyActivity extends BaseActivity
{
    private static final int REQUEST_CODE_START_DATE_TIME = 1;
    private static final int REQUEST_CODE_END_DATE_TIME = 2;
    private static final int START_PARTY_RESPONSE = 3;
    private static final String TAG = StartPartyActivity.class.getSimpleName();
    private Button mBtnPickStartTime;
    private Button mBtnPickEndTime;
    private DateTimeFormatter mDateTimeFormatter;
    private Button mBtnReset;
    private Button mBtnStartParty;
    private EditText mEtTheme;
    private EditText mEtVenues;
    private EditText mEtNumOfPeople;
    //private EditText mEtPrice;
    private EditText mEtAddress;
    private EditText mEtOtherInfo;
    private MutableDateTime mStartDateTime;
    private MutableDateTime mEndDateTime;
    private Party mParty;
    private Location mLocation;

    public StartPartyActivity()
    {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);
        initData();
        initViews();
        initEvents();

    }

    protected void initData()
    {
        mBaseApplication = (BaseApplication) getApplication();
        mCurrentUser = mBaseApplication.getUser();
        mDateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
        // mDateTimeZone = new DateTimeZone(DateTimeZone.UTC);
        mParty = new Party();
        mLocation = new Location("AddressLocation");
    }

    protected void initViews()
    {
        mBtnPickEndTime = (Button) findViewById(R.id.addEventEndDate);
        mBtnPickStartTime = (Button) findViewById(R.id.addEventStartDate);
        //mBtnReset = (Button) findViewById(R.id.start_party_reset_btn);
        mEtTheme = (EditText) findViewById(R.id.addEventThemeText);
        //mEtVenues = (EditText) findViewById(R.id.addEventLocation);
        mEtNumOfPeople = (EditText) findViewById(R.id.addAvailableSlots);
        //mEtPrice = (EditText) findViewById(R.id.start_party_et_price);
        mEtAddress = (EditText) findViewById(R.id.addEventLocation);
        mEtOtherInfo = (EditText) findViewById(R.id.addEventDescription);
        mBtnStartParty = (Button) findViewById(R.id.addEventPost);
    }

    protected void initEvents()
    {
        mBtnPickStartTime.setOnClickListener(this);
        //mBtnReset.setOnClickListener(this);
        mBtnPickEndTime.setOnClickListener(this);
        mBtnStartParty.setOnClickListener(this);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mEtAddress.setOnFocusChangeListener(new OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                /*
                 * When focus is lost check that the text field has valid
                 * values.
                 */
                if (!hasFocus)
                {
                    verifyAddress();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.addEventStartDate:
                pickUpDateTime(REQUEST_CODE_START_DATE_TIME);
                break;

            case R.id.addEventEndDate:
                pickUpDateTime(REQUEST_CODE_END_DATE_TIME);
                break;
            /*
            case R.id.start_party_reset_btn:
                reset();
                break;
            */
            case R.id.addEventPost:
                startParty();
                break;
            default:
                return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void pickUpDateTime(int code)
    {
        Intent intent = new Intent(this, DateTimePickerActivity.class);
        startActivityForResult(intent, code);
    }

    private void reset()
    {
        // TODO
    }

    private <T> void startParty()
    {
        final StartPartyRequest startPartyRequest = getPartyValues();

        if (startPartyRequest == null)
        {
            // Exception handling
            return;
        }

        Log.i(TAG, "Start a new party process ---->" + JSONUtils.toJson(startPartyRequest));

        showLoading("Posting...");

        Response.Listener<StartPartyResponse> startPartyListener = new Response.Listener<StartPartyResponse>()
        {

            @Override
            public void onResponse(StartPartyResponse startPartyResponse)
            {
                DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss zzz");
                MutableDateTime startTime = MutableDateTime.parse(startPartyResponse.getStartTime() + " UTC",
                        dateTimeFormat);
                MutableDateTime endTime = MutableDateTime.parse(startPartyResponse.getEndTime() + " UTC",
                        dateTimeFormat);

                DateTimeZone dateTimeZone = DateTimeZone.getDefault();
                startTime.setZone(dateTimeZone);
                endTime.setZone(dateTimeZone);

                mParty.setStartTime(startTime);
                mParty.setEndTime(endTime);
                mParty.setLatigude(startPartyResponse.getLatitude());
                mParty.setLongitude(startPartyResponse.getLongitude());
                mParty.setNumOfPeople(startPartyResponse.getNumOfPeople());
                mParty.setOtherInfo(startPartyResponse.getOtherInfo());
                mParty.setPartId(startPartyResponse.getPartyId());
                mParty.setUserId(startPartyResponse.getUserId());
                //mParty.setPrice(startPartyResponse.getPrice());
                mParty.setTheme(startPartyResponse.getTheme());
                mParty.setVenues(startPartyResponse.getVenues());
                mParty.setAddress(startPartyResponse.getAddress());
                Log.i(TAG, " params before transmission" + JSONUtils.toJson(startPartyResponse));
                Intent returnIntent = getIntent();
                returnIntent.putExtra("newParty", startPartyResponse);
                setResult(START_PARTY_RESPONSE, returnIntent);
                dismissLoading();

                // Add userinfo to db as cache
                // DBHander dbHander = mBaseApplication.getDbHander();
                // dbHander.addUser(mBaseApplication.getUser());

                finish();
            }
        };

        BaseRestRequest<StartPartyResponse> startPartyRest = new BaseRestRequest<StartPartyResponse>(Method.POST,
                URL.START_PARTY, null, JSONUtils.toBytes(startPartyRequest), startPartyListener, serviceErrorListener())
        {
            @Override
            protected Response<StartPartyResponse> parseNetworkResponse(NetworkResponse response)
            {
                Log.i(TAG, "Get the start party http request response and start to parse the response");
                final int statusCode = response.statusCode;

                if (statusCode == 201)
                {
                    String json = "";
                    try
                    {
                        json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        Log.i(TAG, "Status cod 201 and the message ---->" + json);
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        Log.d(TAG, "UnsupportedEncodingException ---->" + e.getMessage());
                        return Response.error(new ParseError(e));
                    }

                    Log.i(TAG, "start party  Http response body----> " + json);
                    return Response.success((StartPartyResponse) JSONUtils.toObject(json, StartPartyResponse.class),
                            HttpHeaderParser.parseCacheHeaders(response));
                }
                else
                {
                    Log.e(TAG, "Failed to start party  and http code is ---->" + statusCode);
                    // Create a new thread for Toast message
                    StartPartyActivity.this.runOnUiThread(new Runnable()
                    {
                        public void run()
                        {

                            Toast.makeText(getApplicationContext(), "Unknow issue happend, please try it later!",
                                    Toast.LENGTH_LONG).show();

                        }
                    });
                    Log.i(TAG, "start party  Failed due to some error!");

                }

                return Response.error(new ParseError());
            }
        };
        Log.i(TAG, "Add start party  http request in the async queue!");

        executeRequest(startPartyRest);

    }

    private StartPartyRequest getPartyValues()
    {
        StringBuilder errorMessage = new StringBuilder();

        StartPartyRequest party = null;
        try
        {
            party = new StartPartyRequest();

            Address address = new Address();
            address.setAddressLine1(mEtAddress.getText().toString().trim());
            party.setAddress(address);

            if (StringUtils.isBlank(party.getAddress().toString()))
            {
                mEtAddress.requestFocus();
                errorMessage.append("empty address");
                Log.i(TAG, "empty address");
                // Toast.makeText(getApplicationContext(),
                // ERRINFO.EMPTY_ADDRESS, Toast.LENGTH_SHORT).show();
                // return null;
            }

            //party.setPrice(new BigDecimal(mEtPrice.getText().toString().trim()));

            party.setNumOfPeople(Integer.parseInt(mEtNumOfPeople.getText().toString().trim()));

            party.setVenues((String) mEtVenues.getText().toString().trim());

            MutableDateTime endTime = new MutableDateTime(mEndDateTime);
            endTime.setZone(DateTimeZone.UTC);
            party.setEndTime(endTime.toString(mDateTimeFormatter));
            /*
            if (StringUtils.isBlank(party.getEndTime().toString()))
            {
                mBtnPickEndTime.requestFocus();
                errorMessage.append(ERRINFO.EMPTY_ENDTIME);
                Log.i(TAG, ERRINFO.EMPTY_ENDTIME);

                // Toast.makeText(getApplicationContext(),
                // ERRINFO.EMPTY_ENDTIME, Toast.LENGTH_SHORT).show();
                // return null;
            }
            */
            MutableDateTime startTime = new MutableDateTime(mStartDateTime);
            startTime.setZone(DateTimeZone.UTC);
            party.setStartTime(startTime.toString(mDateTimeFormatter));
            /*
            if (StringUtils.isBlank(party.getStartTime().toString()))
            {
                mBtnPickStartTime.requestFocus();
                errorMessage.append(ERRINFO.EMPTY_STARTTIME);
                Log.i(TAG, ERRINFO.EMPTY_STARTTIME);

                // Toast.makeText(getApplicationContext(),
                // ERRINFO.EMPTY_STARTTIME, Toast.LENGTH_SHORT).show();
                // return null;
            }
            */
            /*
            party.setTheme(mEtTheme.getText().toString());
            if (StringUtils.isBlank(party.getTheme()))
            {
                mEtTheme.requestFocus();
                errorMessage.append(ERRINFO.EMPTY_THEME);
                Log.i(TAG, ERRINFO.EMPTY_THEME);

                // Toast.makeText(getApplicationContext(), ERRINFO.EMPTY_THEME,
                // Toast.LENGTH_SHORT).show();
                // return null;
            }
            */
            party.setOtherInfo(mEtOtherInfo.getText().toString().trim());

            Log.i(TAG, "address ---->" + JSONUtils.toJson(address));

            party.setLatitude(mLocation.getLatitude());
            party.setLongitude(mLocation.getLongitude());

            party.setUserId(mCurrentUser.getUserId());
            Log.i(TAG, "getPartyValues ---->" + JSONUtils.toJson(party));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e(TAG, "");
            errorMessage.append("Errors found!");
        }
        finally
        {

            if (errorMessage.length() > 0)
            {
                Toast.makeText(getApplicationContext(), errorMessage.toString(), Toast.LENGTH_SHORT).show();
                return null;
            }

        }
        return party;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.i(TAG, "Return from popup window with code: " + requestCode + "--" + resultCode + "--" + data.toString());
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == REQUEST_CODE_START_DATE_TIME)
            {
                MutableDateTime dateTime = (MutableDateTime) data.getSerializableExtra("DateTime");
                if (dateTime != null)
                {
                    mStartDateTime = dateTime;
                    mBtnPickStartTime.setText(mStartDateTime.toString(mDateTimeFormatter));
                    Log.i(TAG, "mStartDateTime: " + mStartDateTime.toString());
                }
            }
            else if (requestCode == REQUEST_CODE_END_DATE_TIME)
            {
                MutableDateTime dateTime = (MutableDateTime) data.getSerializableExtra("DateTime");
                if (dateTime != null)
                {
                    mEndDateTime = dateTime;
                    mBtnPickEndTime.setText(mEndDateTime.toString(mDateTimeFormatter));
                    Log.i(TAG, "mEndDateTime: " + mStartDateTime.toString());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void verifyAddress()
    {
        Log.i(TAG, "Start to verify the input address");
        getLocationFromAddress(mEtAddress.getText().toString().trim());
    }

    protected void getLocationFromAddress(String address)
    {
        if (StringUtils.isBlank(address))
        {
            return;
        }
        showLoading("Verifing address...");

        String addressString = address.replaceAll(" ", "+");
        String uri = "https://maps.googleapis.com/maps/api/geocode/json?address=" + addressString
                + "&key=AIzaSyAj6YvDDQNflN8YFKIUnr3Hw0FKjKYujNk";

        Log.i(TAG, uri);
        Response.Listener<JSONObject> loationListener = new Response.Listener<JSONObject>()
        {

            @Override
            public void onResponse(JSONObject response)
            {

                Log.i(TAG, "get location rest response ----> " + response.toString());

                try
                {
                    Double longitute = ((JSONArray) response.get("results")).getJSONObject(0).getJSONObject("geometry")
                            .getJSONObject("location").getDouble("lng");

                    Double latitude = ((JSONArray) response.get("results")).getJSONObject(0).getJSONObject("geometry")
                            .getJSONObject("location").getDouble("lat");
                    mLocation.setLatitude(latitude);
                    mLocation.setLongitude(longitute);
                    Log.i(TAG, "Geocoding ---->" + JSONUtils.toJson(mLocation));
                }
                catch (JSONException e)
                {
                    Log.e(TAG, e.getMessage());
                }
                dismissLoading();

            }
        };

        JsonObjectRequest locationRest = new JsonObjectRequest(Method.POST, uri, null, loationListener,
                serviceErrorListener());

        locationRest.setRetryPolicy(new DefaultRetryPolicy(5000,// DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                // // 2500
                1, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 1
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); // 1f);
        executeRequest(locationRest);

    }
}
