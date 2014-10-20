package com.example.meeters.base;

/**
 * Created by Mengqi on 10/18/2014.
 */

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie2;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Singleton asynctask restful api clinet
 *
 * @reference:
 * @http://www.androidhive.info/2014/05/android-working-with- volley -library-1/
 */
public class BaseRequestManager
{
    private static BaseRequestManager mInstance = null;
    private RequestQueue mRequestQueue;

    private static final String TAG = BaseRequestManager.class.getSimpleName();

    private DefaultHttpClient mDefaultHttpClient;

    private ImageLoader mImageLoader;

    private BaseRequestManager()
    {
    }

    public static synchronized BaseRequestManager getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new BaseRequestManager();
        }
        return mInstance;
    }

    public void init(Context context)
    {
        mDefaultHttpClient = new DefaultHttpClient();
        // create the request queue
        mRequestQueue = Volley.newRequestQueue(context, new HttpClientStack(mDefaultHttpClient));
    }

    public RequestQueue getRequestQueue()
    {
        if (mRequestQueue != null)
        {
            return mRequestQueue;
        }
        else
        {
            throw new IllegalStateException("RequestQueue is not initialized!");
        }
    }

    public <T> void addRequest(Request<?> request, Object tag)
    {
        // set the default tag if tag is empty
        request.setTag(tag != null ? tag : TAG);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag)
    {
        if (mRequestQueue != null)
        {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void cancelAll(Object tag)
    {
        getRequestQueue().cancelAll(tag);
    }

    /**
     * Method to set a cookie
     */
    public void setCookie(String key, String value)
    {
        CookieStore cs = mDefaultHttpClient.getCookieStore();
        // create a cookie
        cs.addCookie(new BasicClientCookie2(key, value));
    }


}
