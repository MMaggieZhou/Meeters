package com.example.meeters.exceptions;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.meeters.utils.JSONUtils;

public class ServiceException
{
    private static final String TAG = ServiceException.class.getSimpleName();

    /**
     * Returns appropriate message which is to be displayed to the user against
     * the specified error object.
     * 
     * @param error
     *            å¼‚å¸¸volleyError
     * @param context
     * 
     * @return
     */
    public static String getMessage(Object error, Context context)
    {
        if (error instanceof TimeoutError)
        {
            return "Connection timeout,  please try again!";
        }
        else if (isServerProblem(error))
        {
            return handleServerError(error, context);
        }
        else if (isNetworkProblem(error))
        {
            return "Network Exception";
        }
        return "Please see the agent";
    }

    /**
     * Determines whether the error is related to network
     * 
     * @param error
     * @return
     */
    private static boolean isNetworkProblem(Object error)
    {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    /**
     * Determines whether the error is related to server
     * 
     * @param error
     * @return
     */
    private static boolean isServerProblem(Object error)
    {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }

    /**
     * Handles the server error, tries to determine whether to show a stock
     * message or to show a message retrieved from the server.
     * 
     * ¯
     * 
     * @param err
     * @param context
     * @return
     */
    private static String handleServerError(Object err, Context context)
    {
        VolleyError error = (VolleyError) err;

        NetworkResponse response = error.networkResponse;

        if (response != null)
        {
            switch (response.statusCode)
            {
                case 400:
                case 403:
                case 404:
                case 422:
                case 401:
                    String json = "";
                    try
                    {
                        json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        Log.i(TAG, "handleServerError ---->" + json);

                        Map<String, Object> errorMap = JSONUtils.strToMap(json);

                        String message = "Sorry! Unknown issue happened!";
                        if (errorMap == null)
                        {
                            return message;
                        }
                        message = (String) errorMap.get("message");

                        Log.i(TAG, "handleServerError message ---->" + message);

                        return message;
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        Log.e(TAG, "Can not cast the exception from backend");
                        return error.getMessage();
                    }

                case 500:
                case 503:
                    return "There is a issue with the App, please try it later!";
                default:
                    Log.e(TAG, "---->Connection timeout, please try again, status code: " + response.statusCode
                            + "message: " + response.headers.toString());
                    return "Connection timeout, please try again";
            }
        }
        return "network error";
    }
}
