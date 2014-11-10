package com.example.meeters.base;
import com.example.meeters.database.DBHander;
import com.example.meeters.model.domain.*;

import android.app.Application;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by Mengqi on 10/18/2014.
 */
public class BaseApplication extends Application
{
    @SuppressWarnings("unused")
    private static final String TAG = BaseApplication.class.getSimpleName();

    private User user;
    private DBHander dbHander;
    private LocationManager locManager;
    public Location myLocation;
    private String bestProvider;
    public static BaseRequestManager requestManager;
    // TODO This token is for user identification, when the Android login , the
    // backend will auto generated a token for the user, UI should store the
    // token in sqlLite for reuse, when the user logout UI should delete the
    // token from SqLite and backend will delete the token too.
    private static String authToken = null;

    @Override
    public void onCreate()
    {
        super.onCreate();
        user = new User();
        initBaseRrequestManager();
//        initProvider();
//        locationTracker();
    }

    private void initBaseRrequestManager()
    {
        requestManager = BaseRequestManager.getInstance();
        requestManager.init(this);
    }

    public static String getAuthToken()
    {
        return authToken;
    }

    public static void setAuthToken(String authToken)
    {
        BaseApplication.authToken = authToken;
    }

    public BaseRequestManager getRequestManager()
    {
        return requestManager;
    }

    // public setRequestManager(BaseRequestManager requestManager) {
    // this.requestManager = requestManager;
    // }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public DBHander getDbHander()
    {
        return dbHander;
    }

    public void setDbHander(DBHander dbhander){this.dbHander=dbhander;}

}
