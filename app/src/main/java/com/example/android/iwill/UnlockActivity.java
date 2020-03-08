package com.example.android.iwill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class UnlockActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private Toolbar mToolbar;

    EditText et1,et2,et3,et4,et5,et6;
    Button btn_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Unlock Will");

        SharedPreferences sharedPreferences = this.getSharedPreferences("CodePref", Context.MODE_PRIVATE);
        final String unlockCode = sharedPreferences.getString("Verification","");


        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);

        btn_submit = findViewById(R.id.btn_submit);


        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    et2.requestFocus();
                }
                else if(s.length()==0)
                {
                    et1.clearFocus();
                }
            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    et3.requestFocus();
                }
                else if(s.length()==0)
                {
                    et1.requestFocus();
                }
            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    et4.requestFocus();
                }
                else if(s.length()==0)
                {
                    et2.requestFocus();
                }
            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    et5.requestFocus();
                }
                else if(s.length()==0)
                {
                    et3.requestFocus();
                }
            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    et6.requestFocus();
                }
                else if(s.length()==0)
                {
                    et4.requestFocus();
                }
            }
        });

        et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==1)
                {
                    et6.clearFocus();
                }
                else if(s.length()==0)
                {
                    et5.requestFocus();
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etText1 = et1.getText().toString();
                String etText2 = et2.getText().toString();
                String etText3 = et3.getText().toString();
                String etText4 = et4.getText().toString();
                String etText5 = et5.getText().toString();
                String etText6 = et6.getText().toString();

                String codeVerify = etText1 + etText2 + etText3 + etText4 + etText5 +etText6;
                if(codeVerify != null && unlockCode != null){
                    //Log.d(TAG, "onClick: " +unlockCode+" " + codeVerify);
                    //Toast.makeText(UnlockActivity.this, "" +unlockCode+" " + codeVerify, Toast.LENGTH_SHORT).show();
                    sendUserToViewWill();
                }else {
                    Toast.makeText(UnlockActivity.this, "Invalid Verification code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendUserToViewWill() {
        Intent SetUpIntent = new Intent(UnlockActivity.this,ViewuploadsActivity.class);
        startActivity(SetUpIntent);
    }
}
