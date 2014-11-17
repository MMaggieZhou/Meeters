package com.example.meeters.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.AsyncTask;
import android.app.*;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.meeters.base.BaseRestRequest;
import com.example.meeters.constant.URL;
import com.example.meeters.meeters.R;
import com.example.meeters.base.BaseActivity;
import com.example.meeters.model.user.FindPasswordRequest;
import com.example.meeters.model.user.FindPasswordResponse;
import com.example.meeters.model.user.LoginResponse;
import com.example.meeters.utils.JSONUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Properties;
import java.io.UnsupportedEncodingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.*;
import javax.mail.internet.AddressException;
/**
 * Created by Ying on 11/10/14.
 */
public class FindPasswordActivity extends BaseActivity {

    private static final String TAG = "FindPassword";
    private Button sendButton;
    private Button returnButton;
    private EditText emailText;
    private String passWord;
    private String nickName;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        initViews();
        initEvents();
    }

    @Override
    protected void initViews() {
        sendButton=(Button)findViewById(R.id.findPw_send_button);
        returnButton=(Button)findViewById(R.id.findPw_return_button);
        emailText=(EditText)findViewById(R.id.findPassword_email);
    }

    @Override
    protected void initEvents() {
        sendButton.setOnClickListener(this);
        returnButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.findPw_send_button:
                String email=emailText.getText().toString();
                //check if email is valid
                if (!isEmailValid(email)) {
                    Toast.makeText(FindPasswordActivity.this,
                            R.string.emailInvalid, Toast.LENGTH_LONG).show();
                } else {
                    //check if email is in the database
                    if (!emailExist(email)) {
                        Toast.makeText(FindPasswordActivity.this,
                                R.string.emailNotExist, Toast.LENGTH_LONG).show();
                    } else {
                        if (sendMail(email)) {
                            Toast.makeText(FindPasswordActivity.this,
                                    R.string.emailSent, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(FindPasswordActivity.this,
                                    R.string.emailFailed, Toast.LENGTH_LONG).show();

                        }
                    }
                }

                break;
            case R.id.findPw_return_button:
                startActivity(LoginActivity.class);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("yingirisyeon@gmail.com", "happyday91");
            }
        });
    }

    private Message createMessage(String email, String subject, String messageBody, Session session) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("customerService@meeters.com", "Meeters Team"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }



    private boolean sendMail(String email) {
        Session session = createSessionObject();
        String subject="Find Password for Meeters.";
        String messageBody="Your username is: "+nickName+", Your password is:"+passWord+". " +
                "Thank you. ";
        try {
            Message message = createMessage(email, subject, messageBody, session);
            new SendMailTask().execute(message);
        } catch (AddressException e) {
            e.printStackTrace();
            return false;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    public boolean isEmailValid(String email)
    {
        Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = emailPattern.matcher(email);
        if(matcher.find()){
            return true;
        }
        return false;
    }

    //check database
    public boolean emailExist(String email)
    {

        final FindPasswordRequest passwordRequest = new FindPasswordRequest();

        showLoading("Sending...");

       // loginRequest.setLoginAccount(etAccount.getText().toString().trim());
        passwordRequest.setEmail(email);

      //  passwordRequest.setPassword(etPwd.getText().toString().trim());


        Response.Listener<FindPasswordResponse> passwordListener = new Response.Listener<FindPasswordResponse>()
        {

            @Override
            public void onResponse(FindPasswordResponse findpwResponse)
            {
                Toast.makeText(getApplicationContext(), "findPassword successful!",
                        Toast.LENGTH_LONG).show();
                mCurrentUser.setNickname(findpwResponse.getNickName());
                mCurrentUser.setPassword(findpwResponse.getPassword());

                passWord=findpwResponse.getPassword();
                nickName=findpwResponse.getNickName();

              //  mCurrentUser.setGender(loginResponse.getGender());
              //  mCurrentUser.setPhone(loginResponse.getPhone());
              //  mCurrentUser.setNickname(loginResponse.getNickname());
              //  mCurrentUser.setUserId(loginResponse.getUserId());
              //  mCurrentUser.setLastname(loginResponse.getLastname());
              //  mCurrentUser.setFirstname(loginResponse.getNickname());
               // mCurrentUser.setAuthToken(loginResponse.getAuthToken());
              //  mBaseApplication.setAuthToken(loginResponse.getAuthToken());
                //Log.i(TAG, "After login http request, override user info to application! And Navigate to main pages!");
                dismissLoading();
              //  Toast.makeText(getApplicationContext(), "welcome " + mCurrentUser.getNickname(),
                //        Toast.LENGTH_LONG).show();
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("currentUser", JSONUtils.toJson(mCurrentUser));
                editor.commit();

              //  Intent intent = new Intent(LoginActivity.this, ContentHolderActivity.class);
             //   startActivity(intent);
                finish();
            }
        };

        BaseRestRequest<FindPasswordResponse> passwdRest = new BaseRestRequest<FindPasswordResponse>(Request.Method.GET, URL.FIND_PASSWORD, null,
                JSONUtils.toBytes(passwordRequest), passwordListener, serviceErrorListener())
        {
            @Override
            protected Response<FindPasswordResponse> parseNetworkResponse(NetworkResponse response)
            {
                Log.i(TAG, "Get the findpassword http request response and start to parse the response");
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
                    return Response.success((FindPasswordResponse) JSONUtils.toObject(json, FindPasswordResponse.class),
                            HttpHeaderParser.parseCacheHeaders(response));

                }
                else
                {
                    Log.e(TAG, "Failed to findPassword and http code is ---->" + statusCode);
                    // Create a new thread for Toast message
                    FindPasswordActivity.this.runOnUiThread(new Runnable()
                    {
                        public void run()
                        {

                            Toast.makeText(getApplicationContext(), "Unknow issue happend, please try it later!",
                                    Toast.LENGTH_LONG).show();

                        }
                    });
                    Log.i(TAG, "FindPassword Failed due to some error!");

                }

                return Response.error(new ParseError());
            }
        };
        Log.i(TAG, "Add findPassword http request in the async queue!");

        executeRequest(passwdRest);

        if(nickName==null||passWord==null)
        {
            return false;
        }
        else
            return true;
    }

    private class SendMailTask extends AsyncTask<Message, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(FindPasswordActivity.this, "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
