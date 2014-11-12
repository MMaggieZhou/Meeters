package com.example.meeters.activities;
import java.util.ArrayList;
import com.example.meeters.fragments.NearbyFragment;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.meeters.base.BaseActivity;
import com.example.meeters.base.BaseApplication;
import com.example.meeters.meeters.R;
import com.example.meeters.activities.Profile.*;
//import com.theMobies.golunch.activity.myProfiles.MyProfilesActivity;
//import com.theMobies.golunch.constant.OnSearchReturnListener;
import com.example.meeters.fragments.MyPartyFragment;
import com.example.meeters.fragments.*;
//import com.theMobies.golunch.fragment.NearByFragment;
//import com.theMobies.golunch.fragment.SearchNearbyFragment;
//import com.theMobies.golunch.fragment.WelcomeFragment;
//import com.theMobies.golunch.model.party.StartPartyResponse;
import com.example.meeters.base.*;
public class ContentHolderActivity extends BaseActivity implements ActionBar.TabListener
{
    private static final String TAG = ContentHolderActivity.class.getSimpleName();
    private static final int START_PARTY_REQUEST = 4;
    // private BaseApplication mApplication;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ActionBar mActionBar;
    private Location mLocation;

    private Fragment[] mFragments;

    public ContentHolderActivity()
    {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_title);
        setContentView(R.layout.content_frame);
        mBaseApplication = (BaseApplication) getApplication();
        mCurrentUser = mBaseApplication.getUser();
        initFragments();
        initViewPager();

        // Test myLocation
        mLocation = getLocation();
        if (mLocation != null)
        {
            double dLong = mLocation.getLongitude();
            double dLat = mLocation.getLatitude();
            Log.d(TAG, "My location is: " + dLong + ", " + dLat);
        }

    }

    protected void initViewPager()
    {
        Log.i(TAG, "Init the view pager");
        // Initilization
        mViewPager = (ViewPager) findViewById(R.id.pager);
        final ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), mFragments);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount());

        /**
         * on swiping the viewpager make respective tab selected
         * */
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });

        // Adding Tabs
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++)
        {
            actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
        }

    }

    protected void initFragments()
    {
        mFragments = new Fragment[2];
        mFragments[0] = new MyPartyFragment(mBaseApplication, this, this);
        mFragments[1] = new NearbyFragment(mBaseApplication, this, this);
        //mFragments[1].setTargetFragment(mFragments[0], JOIN_PARTY_REQUEST);
        //mFragments[2] = new WelcomeFragment(mBaseApplication, this, this);

    }

    @Override
    public void onResume()
    {
        super.onResume();

    }

    @Override
    protected void initViews()
    {

    }

    @Override
    protected void initEvents()
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    // Main overflow menu options

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();

        switch (itemId)
        {
            /*
            case R.id.topmenu_action_map:
                navigateToMap();
                break;
            */
            case R.id.topmenu_action_profile:
                updateProfile();
                break;
            case R.id.topmenu_action_logout:
                logout();
                break;
            case R.id.topmenu_action_contact:
                help();
                break;

            case R.id.topmenu_action_search:
                searchNearby();
                // TODO Implement search activity (or other menu options here)
                break;
            case R.id.topmenu_action_start_new_party:
                startNewParty();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /* Debug needed */
    private void navigateToMap()
    {
        //Intent intent = new Intent(this, MapActivity.class);
        //startActivity(intent);
    }

    private void logout()
    {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        SharedPreferences settings = PreferenceManager
                                .getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = settings.edit();
                        editor.remove("currentUser");
                        editor.commit();
                        Intent intent = new Intent(ContentHolderActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);



                        // editor.clear();
                        startActivity(intent);
                        finish();

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }

        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    private void help()
    {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft)
    {

    }

    @Override
    public void onTabReselected(Tab tab, android.app.FragmentTransaction ft)
    {

    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        // TODO Auto-generated method stub

    }

    private void updateProfile()
    {
        //Intent intent = new Intent(this, ProfileActivity.class);
        //startActivity(intent);
    }

    private void searchNearby()
    {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
         SearchNearbyFragment mydialog = new SearchNearbyFragment(mBaseApplication, this, this);
        mydialog.show(manager, "");
    }

    private void startNewParty()
    {
        Intent intent = new Intent(this, StartPartyActivity.class);
        startActivityForResult(intent,START_PARTY_REQUEST);
    }

    /*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == START_PARTY_REQUEST) {
            if (resultCode == START_PARTY_RESPONSE) {
                mFragments[0].onActivityResult(requestCode, resultCode, data);
            }
        }
    }
    public void onSearchReturn(ArrayList<StartPartyResponse> partyList)
    {
        ((NearByFragment) mFragments[1]).setPartyList(partyList);
        ((NearByFragment) mFragments[1]).mAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(1);
    }
    */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
