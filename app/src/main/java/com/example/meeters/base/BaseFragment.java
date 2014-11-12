/**
 * (C) 2014 TheMobies, LLC. All Rights Reserved. Confidential Information of TheMobies, LLC.
 */
package com.example.meeters.base;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
//import com.theMobies.golunch.constant.IANames;
import com.example.meeters.exceptions.ServiceException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

public abstract class BaseFragment extends DialogFragment
{
    @SuppressWarnings("unused")
    private static final String TAG = BaseFragment.class.getSimpleName();

    protected BaseApplication mApplication;
    protected static BaseApplication mBaseApplication;
    protected BaseActivity mActivity;
    protected Context mContext;
    protected View mView;
    private ProgressDialog mDialog;

    public BaseFragment(BaseApplication application, BaseActivity activity, Context context)
    {
        mApplication = application;
        mActivity = activity;
        mContext = context;
    }
    /*public static BaseFragment newInstance(BigInteger id, String nickname)
    {
        BaseFragment f=new BaseFragment();
        Bundle args = new Bundle();
        args.putInt("UserId", id.intValue());
        args.putString("Nickname", nickname);
        f.setArguments(args);
        return f;

    }*/
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

    /**
     * show Loading Dialog
     */
    public void showLoading(String message)
    {
        if (mDialog == null)
        {
            mDialog = new ProgressDialog(this.mContext);
            if (StringUtils.isBlank(message))
            {
                message = "Running...";
            }
            mDialog.setTitle(message);
            mDialog.show();
        }
        else
        {
            mDialog.show();
        }
    }

    /**
     * @param request
     */
    public void executeRequest(Request<?> request)
    {
        mBaseApplication.getRequestManager().addRequest(request, TAG);
    }
}
