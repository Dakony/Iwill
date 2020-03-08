package com.example.android.iwill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BeneficiariesActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    FloatingActionButton add_beneficiary;
    //the listview
    ListView listView;

    List<BeneficiariesClass> beneficiariesClassList;

    //database reference to get uploads data
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiaries);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Beneficiaries");

        beneficiariesClassList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.beneficiariesList);

        add_beneficiary = (FloatingActionButton)findViewById(R.id.add_beneficiary);
        add_beneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUSerToAddBeneficiary();
            }
        });

        //getting the database reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_BENEFICIARIES);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    BeneficiariesClass beneficiariesClass = postSnapshot.getValue(BeneficiariesClass.class);
                    beneficiariesClassList.add(beneficiariesClass);
                }
                String [] uploads = new String[beneficiariesClassList.size()];
                for (int i = 0; i < uploads.length; i++){
                    uploads[i] = beneficiariesClassList.get(i).getFullname();
                }
                //displaying it to list
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.rows, uploads);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        

    }



    private void sendUSerToAddBeneficiary()
    {
        Intent SetUpIntent = new Intent(this,AddBeneficiaryActivity.class);
        startActivity(SetUpIntent);

    }
}
