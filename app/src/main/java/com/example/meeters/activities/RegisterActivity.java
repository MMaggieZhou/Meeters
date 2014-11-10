package com.example.meeters.activities;

/**
 * Created by fox on 10/18/2014.
 */
import java.io.UnsupportedEncodingException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.example.meeters.database.DBHander;
import com.example.meeters.meeters.R;
import com.example.meeters.model.domain.User;
import com.example.meeters.model.user.LoginRequest;
import com.example.meeters.model.user.LoginResponse;
import com.example.meeters.model.user.RegisterRequest;
import com.example.meeters.model.user.RegisterResponse;
import com.example.meeters.utils.InputValidationUtils;
import com.example.meeters.utils.JSONUtils;

public class RegisterActivity extends BaseActivity implements OnClickListener, OnItemClickListener{
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirm;
    private Button btnAdd;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mBaseApplication = (BaseApplication) getApplication();
        mCurrentUser = mBaseApplication.getUser();
        initViews();
        initEvents();
    }

    protected void initViews()
    {

        etUsername = (EditText) findViewById(R.id.username);
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        etConfirm = (EditText) findViewById(R.id.password_confirm);

        btnAdd = (Button) findViewById(R.id.done_button);
    }

    protected void initEvents()
    {
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.done_button:
                if ((!InputValidationUtils.validateNickname(etUsername)))
                {
                    Toast.makeText(getApplicationContext(), "invalid nickname!", Toast.LENGTH_SHORT).show();
                    etUsername.setError("!");
                    break;
                }

                if((!InputValidationUtils.validateEmail(etEmail)))
                {
                    Toast.makeText(getApplicationContext(), "invalid email!", Toast.LENGTH_SHORT).show();
                    etEmail.setError("!");
                    break;
                }

                if ((!InputValidationUtils.validatePwd(etPassword)))
                {
                    Toast.makeText(getApplicationContext(), "invalid password!", Toast.LENGTH_SHORT).show();
                    etPassword.setError("!");
                    break;
                }

                if(!etPassword.getText().toString().equals(etConfirm.getText().toString())){
                    Toast.makeText(getApplicationContext(), "passwords don't match! Please try again.", Toast.LENGTH_SHORT).show();
                    etConfirm.setError("!");
                    break;
                }
               CreateAccount();
                break;

            default:
                return;
        }
    }



    private <T> void CreateAccount() {
        // this.output = (TextView) this.findViewById(R.id.out_text);
        Log.i("Register","onCreate");
        final RegisterRequest registerRequest = new RegisterRequest();

        showLoading("Register...");

        registerRequest.setNickname(etUsername.getText().toString().trim());
        registerRequest.setEmail(etEmail.getText().toString().trim());
        registerRequest.setPassword(etPassword.getText().toString().trim());

       /* Location location = getLocation();

        if (location == null)
        {
            Toast.makeText(getApplicationContext(),
                    "Turn on your GPS, right now I set your location as columbus for testing! ", Toast.LENGTH_LONG)
                    .show();
            registerRequest.setLatitude(39.9833);
            registerRequest.setLongitude( -82.9988889);
        }
        else
        {
            registerRequest.setLatitude(location.getLatitude());
            registerRequest.setLongitude(location.getLongitude());
        }*/

        Response.Listener<RegisterResponse> registerLisenter = new Response.Listener<RegisterResponse>()
        {


            @Override
            public void onResponse(RegisterResponse registerResponse)
            {

                User user = new User();
                user.setEmail(registerResponse.getEmail());
                user.setNickname(registerResponse.getNickname());
                //user.setPassword(registerResponse.getPassword());
                user.setAuthToken(registerResponse.getAuthToken());
                mBaseApplication.setUser(user);
                mBaseApplication.setAuthToken(registerResponse.getAuthToken());

                //After register http request, set user info to application! And Navigate to main pages!
                dismissLoading();

                DBHander dbHander = mBaseApplication.getDbHander();
                dbHander.addUser(mBaseApplication.getUser());
                Intent intent = new Intent(RegisterActivity.this, ContentHolderActivity.class);
                startActivity(intent);
                finish();
            }
        };

        BaseRestRequest<RegisterResponse> registerRest = new BaseRestRequest<RegisterResponse>(Method.POST,
                URL.REGISTER, null, JSONUtils.toJson(registerRequest).getBytes(), registerLisenter,
                serviceErrorListener())

        {

            @Override
            protected Response<RegisterResponse> parseNetworkResponse(NetworkResponse response)
            {
               // Log.i(TAG, "Get the register http request response and start to parse the response");
                final int statusCode = response.statusCode;

                if (statusCode == 201)
                {
                    String json = "";
                    try
                    {
                        json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                       // Log.i(TAG, "Status cod 201 and the message ---->" + json);
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        //Log.d(TAG, "UnsupportedEncodingException ---->" + e.getMessage());
                        return Response.error(new ParseError(e));
                    }

                   //Log.i(TAG, "Register Http response body----> " + json);
                    return Response.success((RegisterResponse) JSONUtils.toObject(json, RegisterResponse.class),
                            HttpHeaderParser.parseCacheHeaders(response));

                }
                else
                {
                   // Log.e(TAG, "Failed to register and http code is ---->" + statusCode);
                    // Create a new thread for Toast message
                    RegisterActivity.this.runOnUiThread(new Runnable()
                    {
                        public void run()
                        {

                            Toast.makeText(getApplicationContext(), "Unknow issue happend, please try it later!",
                                    Toast.LENGTH_LONG).show();

                        }
                    });
                    //Log.i(TAG, "Register Failed due to some error!");
                }

                return Response.error(new ParseError());
            }

        };
        //Log.i(TAG, "Add regist http request in the async queue!");
        executeRequest(registerRest);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
