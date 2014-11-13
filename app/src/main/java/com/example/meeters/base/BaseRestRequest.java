
package com.example.meeters.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.meeters.constant.URL;
public class BaseRestRequest<T> extends Request<T>
{
    @SuppressWarnings("unused")
    private static final String TAG = BaseRestRequest.class.getSimpleName();
    protected final Listener<T> mListener;
    protected final byte[] mBody;
    protected Map<String, String> mHeader;
    protected Map<String, String> mParameter;

    /**
     * 
     * @param method
     *            DEPRECATED_GET_OR_POST = -1; GET = 0; POST = 1; PUT = 2;
     *            DELETE = 3; HEAD = 4; OPTIONS = 5; TRACE = 6; PATCH = 7;
     * @param param
     *            TODO
     * @param listener
     * @param errorListener
     * @param uri
     *            //Still thinking about this, ignore this: endpoint ignore the
     *            base (base = http://www.golounc.com:8888/)
     * @param body
     *            Json String bode, use JSONUtils.toJson(obj).getByte() to
     *            generate;
     */
    public BaseRestRequest(int method, URL uri, HashMap<String, String> param, byte[] body, Listener<T> listener,
            ErrorListener errorListener)
    {
        super(method, buildUrl(uri.getValue(), param), errorListener);
        this.mParameter = param;
        this.mBody = body;
        this.mListener = listener;
        this.mHeader = new HashMap<String, String>();
        mHeader.put("Content-Type", "application/json");
        mHeader.put("Accept", "application/json");

        // TODO add the user auth token here for permission validation
       mHeader.put("authToken", BaseApplication.getAuthToken());

        // set default retry policy
        setRetryPolicy(new DefaultRetryPolicy(10000,// DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                                                    // // 2500
                2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 1
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); // 1f);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError
    {
        return mHeader;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError
    {
        return mHeader != null ? mHeader : super.getHeaders();
    }

    @Override
    public byte[] getBody() throws AuthFailureError
    {
        // TODO Auto-generated method stub
        return mBody != null ? mBody : super.getBody();
    }

    @Override
    protected void deliverResponse(T response)
    {
        if (mListener != null)
        {
            mListener.onResponse(response);
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse arg0)
    {
        return null;
    }

    private static String buildUrl(String uri, Map<String, String> param)
    {
        // if (StringUtils.isBlank(uri)) {
        // throw new Exception("Invalide uri");
        // }
        if (param == null || param.size() == 0)
        {
            return URL.BASE_URL.getValue() + uri;
        }

        StringBuilder query = new StringBuilder("?");
        Set<Entry<String, String>> entrySet = param.entrySet();
        String sign = "&";
        String equal = "=";
        for (Entry entry : entrySet)
        {
            query.append(entry.getKey());
            query.append(equal);
            query.append(entry.getValue());
            query.append(sign);
        }

        query.setLength(Math.max(query.length() - 1, 0));

        return URL.BASE_URL.getValue() + uri + query.toString();

        // if (mBaseUrl.endsWith("/") && uri.startsWith("/")) {
        // return mBaseUrl.substring(0, mBaseUrl.length() - 1) + uri;
        // }
        //
        // if (mBaseUrl.endsWith("/") || uri.startsWith("/")) {
        // return mBaseUrl + uri;
        // }
        //
        // return mBaseUrl + "/" + uri;
    }

}
