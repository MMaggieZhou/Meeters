/**
 * (C) 2014 TheMobies, LLC. All Rights Reserved. Confidential Information of TheMobies, LLC.
 */
package com.example.meeters.fragments;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.meeters.activities.ContentHolderActivity;
import com.example.meeters.meeters.R;

import com.example.meeters.constant.URL;
import com.example.meeters.model.domain.User;
//import com.theMobies.golunch.model.party.GetMyPartyResponse;
//import com.theMobies.golunch.model.party.NearbySearchRequest;
//import com.theMobies.golunch.model.party.StartPartyResponse;
import com.example.meeters.utils.JSONUtils;
import com.example.meeters.base.*;
import com.example.meeters.model.party.*;
@SuppressLint("ValidFragment")
public class SearchNearbyFragment extends DialogFragment implements OnClickListener
{
    @SuppressWarnings("unused")
    private static final String TAG = SearchNearbyFragment.class.getSimpleName();

    private Button mSearch;
    private Button mCancelBtn;
    private ListView mSearchList;
    private String[] mOptions;
    private SearchNearbyAdapter mAdapter;
    private Map<String, Integer> mResultValues;
    private Location mLocation;
    private BaseApplication mBaseApplication;
    private User mCurrentUser;
    protected ArrayList<SearchPartyResponse> mPartyList;
    private BaseActivity mBaseActivity;
    private Context mContext;

    
    public SearchNearbyFragment(BaseApplication application, BaseActivity activity, Context context)
    {
        this.mBaseActivity = activity;
        this.mBaseApplication = application;
        this.mContext = context;
    }
    
    
    @Override
    public void onAttach(Activity activity)

    {

        super.onAttach(activity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        setCancelable(false);
        getDialog().setTitle(R.string.search_nearby_fragment_title);

        View view = inflater.inflate(R.layout.fragment_search_nearby, null, false);

        mSearch = (Button) view.findViewById(R.id.search_nearby_search_btn);
        mCancelBtn = (Button) view.findViewById(R.id.search_nearby_cancel_btn);
        mSearchList = (ListView) view.findViewById(R.id.search_nearby_list);

        mOptions = this.getResources().getStringArray(R.array.search_nearby_options);
        mAdapter = new SearchNearbyAdapter(getActivity(), mOptions);
        mSearchList.setAdapter(mAdapter);

        // setting onclick listener for buttons
        mSearch.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);

        mLocation = mBaseActivity.getLocation();
        mCurrentUser = mBaseApplication.getUser();

        return view;
    }

    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.search_nearby_search_btn:
                search();
                dismiss();
                break;

            case R.id.search_nearby_cancel_btn:
                dismiss();
                cancel();
                break;
        }

    }

    private <T> void search()
    {
        mResultValues = mAdapter.getValues();

        final NearbySearchRequest nearbySearchRequest = getSearchValues();

        if (nearbySearchRequest == null)
        {
            // Exception handling
            return;
        }

        Log.i(TAG, "Search nearby party process ---->" + JSONUtils.toJson(nearbySearchRequest));

         mBaseActivity.showLoading(getActivity().getString(R.string.search_nearby_progress_dialog));

        Response.Listener<SearchResponse> searchPartyListener = new Response.Listener<SearchResponse>()
        {

            @Override
            public void onResponse(SearchResponse searchResponse)
            {
                Log.d(TAG, "On search nearby response");
                mPartyList = searchResponse.getMyParties();
                if (!mPartyList.isEmpty())
                {
                    ((ContentHolderActivity) getActivity()).onSearchReturn(mPartyList);
                }
                else
                {
                     mBaseActivity.dismissLoading();
                     Toast.makeText(mContext,
                     "No match result!", Toast.LENGTH_LONG).show();
                    return;
                }

                mBaseActivity.dismissLoading();
            }
        };

        // TODO: Change api url, double check request
        BaseRestRequest<SearchResponse> searchPartyRest = new BaseRestRequest<SearchResponse>(Method.POST,
                URL.SEARCH_PARTY, null, JSONUtils.toBytes(nearbySearchRequest), searchPartyListener,
                ((BaseActivity) getActivity()).serviceErrorListener())
        {
            @Override
            protected Response<SearchResponse> parseNetworkResponse(NetworkResponse response)
            {
                Log.i(TAG, "Get the search party http request response and start to parse the response");
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

                    Log.i(TAG, "search party  Http response body----> " + json);
                    return Response.success((SearchResponse) JSONUtils.toObject(json, SearchResponse.class),
                            HttpHeaderParser.parseCacheHeaders(response));
                }
                else
                {
                    Log.e(TAG, "Failed to search party  and http code is ---->" + statusCode);
                    // Create a new thread for Toast message
                    getActivity().runOnUiThread(new Runnable()
                    {
                        public void run()
                        {

                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Unknow issue happend, please try it later!", Toast.LENGTH_LONG).show();
                        }
                    });
                    Log.i(TAG, "search party  Failed due to some error!");

                }

                return Response.error(new ParseError());
            }
        };
        Log.i(TAG, "Add search party  http request in the async queue!");

        ((BaseActivity) getActivity()).executeRequest(searchPartyRest);

    }

    private NearbySearchRequest getSearchValues()
    {
        StringBuilder errorMessage = new StringBuilder();

        NearbySearchRequest searchRequest = null;
        try
        {
            searchRequest = new NearbySearchRequest();

            searchRequest.setDistanceOption((mResultValues.get(mOptions[0])).intValue());

            if (mLocation == null)
            {
                errorMessage.append("Turn on your GPS, right now I set your location as seattle for testing ");
                searchRequest.setLatitude(47.6076);
                searchRequest.setLongitude(-122.333);
            }
            else
            {
                searchRequest.setLatitude(mLocation.getLatitude());
                searchRequest.setLongitude(mLocation.getLongitude());
            }
            searchRequest.setUserId(mCurrentUser.getUserId());
            Log.i(TAG, "searchNearbyValues ---->" + JSONUtils.toJson(searchRequest));
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
                Toast.makeText(getActivity().getApplicationContext(), errorMessage.toString(), Toast.LENGTH_SHORT)
                        .show();
                return null;
            }

        }
        return searchRequest;
    }

    private void cancel()
    {
        dismiss();
    }

    public interface OnSearchReturnListener
    {
        public void onSearchReturn(ArrayList<SearchPartyResponse> partyList);
    }

}
