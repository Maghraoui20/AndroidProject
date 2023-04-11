package com.example.authentification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewAccountActivity extends AppCompatActivity {
EditText name, email, password, cPassword;
Button signup;
DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        //hide-actionbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initView();
        initEvent();
    }


    private void initView() {

        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        email=findViewById(R.id.email);

        cPassword=findViewById(R.id.cpassword);
        signup=findViewById(R.id.signup);

        db = new DBHelper(this);


    }

    public void initEvent(){
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                String cPass = cPassword.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(cPass) )
                {
                    Toast.makeText(NewAccountActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                }

                else {
                    if (pass.equals(cPass)) {
                        Boolean checkuser = db.checkemail(mail);

                        if (!checkuser) {

                            Boolean insert = db.insertdata(mail,username, pass);
                            if (insert) {
                                // Storing data into SharedPreferences
                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                                myEdit.putString("token", "qsjhdqsjdklqhjd");

// Once the changes have been made, we need to commit to apply those changes made,
// otherwise, it will throw an error
                                myEdit.commit();
                                Toast.makeText(NewAccountActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(NewAccountActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(NewAccountActivity.this, "Email exists" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        Toast.makeText(NewAccountActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });
    }

    }