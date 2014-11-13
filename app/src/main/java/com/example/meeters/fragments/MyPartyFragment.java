package com.example.meeters.fragments;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.example.meeters.model.domain.User;
import com.example.meeters.model.party.*;
import android.annotation.SuppressLint;
import com.android.volley.Request.Method;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.meeters.base.*;
import com.example.meeters.meeters.R;
import com.example.meeters.constant.URL;
import com.example.meeters.model.party.GetMyPartyResponse;
import com.example.meeters.model.party.StartPartyResponse;
import com.android.volley.ParseError;
import com.example.meeters.utils.JSONUtils;
import java.util.ArrayList;
@SuppressLint("ValidFragment")
public class MyPartyFragment extends BaseFragment
{
    @SuppressWarnings("unused")
    private static final String TAG = MyPartyFragment.class.getSimpleName();
    protected ArrayList<StartPartyResponse> mPartyList;
    protected PartyListAdapter mAdapter;
    private ListView mListView;
    private BaseActivity mBaseActivity;
    private Fragment mFragment;

    public MyPartyFragment(BaseApplication application, BaseActivity activity, Context context)
    {
        super(application, activity, context);
        this.mApplication = application;
        this.mBaseActivity = activity;
        this.mFragment = this;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i(TAG,"onCreateView");
        try
        {
            mView = inflater.inflate(R.layout.fragment_my_party, container, false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        mListView = (ListView) mView.findViewById(R.id.my_party_list);

        initData();
        return mView;

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
    }
    protected void initData()
    {
        // testParties();
        getMyParties();
    }
    public ArrayList<StartPartyResponse> getmPartyList()
    {
        return mPartyList;
    }

    public void setmPartyList(ArrayList<StartPartyResponse> mPartyList)
    {
        this.mPartyList = mPartyList;
    }

    public PartyListAdapter getmAdapter()
    {
        return mAdapter;
    }
    public void getMyParties()
    {

        Log.i(TAG, "Start get my parties process");

        Response.Listener<GetMyPartyResponse> getMyPartiesListener = new Response.Listener<GetMyPartyResponse>()
        {

            @Override
            public void onResponse(GetMyPartyResponse getMyPartyResponse)
            {
                mPartyList = getMyPartyResponse.getMyParties();
                mAdapter = new PartyListAdapter(mPartyList, mView.getContext());
                mListView.setAdapter(mAdapter);
                Log.i(TAG, "After get my party http request, create list item view !");

                mListView.setOnItemClickListener(new OnItemClickListener()
                {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        //android.support.v4.app.FragmentManager manager = mActivity.getSupportFragmentManager();
                        //PartyDetailsFragment mydialog = new PartyDetailsFragment();
                        //mydialog.setData(mPartyList.get(position), LEAVE, mActivity, mApplication);
                        //mydialog.setTargetFragment(mFragment, PARTY_DETAIL_REQUEST);

                        //mydialog.show(manager, "Activity Details");

                    }
                });
                // Save it to local cache

            }
        };

        HashMap<String, String> param = new HashMap<String, String>();
        try
        {
            Location location = mBaseActivity.getLocation();

            if (location == null)
            {
                Toast.makeText(mActivity.getApplicationContext(),
                        "Turn on your GPS, right now I set your location as seattle for testing! ", Toast.LENGTH_LONG)
                        .show();
                param.put("lat", "47.6076");
                param.put("long", "-122.333");
            }
            else
            {
                param.put("lat", String.valueOf(location.getLatitude()));
                param.put("long", String.valueOf(location.getLongitude()));
            }

        }
        catch (Exception e)
        {
            Log.e(TAG, "Can not load location to get my parties");
            param.put("lat", "-122.357082");
             param.put("long", "47.627577");
        }

        String str = (mApplication == null? "not null" : "null");
        User user = mApplication.getUser();
        String s = null;
        if (user.getUserId() == null) {
            user.setUserId(new BigInteger("4"));
        }
        param.put("userId", mApplication.getUser().getUserId().toString());

        BaseRestRequest<GetMyPartyResponse> loginRest = new BaseRestRequest<GetMyPartyResponse>(Method.GET,
                URL.GET_PARTY, param, null, getMyPartiesListener, serviceErrorListener(mActivity))
        {
            @Override
            protected Response<GetMyPartyResponse> parseNetworkResponse(NetworkResponse response)
            {
                Log.i(TAG, "parseNetworkResponse ---->" + response.toString());

                final int statusCode = response.statusCode;

                if (statusCode == 200)
                {
                    String json = "";
                    try
                    {
                        json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        Log.i(TAG, "Status cod 200 and the message ---->" + json);
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        Log.d(TAG, "UnsupportedEncodingException ---->" + e.getMessage());
                        return Response.error(new ParseError(e));
                    }
                    return Response.success((GetMyPartyResponse) JSONUtils.toObject(json, GetMyPartyResponse.class),
                            HttpHeaderParser.parseCacheHeaders(response));

                }
                else
                {
                    Log.e(TAG, "Failed to get my party request and http code is ---->" + statusCode);
                    // Create a new thread for Toast message
                    mActivity.runOnUiThread(new Runnable()
                    {
                        public void run()
                        {

                            Toast.makeText(mActivity.getApplicationContext(),
                                    "Unknow issue happend, please try it later!", Toast.LENGTH_LONG).show();

                        }
                    });

                }

                return Response.error(new ParseError());
            }
        };

        Log.i(TAG, "Add find my joined parties http request in the async queue!");

        mActivity.executeRequest(loginRest);

    }
    /*
    private ArrayList<StartPartyResponse> testParties()
    {
        ArrayList<StartPartyResponse> parties = new ArrayList<StartPartyResponse>();

        for (int i = 0; i < 19; i++)
        {
            StartPartyResponse party = new StartPartyResponse();
            Double d = i / 2.0 + 0.05;

            party.setDistance(new BigDecimal(d));
            party.setVenues("Party " + i);

            DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
            MutableDateTime startTime = MutableDateTime.parse(String.format("10/%s/2014 %s:%s:00", i + 1, i, i * 3),
                    dateTimeFormat);
            MutableDateTime endTime = MutableDateTime.parse(String.format("10/%s/2014 %s:%s:00", i + 1, i + 2, i * 3),
                    dateTimeFormat);

            DateTimeZone dateTimeZone = DateTimeZone.getDefault();
            startTime.setZone(dateTimeZone);
            endTime.setZone(dateTimeZone);

            party.setStartTime(startTime.toString(dateTimeFormat));
            party.setEndTime(endTime.toString(dateTimeFormat));
            parties.add(party);
        }

        return parties;
    }
    */
    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PARTY_DETAIL_REQUEST)
        {
            if (resultCode == LEAVE_PARTY_RESPONSE)
            {
                BigInteger partyId = new BigInteger((String) data.getExtras().get("partyId"));
                Log.i(TAG, partyId.toString());
                if (partyId != null)
                {
                    Log.i(TAG, "refresh");
                    delete(partyId);
                    return;
                }
            }
        }
        if (requestCode == START_PARTY_REQUEST) {
            if (resultCode == START_PARTY_RESPONSE) {
                StartPartyResponse party = data.getParcelableExtra("party");
                if (party != null) {
                    add(party);
                    return;
                }
            }
        }
        if (requestCode == JOIN_PARTY_REQUEST) {
            if (resultCode == JOIN_PARTY_RESPONSE) {
                StartPartyResponse party = data.getParcelableExtra("party");
                if (party != null) {
                    add(party);
                    return;
                }
            }
        }

    }
    */
    /*
    private void delete(BigInteger partyId)
    {
        Iterator<StartPartyResponse> iterator = mPartyList.iterator();
        while (iterator.hasNext())
        {
            if (iterator.next().getPartyId().equals(partyId))
            {
                iterator.remove();
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void add(StartPartyResponse party) {
        mPartyList.add(party);
        mAdapter.notifyDataSetChanged();
    }
    */
}
