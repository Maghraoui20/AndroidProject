package com.example.authentification;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
TextView signup;
EditText email,password;
Button signin;
TextView forgetPassword;
DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //hideactionbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        initView();
        initEvent();

    }
    private void initView(){


        email= findViewById(R.id.email);
        password=findViewById(R.id.password);
        signin= findViewById(R.id.signin);
        db = new DBHelper(this);
        forgetPassword= findViewById(R.id.txtforgetpassword);
        signup = findViewById(R.id.txtsignup);
    }

    public void initEvent(){
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user= email.getText().toString().trim();

                String pass=password.getText().toString().trim();
                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
                    Toast.makeText(LoginActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                }

                else{
                    Boolean checkuserpass = db.checkusernamepassword(user,pass);
                    if(checkuserpass){
                        // Storing data into SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                        myEdit.putString("email", user);

// Once the changes have been made, we need to commit to apply those changes made,
// otherwise, it will throw an error
                        myEdit.commit();
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensignup();
            }
        });



        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openforgetpassword();
            }
        });


    }
    public void openforgetpassword() {
        Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
        startActivity(intent);
    }
    public  void opensignup(){
        Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
        startActivity(intent);
    }



}