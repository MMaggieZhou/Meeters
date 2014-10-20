package com.example.meeters.activities;

/**
 * Created by fox on 10/18/2014.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meeters.meeters.R;

public class RegisterActivity extends Activity implements OnClickListener {
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirm;
    private TextView viewAll;
    private DatabaseHelper dh;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = (EditText) findViewById(R.id.username);
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        etConfirm = (EditText) findViewById(R.id.password_confirm);

        View btnAdd = (Button) findViewById(R.id.done_button);
        btnAdd.setOnClickListener(this);
        View btnViewAll = (Button) findViewById(R.id.ViewAll_button);
        btnViewAll.setOnClickListener(this);
        //View btnCancel = (Button) findViewById(R.id.cancel_button);
        //btnCancel.setOnClickListener(this);
        viewAll = (TextView) findViewById(R.id.viewAll);

    }

    private void CreateAccount() {
        // this.output = (TextView) this.findViewById(R.id.out_text);
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirm = etConfirm.getText().toString();
        if ((password.equals(confirm)) && (!username.equals(""))
                && (!password.equals("")) && (!confirm.equals(""))) {
            this.dh = new DatabaseHelper(this);
            this.dh.insert(username, email, password);
            // this.labResult.setText("Added");
            Toast.makeText(RegisterActivity.this, "new record inserted",
                    Toast.LENGTH_SHORT).show();
            //finish();
        } else if ((username.equals("")) || (password.equals(""))
                || (confirm.equals(""))) {
            Toast.makeText(RegisterActivity.this, "Missing entry", Toast.LENGTH_SHORT)
                    .show();
        } else if (!password.equals(confirm)) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("passwords do not match")
                    .setNeutralButton("Try Again",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            })

                    .show();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_button:
                CreateAccount();
                Log.v("insert","Done");
                //finish();
                break;
            //case R.id.cancel_button:

                //break;
            case R.id.ViewAll_button:
                Cursor cur = dh.loadAll();
                StringBuffer sf = new StringBuffer();
                cur.moveToFirst();
                while (!cur.isAfterLast()) {
                    sf.append(cur.getInt(0)).append(" : ").append(
                            cur.getString(1)).append(",").append(cur.getString(3)).append("\n");
                    cur.moveToNext();
                }
                //db.close();
                viewAll.setText(sf.toString());
                break;
        }
    }
}
