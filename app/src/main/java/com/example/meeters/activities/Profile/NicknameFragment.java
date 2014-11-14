package com.example.meeters.activities.Profile;

/**
 * Created by fox on 2014/11/11.
 */
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.meeters.base.BaseFragment;
import com.example.meeters.constant.URL;

import com.example.meeters.activities.ContentHolderActivity;
import com.example.meeters.activities.RegisterActivity;
import com.example.meeters.meeters.R;

import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.meeters.base.BaseActivity;
import com.example.meeters.base.BaseApplication;
import com.example.meeters.base.BaseRestRequest;
import com.example.meeters.constant.URL;
import com.example.meeters.meeters.R;
import com.example.meeters.model.domain.User;
import com.example.meeters.model.user.LoginRequest;
import com.example.meeters.model.user.LoginResponse;
import com.example.meeters.model.user.ProfileUpdateRequest;
import com.example.meeters.model.user.ProfileUpdateResponse;
import com.example.meeters.model.user.RegisterResponse;
import com.example.meeters.utils.InputValidationUtils;
import com.example.meeters.utils.JSONUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class NicknameFragment  extends DialogFragment implements OnClickListener {
    private static final String TAG = NicknameFragment.class.getSimpleName();
    private View view;
    private Button mUpdateBtn;
    private Button mCancelBtn;
    private EditText mEditText;
    private BaseActivity mBaseActivity;
    private BaseApplication mBaseApplication;
    private String newNickname;

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setCancelable(false);
        getDialog().setTitle("Change User Name.");

        view = inflater.inflate(R.layout.activity_profile_nickname_fragment, null, false);

        mUpdateBtn = (Button) view.findViewById(R.id.profile_nickname_update_btn);
        mCancelBtn = (Button) view.findViewById(R.id.profile_nickname_cancel_btn);

        // setting onclick listener for buttons
        mUpdateBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);

        if (view == null) {
            Log.e("Nickname Fragment:", "View is null");
            return null;
        }
        mEditText = (EditText) view.findViewById(R.id.profile_update_nickname);
        mEditText.setHint(mBaseApplication.getUser().getNickname());
        return view;
    }

    public void setData(BaseActivity activity, BaseApplication application) {
        this.mBaseActivity = activity;
        this.mBaseApplication = application;

    }

    @Override
    public void onClick(View view) {
        newNickname = mEditText.getText().toString().trim();
        switch (view.getId()) {
            case R.id.profile_nickname_update_btn:
                dismiss();
                if (newNickname.equals("") || newNickname.equals(mBaseApplication.getUser().getNickname()) || !InputValidationUtils.validatePwd(mEditText)) {
                    mEditText.setHint(mBaseApplication.getUser().getNickname() + " Please use a new and valid nickname.");
                } else {
                    update();
                }
                break;

            case R.id.profile_nickname_cancel_btn:
                dismiss();
                cancel();
                break;
        }

    }

    private void cancel() {
        // TODO Auto-generated method stub

    }

    private void update() {
        // TODO Auto-generated method stub
        Log.i("Update", "onCreate");
        final ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();
        mBaseActivity.showLoading("Update...");

        profileUpdateRequest.setValue(newNickname);
        profileUpdateRequest.setUserId(mBaseApplication.getUser().getUserId());
        profileUpdateRequest.setType(2);

        Response.Listener<ProfileUpdateResponse> profileUpdateLisenter = new Response.Listener<ProfileUpdateResponse>() {
            @Override
            public void onResponse(ProfileUpdateResponse profileUpdateResponse) {
                Log.i("Update Done.",
                        "After join party http request, override user info to application! And Navigate to profile pages!");
                mBaseApplication.getUser().setNickname(newNickname);
                mBaseActivity.dismissLoading();
                dismiss();

            }
        };

        BaseRestRequest<ProfileUpdateResponse> profileUpdateRest = new BaseRestRequest<ProfileUpdateResponse>(Method.POST,
                URL.PROFILE_UPDATE, null, JSONUtils.toJson(profileUpdateRequest).getBytes(), profileUpdateLisenter,
                mBaseActivity.serviceErrorListener())

        {

            @Override
            protected Response<ProfileUpdateResponse> parseNetworkResponse(NetworkResponse response) {
                // Log.i(TAG, "Get the register http request response and start to parse the response");
                final int statusCode = response.statusCode;

                if (statusCode == 201) {
                    String json = "";
                    try {
                        json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        // Log.i(TAG, "Status cod 201 and the message ---->" + json);
                    } catch (UnsupportedEncodingException e) {
                        //Log.d(TAG, "UnsupportedEncodingException ---->" + e.getMessage());
                        return Response.error(new ParseError(e));
                    }

                    //Log.i(TAG, "Register Http response body----> " + json);
                    return Response.success((ProfileUpdateResponse) JSONUtils.toObject(json, ProfileUpdateResponse.class),
                            HttpHeaderParser.parseCacheHeaders(response));

                } else
                {
                    Log.e(TAG, "Failed to  change nickname  and http code is ---->" + statusCode);
                    // Create a new thread for Toast message
                    mBaseActivity.runOnUiThread(new Runnable()
                    {
                        public void run()
                        {

                            Toast.makeText(mBaseActivity.getApplicationContext(),
                                    "Unknow issue happend, please try it later!", Toast.LENGTH_LONG).show();

                        }
                    });
                    Log.i(TAG, " change nickname  Failed due to some error!");

                }

                return Response.error(new ParseError());
            }
        };
        Log.i(TAG, "Add  change nickname  http request in the async queue!");

        mBaseActivity.executeRequest(profileUpdateRest);
    }
}
