package com.example.android.iwill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class RemiderActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    LinearLayout alarm_l;
    ImageView img_alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remider);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Reminder");

        alarm_l = findViewById(R.id.alarm_l);
        img_alarm = findViewById(R.id.img_alarm);
        alarm_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SetUpIntent = new Intent(RemiderActivity.this,AlarmActivity.class);
                startActivity(SetUpIntent);
            }
        });

        img_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SetUpIntent = new Intent(RemiderActivity.this,AlarmActivity.class);
                startActivity(SetUpIntent);
            }
        });
    }

}
