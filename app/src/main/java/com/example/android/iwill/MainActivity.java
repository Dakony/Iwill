package com.example.android.iwill;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    LinearLayout add_will, add_beneficiaries, add_remind, unlock_will;
    ImageView img_will, img_beneficiaries, img_remind, img_unlock;

    private FirebaseAuth mAuth;
    String Admin = "8kCggN0y0gPlw1Jqw2ZDyJHjBfx2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");

        add_will = (LinearLayout) findViewById(R.id.add_will);
        add_beneficiaries = (LinearLayout) findViewById(R.id.beneficiaries);
        add_remind = (LinearLayout) findViewById(R.id.add_remind);
        unlock_will = (LinearLayout) findViewById(R.id.unlock_will);


        img_will = (ImageView) findViewById(R.id.img_will);
        img_beneficiaries = (ImageView) findViewById(R.id.img_beneficiaries);
        img_remind = (ImageView) findViewById(R.id.img_remind);
        img_unlock = (ImageView) findViewById(R.id.img_unlock);


        add_will.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              sendUserToAddwill();
            }
        });
        img_will.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToAddwill();
            }
        });

        add_beneficiaries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToAddBeneficiary(); }
        });
        img_beneficiaries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToAddBeneficiary(); }
        });

        add_remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToReminder();
            }
        });
        img_remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToReminder();
            }
        });

        unlock_will.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToUnlock();
            }
        });
        img_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToUnlock();
            }
        });
    }

    private void sendUserToUnlock() {
        Intent SetUpIntent = new Intent(MainActivity.this,UnlockActivity.class);
        startActivity(SetUpIntent);
    }

    private void sendUserToReminder() {
        Intent SetUpIntent = new Intent(MainActivity.this,RemiderActivity.class);
        startActivity(SetUpIntent);

    }

    private void sendUserToAddBeneficiary() {
        Intent SetUpIntent = new Intent(MainActivity.this,BeneficiariesActivity.class);
        startActivity(SetUpIntent);

    }

    private void sendUserToAddwill() {
        Intent SetUpIntent = new Intent(MainActivity.this,AddWillActivity.class);
        startActivity(SetUpIntent);

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
        }
        else{
           sendUSerToLoginActivity();
        }
    }

    private void sendUSerToLoginActivity() {
        Intent SetUpIntent = new Intent(MainActivity.this,HomeActivity.class);
        SetUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(SetUpIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
