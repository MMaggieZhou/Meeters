
package com.example.meeters.activities;

import java.io.UnsupportedEncodingException;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.meeters.base.BaseActivity;
import com.example.meeters.base.BaseApplication;
import com.example.meeters.base.BaseRestRequest;
import com.example.meeters.constant.URL;
import com.example.meeters.meeters.R;
import com.example.meeters.model.user.LoginRequest;
import com.example.meeters.model.user.LoginResponse;
import com.example.meeters.utils.InputValidationUtils;
import com.example.meeters.utils.JSONUtils;

@SuppressLint("NewApi")
public class LoginActivity extends BaseActivity
{
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String TAG1 = LoginActivity.class.getSimpleName() + " lifecycle";

    private EditText etAccount;
    private EditText etPwd;
    private Button btnRegister;
    private Button btnLogin;
    private Button btnFindPassword;

    // private HandyTextView htvForgotPassword;

    // static {
    // StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
    // .permitAll().build();
    // StrictMode.setThreadPolicy(policy);
    // }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(TAG1,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBaseApplication = (BaseApplication) getApplication();
        mCurrentUser = mBaseApplication.getUser();
        initViews();
        initEvents();

    }
    protected void initViews()
    {

        etAccount = (EditText) findViewById(R.id.login_et_account);
        etPwd = (EditText) findViewById(R.id.login_et_pwd);
        // htvForgotPassword = (HandyTextView)
        // findViewById(R.id.login_htv_forgotpassword);
        // TextUtils.addUnderlineText(this, htvForgotPassword, 0,
        // htvForgotPassword.getText().length());

        btnRegister = (Button) findViewById(R.id.login_btn_register);
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        btnFindPassword = (Button) findViewById(R.id.login_btn_find_password);
    }

    protected void initEvents()
    {

        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnFindPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.login_btn_find_password:
                //startActivity(FindPasswordActivity.class);
                break;

            case R.id.login_btn_register:
                startActivity(RegisterActivity.class);
                break;

            case R.id.login_btn_login:
                if ((!InputValidationUtils.validateAccount(etAccount)))
                {
                    Toast.makeText(getApplicationContext(), "invalid account or password!", Toast.LENGTH_SHORT).show();
                    etAccount.setError("!");
                    break;
                }

                if ((!InputValidationUtils.validatePwd(etPwd)))
                {
                    Toast.makeText(getApplicationContext(), "invalid account or password!", Toast.LENGTH_SHORT).show();
                    etPwd.setError("!");
                    break;
                }
                login();
                break;

            default:
                return;
        }
    }

    private <T> void login()
    {
        Log.i(TAG, "Start login process");
        final LoginRequest loginRequest = new LoginRequest();

        showLoading("Loading...");

        loginRequest.setLoginAccount(etAccount.getText().toString().trim());
        // TODO encryption the pwd
        // String base64EncodedCredentials =
        // Base64.encodeToString(etPwd.getText()
        // .toString().getBytes(), Base64.NO_WRAP);
        // loginRequest.setPassword(base64EncodedCredentials);

        loginRequest.setPassword(etPwd.getText().toString().trim());

        Location location = getLocation();

        if (location == null)
        {
            Toast.makeText(getApplicationContext(),
                    "Turn on your GPS, right now I set your location as columbus for testing! ", Toast.LENGTH_LONG)
                    .show();
            loginRequest.setLatitude(39.9833);
            loginRequest.setLongitude( -82.9988889);
        }
        else
        {
            loginRequest.setLatitude(location.getLatitude());
            loginRequest.setLongitude(location.getLongitude());
        }

        Response.Listener<LoginResponse> loginListener = new Response.Listener<LoginResponse>()
        {

            @Override
            public void onResponse(LoginResponse loginResponse)
            {
                Toast.makeText(getApplicationContext(), "login successful!",
                        Toast.LENGTH_LONG).show();
                mCurrentUser.setEmail(loginResponse.getEmail());
                mCurrentUser.setGender(loginResponse.getGender());
                mCurrentUser.setPhone(loginResponse.getPhone());
                mCurrentUser.setNickname(loginResponse.getNickname());
                mCurrentUser.setUserId(loginResponse.getUserId());
                mCurrentUser.setLastname(loginResponse.getLastname());
                mCurrentUser.setFirstname(loginResponse.getNickname());
                mCurrentUser.setAuthToken(loginResponse.getAuthToken());
                mBaseApplication.setAuthToken(loginResponse.getAuthToken());
                //Log.i(TAG, "After login http request, override user info to application! And Navigate to main pages!");
                dismissLoading();
                Toast.makeText(getApplicationContext(), "welcome " + mCurrentUser.getNickname(),
                        Toast.LENGTH_LONG).show();
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("currentUser", JSONUtils.toJson(mCurrentUser));

                //finish();

            }
        };

        BaseRestRequest<LoginResponse> loginRest = new BaseRestRequest<LoginResponse>(Method.POST, URL.LOGIN, null,
                JSONUtils.toBytes(loginRequest), loginListener, serviceErrorListener())
        {
            @Override
            protected Response<LoginResponse> parseNetworkResponse(NetworkResponse response)
            {
                Log.i(TAG, "Get the login http request response and start to parse the response");
                final int statusCode = response.statusCode;

                if (statusCode == 200)
                {
                    String json = "";
                    try
                    {
                        json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        Log.i(TAG, "Status cod 200 and the message ---->" + json);
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        Log.d(TAG, "UnsupportedEncodingException ---->" + e.getMessage());
                        return Response.error(new ParseError(e));
                    }

                    Log.i(TAG, "Login Http response body----> " + json);
                    return Response.success((LoginResponse) JSONUtils.toObject(json, LoginResponse.class),
                            HttpHeaderParser.parseCacheHeaders(response));

                }
                else
                {
                    Log.e(TAG, "Failed to login and http code is ---->" + statusCode);
                    // Create a new thread for Toast message
                    LoginActivity.this.runOnUiThread(new Runnable()
                    {
                        public void run()
                        {

                            Toast.makeText(getApplicationContext(), "Unknow issue happend, please try it later!",
                                    Toast.LENGTH_LONG).show();

                        }
                    });
                    Log.i(TAG, "Login Failed due to some error!");

                }

                return Response.error(new ParseError());
            }
        };
        Log.i(TAG, "Add login http request in the async queue!");

        executeRequest(loginRest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        Log.i(TAG1,"onCreateOptionMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        // TODO Auto-generated method stub

    }

    //
    // @Override
    // public void onBackPressed()
    // {
    // moveTaskToBack(true);
    // super.onBackPressed();
    // }

    @Override
    protected void onStart() {
        Log.i(TAG1,"onStart");
        super.onStart();
    }
    @Override
    protected void onResume()
    {
        Log.i(TAG1,"onResume");
        super.onResume();
    }
    @Override
    protected void onPause(){
        Log.i(TAG1,"onPause");
        super.onPause();
    }
    @Override
    protected void onStop()
    {
        super.onStop();
    }

}
