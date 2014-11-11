package com.example.meeters.activities;

import android.os.Bundle;
import android.os.AsyncTask;
import android.app.*;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.meeters.meeters.R;
import com.example.meeters.base.BaseActivity;

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

    private Button sendButton;
    private Button returnButton;
    private EditText emailText;

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
                return new PasswordAuthentication("yingirisyeon@gmail.com", "topcoder");
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

   /* public boolean sendEmail(String email)
    {
        Mail m = new Mail("yingirisyeon@gmail.com", "topcoder");
        m.set_debuggable(true);
        String[] toArr = {"1345075374@qq.com"};
        m.set_to(toArr);
        m.set_from("customerService@meeters.com");
        m.set_subject("Find Password");
        m.setBody("Your password is ,please reset your password as soon as possible");
        try {
            //m.addAttachment("/sdcard/filelocation");
            if(m.send()) {
                Log.i("IcetestActivity", "Email was sent successfully.");

            } else {
                Log.i("IcetestActivity","Email was sent failed.");
            }
        } catch (Exception e) {
            // Toast.makeText(MailApp.this,
            // "There was a problem sending the email.",
            // Toast.LENGTH_LONG).show();
            Log.e("MailApp", "Could not send email", e);
        }

        return true;
    }*/

    private boolean sendMail(String email) {
        Session session = createSessionObject();
        String subject="Find Password for Meeters.";
        String messageBody="Your password is:  please change your password as soon as possible";
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
