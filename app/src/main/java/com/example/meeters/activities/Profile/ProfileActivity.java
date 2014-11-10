package com.example.meeters.activities.Profile;

/**
 * Created by fox on 2014/11/9.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.meeters.base.BaseActivity;
import com.example.meeters.base.BaseApplication;
import com.example.meeters.meeters.R;

public class ProfileActivity{
    private TextView etUsername;
    private TextView etEmail;
    private TextView etFirstname;
    private TextView etLastname;
    private TextView etPassword;
    private TextView etGender;
    private TextView etPhone;


   /* @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);

        mBaseApplication = (BaseApplication) getApplication();
        mCurrentUser = mBaseApplication.getUser();

        getListItem();
        initViews();

        ProfileAdapter adapter = new ProfileAdapter(this, listData);
        mProfileList.setAdapter(adapter);

        mProfileList.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                switch (position)
                {
                    case 0:
                        NickNameFragment nickname = new NickNameFragment(mCurrentUser.getNickname());
                        nickname.show(manager, "");
                        break;
                    case 1:
                        EmailFragment email = new EmailFragment(mCurrentUser.getEmail());
                        email.show(manager, "");
                        break;
                    case 2:
                        GenderFragment gender = new GenderFragment(mCurrentUser.getGender());
                        gender.show(manager, "");
                        break;
                    case 3:
                        PhoneFragment phone = new PhoneFragment(mCurrentUser.getPhone());
                        phone.show(manager, "");
                        break;
                    case 4:
                        FirstnameFragment firstname = new FirstnameFragment(mCurrentUser.getFirstname());
                        firstname.show(manager, "");
                        break;
                    case 5:
                        LastnameFragment lastname = new LastnameFragment(mCurrentUser.getLastname());
                        lastname.show(manager, "");
                        break;
                    case 6:
                        PasswordFragment password = new PasswordFragment();
                        password.show(manager, "");
                        break;
                }

            }

        });
    }

    private List<Map<String, Object>> getListItem()
    {
        listData = new ArrayList<Map<String, Object>>();
        String[] options = this.getResources().getStringArray(R.array.myProfile_options);

        Map<String, Object> item1 = new HashMap<String, Object>();
        item1.put("ItemTitle", options[0]);
        item1.put("ItemText", mCurrentUser.getNickname());
        listData.add(item1);

        Map<String, Object> item2 = new HashMap<String, Object>();
        item2.put("ItemTitle", options[1]);
        item2.put("ItemText", mCurrentUser.getEmail());
        listData.add(item2);

        Map<String, Object> item3 = new HashMap<String, Object>();
        item3.put("ItemTitle", options[2]);
        item3.put("ItemText", mCurrentUser.getGender());
        listData.add(item3);

        Map<String, Object> item4 = new HashMap<String, Object>();
        item4.put("ItemTitle", options[3]);
        item4.put("ItemText", mCurrentUser.getPhone());
        listData.add(item4);

        Map<String, Object> item5 = new HashMap<String, Object>();
        item5.put("ItemTitle", options[4]);
        item5.put("ItemText", mCurrentUser.getFirstname());
        listData.add(item5);

        Map<String, Object> item6 = new HashMap<String, Object>();
        item6.put("ItemTitle", options[5]);
        item6.put("ItemText", mCurrentUser.getLastname());
        listData.add(item6);

        Map<String, Object> item7 = new HashMap<String, Object>();
        item7.put("ItemTitle", options[6]);
        item7.put("ItemText", "");
        listData.add(item7);

        return listData;
    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected void initViews()
    {
        mProfileList = (ListView)this.findViewById(R.id.my_profile_list);

    }

    @Override
    protected void initEvents()
    {
        // TODO Auto-generated method stub

    }*/

}
