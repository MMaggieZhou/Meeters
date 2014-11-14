package com.example.meeters.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.meeters.base.BaseActivity;
import com.example.meeters.base.BaseApplication;
import com.example.meeters.base.BaseRestRequest;
import com.example.meeters.model.party.JoinPartyRequest;
import com.example.meeters.model.party.JoinPartyResponse;
import com.example.meeters.model.party.SearchPartyResponse;
import com.example.meeters.meeters.R;
import com.example.meeters.constant.URL;
import com.example.meeters.utils.JSONUtils;


import java.io.UnsupportedEncodingException;

/**
 * Created by fox on 2014/11/13.
 */
public class JoinPartyFragment extends DialogFragment implements View.OnClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = JoinPartyFragment.class.getSimpleName();
    private View view;
    private Button mJoinBtn;
    private Button mBackBtn;
    private SearchPartyResponse party;
    private BaseActivity mActivity;
    private BaseApplication mApplication;

    @Override
    public void onAttach(Activity activity)
    {

        super.onAttach(activity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        setCancelable(false);
        getDialog().setTitle("Party Details");

        view = inflater.inflate(R.layout.fragment_join_party, null, false);

        mJoinBtn = (Button) view.findViewById(R.id.joinParty);
        mBackBtn=(Button)view.findViewById(R.id.backParty);

        // setting onclick listener for buttons
        mJoinBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);

        if (view == null)
        {
            Log.e(TAG, "View is null");
            return null;
        }

        TextView theme = (TextView) view.findViewById(R.id.joinPartyThemeText);
        theme.setText(party.getTheme());

        TextView startTime = (TextView) view.findViewById(R.id.joinPartyStarttimeText);
        startTime.setText(party.getStartTime());

        TextView endTime = (TextView) view.findViewById(R.id.joinPartyEndtimeText);
        endTime.setText(party.getEndTime());

        TextView location = (TextView) view.findViewById(R.id.joinPartyLocationText);
        location.setText(party.getLocation());

        TextView description = (TextView) view.findViewById(R.id.joinPartyDescriptionText);
        description.setText(party.getDescription());

        TextView state=(TextView) view.findViewById(R.id.joinPartyStateText);
        if(party.getJoin()){
            state.setText("You have joined the activity.");
        }else{
            state.setText("This activity is interesting! Join it now!");
        }

        return view;
    }

    public void setData(SearchPartyResponse party,  BaseActivity activity,
                        BaseApplication application)
    {
        this.party = party;
        this.mActivity = activity;
        this.mApplication = application;

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.joinParty:
                if(party.getJoin()){
                    dismiss();

                }else{
                    join();
                }
                break;
            case R.id.backParty:
                dismiss();
                break;

        }
    }

    public void join()
    {

        Log.i(TAG, "Start login process");
        final JoinPartyRequest joinPartyRequest = new JoinPartyRequest();

        mActivity.showLoading("Join...");

        joinPartyRequest.setPartyId(party.getPartyId());
        joinPartyRequest.setUserId(mApplication.getUser().getUserId());

        Response.Listener<JoinPartyResponse> joinPartyListener = new Response.Listener<JoinPartyResponse>()
        {

            @Override
            public void onResponse(JoinPartyResponse joinPartyResponse)
            {

                Log.i(TAG,
                        "After join party http request, override user info to application! And Navigate to main pages!");
                mActivity.dismissLoading();
                dismiss();

            }
        };

        BaseRestRequest<JoinPartyResponse> joinRest = new BaseRestRequest<JoinPartyResponse>(Request.Method.POST,
                URL.JOIN_PARTY, null, JSONUtils.toBytes(joinPartyRequest), joinPartyListener,
                mActivity.serviceErrorListener())
        {
            @Override
            protected Response<JoinPartyResponse> parseNetworkResponse(NetworkResponse response)
            {
                Log.i(TAG, "Get the join party http request response and start to parse the response");
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

                    Log.i(TAG, "Login Http response body----> " + json);
                    return Response.success((JoinPartyResponse) JSONUtils.toObject(json, JoinPartyResponse.class),
                            HttpHeaderParser.parseCacheHeaders(response));

                }
                else
                {
                    Log.e(TAG, "Failed to  join party  and http code is ---->" + statusCode);
                    // Create a new thread for Toast message
                    mActivity.runOnUiThread(new Runnable()
                    {
                        public void run()
                        {

                            Toast.makeText(mActivity.getApplicationContext(),
                                    "Unknow issue happend, please try it later!", Toast.LENGTH_LONG).show();

                        }
                    });
                    Log.i(TAG, " join party  Failed due to some error!");

                }

                return Response.error(new ParseError());
            }
        };
        Log.i(TAG, "Add  join party  http request in the async queue!");

        mActivity.executeRequest(joinRest);
    }
}
