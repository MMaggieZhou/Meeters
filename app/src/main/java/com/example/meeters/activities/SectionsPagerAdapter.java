package com.example.meeters.activities;

/**
 * Created by Mengqi on 11/7/2014.
 */

import java.util.Locale;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.meeters.base.BaseActivity;
import com.example.meeters.base.BaseApplication;
import com.example.meeters.meeters.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter
{
    private static final String TAG = SectionsPagerAdapter.class.getSimpleName();
    private Context mContext;
    private BaseApplication mApplication;
    private BaseActivity mActivity;
    private Fragment[] mFragments;

    /**
     * @param fm
     * @param fragments
     */

    public SectionsPagerAdapter(Context context, FragmentManager fm, Fragment[] fragments)
    {
        super(fm);
        mContext = context;
        mFragments = fragments;

    }

    @Override
    public Fragment getItem(int position)
    {
        return mFragments[position];
    }

    @Override
    public int getCount()
    {
        return mFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        Locale l = Locale.getDefault();
        switch (position)
        {
            case 0:
                return mContext.getString(R.string.title_section1).toUpperCase(l);

            case 1:
                return mContext.getString(R.string.title_section2).toUpperCase(l);

           /*
            case 2:
                return mContext.getString(R.string.title_section3).toUpperCase(l);
            */
        }
        return null;
    }

}