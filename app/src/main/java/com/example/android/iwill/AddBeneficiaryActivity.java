package com.example.android.iwill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.StrictMode.ThreadPolicy.Builder;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AddBeneficiaryActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    private Toolbar mToolbar;
    EditText input_name, input_email, input_phone;
    Spinner input_relationship, input_legal;
    Button btn_submit;
    ProgressBar progressBar;

    FirebaseAuth mAuth;
    String relate, legal;



    PhoneAuthProvider.ForceResendingToken token;

    DatabaseReference mDatabaseReference, mUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beneficiary);




        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Beneficiaries");


        //getting firebase objects
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_BENEFICIARIES);

        input_name = (EditText)findViewById(R.id.input_name);
        input_email = (EditText) findViewById(R.id.input_email);
        input_phone = (EditText) findViewById(R.id.input_phone);


        input_relationship = (Spinner) findViewById(R.id.relationship);
        relate = input_relationship.getSelectedItem().toString();

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Relationship,android.R.layout.simple_spinner_item);
       // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //input_relationship.setAdapter(adapter);

        input_legal = (Spinner) findViewById(R.id.legal_solicitors);
        legal = input_legal.getSelectedItem().toString();



        btn_submit = (Button)findViewById(R.id.btn_submit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = input_name.getText().toString();
                String email = input_email.getText().toString();
                String phone_number = input_phone.getText().toString();
                String relationship = relate;
                String legal_solicitor = legal;


                if(TextUtils.isEmpty(fullname)){
                    input_name.setError("Please Provide  fullname");
                    input_name.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    input_email.setError("Please Provide  Email");
                    input_email.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(phone_number)){
                    input_phone.setError("Please Provide  Phone number");
                    input_phone.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(relationship)){
                    //input_relationship.setError("Please Provide your fullname");
                    //input_email.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(legal_solicitor)){
                    //input_email.setError("Please Provide your fullname");
                    //input_email.requestFocus();
                    return;
                }

                registerBeneficiary(fullname,email,phone_number, relationship,legal_solicitor);
                //getting the database reference
                mUpload = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
                mUpload.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            String key = ds.getKey();
                           // Log.d(TAG, "onDataChange: ");
                            //UploadWIll uploadWIll = postSnapshot.getValue(UploadWIll.class);
                            //mDatabaseReference.updateChildren((Map<String, Object>) uploadWIll);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }



    private void registerBeneficiary(String fullname, String email, String phone_number, String relationship, String legal_solicitor) {
        progressBar.setVisibility(View.VISIBLE);
        BeneficiariesClass beneficiariesClass = new BeneficiariesClass(
                fullname,
                email,
                phone_number,
                relationship,
                legal_solicitor
        );
        mDatabaseReference.child(mDatabaseReference.push().getKey())
                .setValue(beneficiariesClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddBeneficiaryActivity.this, "Your Beneficiary has been Added", Toast.LENGTH_SHORT).show();

                sendUserToAddBeneficiary();
            }
        });
    }






    private void sendUserToAddBeneficiary() {
        EditText etMail = (EditText) findViewById(R.id.input_email);
        Intent i = new Intent(this, SendMailActivity.class);
        i.putExtra("email", etMail.getText().toString());
        startActivity(i);
    }
}
