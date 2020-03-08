package com.example.android.iwill;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class HomeActivity extends AppCompatActivity {
    TextView txt_create_account;
    MaterialEditText register_email, register_password, register_name,register_phone_no;
    EditText mEmail, mPassword;
    ImageButton mLogin;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Init Views
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText)findViewById(R.id.input_password);
        mLogin = (ImageButton) findViewById(R.id.btn_login);

        loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(email.isEmpty()){
                    mEmail.setError("Please Type a valid Email");
                    mEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()){
                    mPassword.setError("Password cannot be Empty");
                    mPassword.requestFocus();
                    return;
                }

                showDialog();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loadingBar.dismiss();
                        if(task.isSuccessful()){
                            LoginInUser();

                        }else{
                            Toast.makeText(getApplicationContext(),"Sorry incorrect Password or email",Toast.LENGTH_LONG).show();

                        }

                    }
                });

            }
        });
        txt_create_account = (TextView) findViewById(R.id.btn_create);
        txt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View register_layout = LayoutInflater.from(HomeActivity.this)
                        .inflate(R.layout.register_layout, null);
                new MaterialStyledDialog.Builder(HomeActivity.this)
                        .setIcon(R.drawable.ic_person_black_24dp)
                        .setTitle("REGISTRATION")
                        .setDescription("Please Fill all the fields")
                        .setCustomView(register_layout)
                        .setNegativeText("CANCEL")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("REGISTER")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                MaterialEditText register_name = (MaterialEditText)register_layout.findViewById(R.id.edt_name);
                                MaterialEditText register_email = (MaterialEditText)register_layout.findViewById(R.id.edt_email);
                                MaterialEditText register_password = (MaterialEditText)register_layout.findViewById(R.id.edt_password);
                                MaterialEditText register_phone_no = (MaterialEditText)register_layout.findViewById(R.id.edt_phone);


                                if(TextUtils.isEmpty(register_name.getText().toString())){
                                    register_name.setError("Please Provide your fullname");
                                    register_name.requestFocus();
                                    return;
                                }

                                if(TextUtils.isEmpty(register_email.getText().toString())){
                                    register_email.setError("Please Provide an email");
                                    register_email.requestFocus();
                                    return;
                                }
                                if(TextUtils.isEmpty(register_password.getText().toString())){
                                    register_email.setError("Please Provide a Password");
                                    register_email.requestFocus();
                                    return;
                                }
                                if(TextUtils.isEmpty(register_phone_no.getText().toString())){
                                    register_email.setError("Please Provide a Phone number");
                                    register_email.requestFocus();
                                    return;
                                }

                                registerUser(register_name.getText().toString().trim(),
                                        register_email.getText().toString().trim(),
                                        register_password.getText().toString().trim(),
                                        register_phone_no.getText().toString().trim());
                            }
                        }).show();
            }
        });
    }

    private void LoginInUser()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showDialog()
    {
        loadingBar.setTitle("Login");
        loadingBar.setMessage("Please wait, while we are Authenticating your Account...");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();
    }

   // @Override
   // protected void onStart() {

       // super.onStart();
        //if(mAuth.getCurrentUser() != null){
       // }else{
        //    sendUSerToLoginActivity();
       // }
   // }

    private void sendUSerToLoginActivity() {
        Intent SetUpIntent = new Intent(this,HomeActivity.class);
        SetUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(SetUpIntent);
        finish();
    }

    private void registerUser(final String name, final String email, String password, final String phone) {

        loadingBar.setTitle("Creating Account");
        loadingBar.setMessage("Please Wait while we are Creating Your Account");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(true);

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(
                                    name,
                                    email,
                                    phone);


                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        loadingBar.dismiss();
                                        sendUserToMainActivity();

                                    }else{
                                        Toast.makeText(HomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(HomeActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
    }

    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(this,MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
