package com.example.meeters.activities.Profile;

/**
 * Created by fox on 2014/11/9.
 */

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.example.meeters.base.BaseActivity;
import com.example.meeters.base.BaseApplication;
import com.example.meeters.meeters.R;

public class ProfileActivity extends BaseActivity implements OnItemClickListener{
    private TextView etUsername;
    private TextView etEmail;
    private TextView etFirstname;
    private TextView etLastname;
    private TextView etGender;
    private TextView etPhone;


   @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
        initEvents();
    }

    @Override
    protected void initViews()
    {
        etUsername=(TextView) findViewById(R.id.username);
        etEmail=(TextView) findViewById(R.id.email);
        etFirstname=(TextView) findViewById(R.id.firstname);
        etLastname=(TextView) findViewById(R.id.lastname);
        etGender=(TextView) findViewById(R.id.gender);
        etPhone=(TextView) findViewById(R.id.phone);

        mBaseApplication = (BaseApplication) getApplication();
        mCurrentUser = mBaseApplication.getUser();

        etUsername.setText(mCurrentUser.getNickname());
        etEmail.setText(mCurrentUser.getEmail());
        etFirstname.setText(mCurrentUser.getFirstname());
        etLastname.setText(mCurrentUser.getLastname());
        etGender.setText(mCurrentUser.getGender());
        etPhone.setText(mCurrentUser.getPhone());

    }

    @Override
    protected void initEvents()
    {
        // TODO Auto-generated method stub
        etUsername.setOnClickListener(this);
        etEmail.setOnClickListener(this);
        etFirstname.setOnClickListener(this);
        etLastname.setOnClickListener(this);
        etGender.setOnClickListener(this);
        etPhone.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        switch(v.getId()){
            case R.id.username:
                NicknameFragment nickname = new NicknameFragment((BaseApplication) getApplication(), this, this);
                nickname.show(manager, "");
                mBaseApplication = (BaseApplication) getApplication();
                mCurrentUser = mBaseApplication.getUser();
                etUsername.setText(mCurrentUser.getNickname());
                break;
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        // TODO Auto-generated method stub

    }



}
