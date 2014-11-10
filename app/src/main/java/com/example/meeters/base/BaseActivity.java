
package com.example.meeters.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.example.meeters.model.domain.*;
import com.example.meeters.utils.JSONUtils;

public abstract class BaseActivity extends FragmentActivity implements OnClickListener, OnItemClickListener
{
    private static final String TAG = BaseActivity.class.getSimpleName();

    protected List<AsyncTask<Void, Void, Boolean>> asyncTasks = new ArrayList<AsyncTask<Void, Void, Boolean>>();
    protected static BaseApplication mBaseApplication;
    private ProgressDialog mDialog;
    protected static User mCurrentUser;
    private LocationManager locManager;
    public static Location myLocation;
    private String bestProvider;
    private LocationListener mLocListener;

    /**
	 * 
	 */
    public BaseActivity()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        initProvider();
        initLocationListener();
        locationTracker();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        String userStr = settings.getString("currentUser", "");

        if (StringUtils.isBlank(userStr))
        {
            Log.i(TAG, "Can not get user info from sharedpreferences");
            return;
        }

        try
        {
            mCurrentUser = (User) JSONUtils.toObject(userStr, User.class);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Can not get user info from sharedpreferences");
            return;
        }

    }

    protected void showCustomToast(String text)
    {
        Toast toast = new Toast(BaseActivity.this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    protected void startActivity(Class<?> cls)
    {
        startActivity(cls, null);
    }

    protected void startActivity(Class<?> cls, Bundle bundle)
    {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void startActivity(String action)
    {
        startActivity(action, null);
    }

    protected void startActivity(String action, Bundle bundle)
    {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy()
    {
        clearAsyncTask();
        super.onDestroy();
    }

    protected abstract void initViews();

    protected abstract void initEvents();

    protected void putAsyncTask(AsyncTask<Void, Void, Boolean> asyncTask)
    {
        asyncTasks.add(asyncTask.execute());
    }

    protected void clearAsyncTask()
    {
        Iterator<AsyncTask<Void, Void, Boolean>> iterator = asyncTasks.iterator();
        while (iterator.hasNext())
        {
            AsyncTask<Void, Void, Boolean> asyncTask = iterator.next();
            if (asyncTask != null && !asyncTask.isCancelled())
            {
                asyncTask.cancel(true);
            }
        }
        asyncTasks.clear();
    }

    protected void showToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        // mBaseApplication.getRequestManager().cancelAll(this);
        if (locManager != null && mLocListener != null)
        {
            locManager.removeUpdates(mLocListener);
        }
    }

    /**
     * @param request
     */
    public void executeRequest(Request<?> request)
    {
        mBaseApplication.getRequestManager().addRequest(request, TAG);
    }

    /**
     * ErrorListener listen the error message from the http service request
     * 
     * @return
     */
    public Response.ErrorListener serviceErrorListener()
    {
        return new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                dismissLoading();
                Toast.makeText(BaseActivity.this, "",
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
            mDialog = new ProgressDialog(this);
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
     * Dismiss Dialog
     */
    public void dismissLoading()
    {
        if (mDialog != null && mDialog.isShowing())
        {
            mDialog.dismiss();
        }
    }

    /* Init location service provider. */
    private void initProvider()
    {
        bestProvider = null;
        Criteria criteria = new Criteria();
        bestProvider = locManager.getBestProvider(criteria, false);
        //TODO
        if (bestProvider == null)
        {
            Toast.makeText(BaseActivity.this, "Please turn on your GPS or connect to WIFI", Toast.LENGTH_LONG).show();
        }
        
    }

    private void initLocationListener()
    {
        mLocListener = new LocationListener()
        {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {
            }

            @Override
            public void onProviderEnabled(String provider)
            {
                // initProvider();
            }

            @Override
            public void onProviderDisabled(String provider)
            {

            }

            @Override
            public void onLocationChanged(Location location)
            {
                // initProvider();
            }
        };

    }

    /* Track user location with specific time interval. */
    private void locationTracker()
    {
        if (bestProvider != null) {
            locManager.requestLocationUpdates(bestProvider, 5 * 1000, 8, mLocListener);
            myLocation = locManager.getLastKnownLocation(bestProvider);



        /* mandatory null location check */
        if (myLocation == null)
        {
            Log.i(TAG, "Bestlocation returns null location");
            if (bestProvider.equals(LocationManager.GPS_PROVIDER))
            {
                bestProvider = null;
                locManager.removeUpdates(mLocListener);

                bestProvider = LocationManager.NETWORK_PROVIDER;
                locationTracker();
            }
            else if (bestProvider.equals(LocationManager.NETWORK_PROVIDER))
            {
                bestProvider = null;
                locManager.removeUpdates(mLocListener);

                bestProvider = LocationManager.PASSIVE_PROVIDER;
                locationTracker();
            }
            else if (bestProvider.equals(LocationManager.PASSIVE_PROVIDER))
            {
                Toast.makeText(BaseActivity.this, "Cannot retrieve your location, check your location settings", 
                        Toast.LENGTH_LONG).show();
            }
        }
        }
    }

    public Location getLocation()
    {
        initProvider();
        return myLocation;
    }

    public void setLocation(Location myLocation)
    {
        BaseActivity.myLocation = myLocation;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initProvider();
        initLocationListener();
        locationTracker();
    }

}
