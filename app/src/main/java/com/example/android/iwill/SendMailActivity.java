package com.example.android.iwill;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class SendMailActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private Toolbar mToolbar;
    Button btn_otp;
    int randomNum;
    String verifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);



        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Send Mail");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent in = getIntent();
         verifyCode = in.getExtras().getString("email");




        btn_otp = findViewById(R.id.btn_otp);
        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sentEmail();
            }
        });
    }

    private void sentEmail(){
        try {
            // Construct data
            String apiKey = "apikey=" + "3dRo1S39lok-ATVXzv3KE99mq9UYrHRDMySHtGfvlK";
            Random random = new Random();

            randomNum = random.nextInt(999999);

            SharedPreferences sharedPreferences = this.getSharedPreferences("CodePref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Verification", String.valueOf(randomNum));
            editor.apply();

            String message = "&message=" + "This is your code to activate your will " + randomNum;
            String sender = "&sender=" + "Iwill";
            String numbers = "&numbers=" + "+2347067721573";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            String email = verifyCode;
            Intent intent = new Intent(Intent.ACTION_SENDTO,
                    Uri.parse("mailto:" + email));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Iwill");
            intent.putExtra(Intent.EXTRA_TEXT, "This is your code to activate your will " + randomNum);
            startActivityForResult(Intent.createChooser((intent), "Choose email client"),0);
            Toast.makeText(this, "Sending OTP", Toast.LENGTH_SHORT).show();


            //return stringBuffer.toString();
        } catch (Exception e) {
            Toast.makeText(this, "Error SMS" + e, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "generateOTP: " + e);
            //System.out.println("Error SMS "+e);
            //return "Error "+e;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode  == 0){
            Intent intentGoToMenu = new Intent(this, MainActivity.class);
            startActivity(intentGoToMenu);
        }
    }
}
