/**
 * (C) 2014 TheMobies, LLC. All Rights Reserved. Confidential Information of TheMobies, LLC.
 */
package com.example.meeters.base;

import com.android.volley.Response;
import com.android.volley.VolleyError;
//import com.theMobies.golunch.constant.IANames;
import com.example.meeters.exceptions.ServiceException;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment
{
    @SuppressWarnings("unused")
    private static final String TAG = BaseFragment.class.getSimpleName();

    protected BaseApplication mApplication;
    protected BaseActivity mActivity;
    protected Context mContext;
    protected View mView;

    public BaseFragment(BaseApplication application, BaseActivity activity, Context context)
    {
        mApplication = application;
        mActivity = activity;
        mContext = context;
    }
    
    /**
     * ErrorListener listen the error message from the http service request
     * 
     * @return
     */

    protected Response.ErrorListener serviceErrorListener(final BaseActivity activity)
    {
        return new Response.ErrorListener( )
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(activity, ServiceException.getMessage(error, activity),
                        Toast.LENGTH_LONG).show();
            }
        };
    }

}
