package com.example.authentification;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class PasswordActivity extends AppCompatActivity {
EditText emailEditText;
Button resetpassword;
    DBHelper db;
    private String email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        //hideactionbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initView();
        initEvent();





}


    private void initView() {
        emailEditText = findViewById(R.id.email);
        resetpassword = findViewById(R.id.btnresetpasswprd);
        db = new DBHelper(this);
    }
    public void initEvent(){
        resetpassword.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                email = emailEditText.getText().toString().trim();
                if (isValidEmail(email)) {
                    Boolean user = db.checkemail(email);
                    if (user == true) {

                        sendMail();

                    } else {
                        Toast.makeText(PasswordActivity.this, "Email address does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PasswordActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendMail() {

        String mail = emailEditText.getText().toString().trim();
        String message = db.getuser(mail);
        String subject = "Email forgot password";

        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,mail,subject,message);

        javaMailAPI.execute();
        Intent intent= new Intent(PasswordActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


















}