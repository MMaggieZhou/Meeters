package com.example.meeters.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.meeters.meeters.R;
import com.example.meeters.base.*;
import com.example.meeters.model.party.SearchPartyResponse;
import com.example.meeters.model.party.StartPartyResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class NearbyFragment extends BaseFragment {
    private static final String TAG = MyPartyFragment.class.getSimpleName();
    protected ArrayList<StartPartyResponse> mPartyList;
    protected PartyListAdapter mAdapter;
    private ListView mListView;
    private BaseActivity mBaseActivity;
    private android.support.v4.app.Fragment mFragment;
    private HashMap<Marker, BigInteger> id = new HashMap<Marker, BigInteger>();


    private GoogleMap map;

    public NearbyFragment(BaseApplication application, BaseActivity activity, Context context)
    {
        super(application, activity, context);
        this.mBaseActivity = activity;
        this.mFragment = this;
        //this.id = new HashMap<Marker, BigInteger>();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nearby, container, false);
        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                BigInteger partyId = id.get(marker);
                return false;
            }
        });
        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setPartyList(ArrayList<SearchPartyResponse> partyList) {
        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        for (SearchPartyResponse party: partyList) {
            LatLng location = new LatLng(party.getLatitude(),party.getLongitude());
            Marker marker = map.addMarker(new MarkerOptions().position(location)
                    .title(party.getTheme())
                    .snippet(party.getTheme())
                    .icon(BitmapDescriptorFactory.defaultMarker()));

            if (party.getJoin()) {
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            } else {
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            }
            id.put(marker, party.getPartyId());
        }
        // Move the camera instantly to hamburg with a zoom of 15.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(((BaseActivity) getActivity()).getLocation().getLatitude(), ((BaseActivity) getActivity()).getLocation().getLongitude()), 2));
        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }
    public class PartyListAdapter extends BaseAdapter {
        private ArrayList<SearchPartyResponse> mMyParties;
        private Context mContext;
        Random randomGenerator = new Random(16);
        ArrayList<Drawable> drawables = new ArrayList<Drawable>();

        public PartyListAdapter(ArrayList<SearchPartyResponse> partyList, Context context) {
            this.mMyParties = partyList;
            this.mContext = context;
            Resources res = mContext.getResources();
        }

        @Override
        public int getCount() {
            return mMyParties.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_party, parent, false);
            }
            BigDecimal dis = mMyParties.get(position).getDistance();
            String disData = "";
            if (dis == null) {
                disData = "? mile";
            } else if (dis.compareTo(BigDecimal.ONE) <= 0) {
                disData = dis.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + " mile";
            } else {
                disData = dis.setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + " miles";
            }
            return convertView;
        }

        private class ViewHolder {

        }
    }
}
